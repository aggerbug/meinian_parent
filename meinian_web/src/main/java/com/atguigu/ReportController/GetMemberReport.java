package com.atguigu.ReportController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.MemberService;
import com.atguigu.service.SetmealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class GetMemberReport {

    @Reference
    MemberService memberService;

    @Reference
    SetmealService setmealService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-9);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <12 ; i++) {
            calendar.add(Calendar.MONTH,1);
            list.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }
        Map<String,Object> map=new HashMap<>();
        map.put("months",list);
        List<Integer> member=memberService.findMemberReport(list);
        map.put("memberCount",member);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        List<Map<String,Object>> listMap=setmealService.findSetmealCount();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("setmealCount",listMap);
        ArrayList<String> list = new ArrayList<>();
        for (Map<String, Object> map : listMap) {
            String name = (String)map.get("name");
            list.add(name);
        }
        hashMap.put("setmealNames",list);
        return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,hashMap);
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map<String,Object> map=setmealService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response)  {
        try {
            Map<String,Object> map=setmealService.getBusinessReportData();
            String reportDate =(String)map.get("reportDate");
            Integer todayNewMember = (Integer)map.get("todayNewMember");
            Integer totalMember = (Integer) map.get("totalMember");
            Integer thisWeekNewMember = (Integer) map.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) map.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) map.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer)map.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) map.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) map.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) map.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) map.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) map.get("hotSetmeal");

            String temlateRealPath=request.getSession().getServletContext().getRealPath("template")+
                    File.separator+"report_template.xlsx";
            XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(new File(temlateRealPath)));
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            XSSFRow row = sheetAt.getRow(2);
            row.getCell(5).setCellValue(reportDate);
            row=sheetAt.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);
            row=sheetAt.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);
            row.getCell(7).setCellValue(thisMonthNewMember);
            row = sheetAt.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日出游数

            row = sheetAt.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周出游数

            row = sheetAt.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月出游数

            int rowNum=12;
            for (Map map1 : hotSetmeal) {
                String name=(String)map1.get("name");
                Long setmeal_count=(Long)map1.get("setmeal_count");
                BigDecimal proportion=(BigDecimal)map1.get("proportion");
                row=sheetAt.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比

            }
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS);
        }


    }

}
