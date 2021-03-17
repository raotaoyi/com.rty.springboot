package com.rty.springboot.util;

import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 运用反射字段，将对象赋值，提高代碼的复用性
 */
public class ExcelFileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelFileUtil.class);

    public static void exportExcel(HttpServletResponse response, String fileName,
                                   Map<String, Map<String, List<Object>>> exportData) throws IOException, IllegalAccessException {
        initExportHeader(response, fileName);
        //初始化respone
        try (OutputStream outputStream = response.getOutputStream();) {
            XSSFWorkbook wb = new XSSFWorkbook();
            export(outputStream, wb, exportData);
        }
    }

    public static void export(OutputStream outputStream, XSSFWorkbook wb,
                              Map<String, Map<String, List<Object>>> exportData) throws IllegalAccessException, IOException {
        //wb设置style
        setStyle(wb);
        for (Map.Entry<String, Map<String, List<Object>>> entry : exportData.entrySet()) {
            String sheetName = entry.getKey();
            XSSFSheet sheet = wb.createSheet(sheetName);
            Map<String, List<Object>> dataValue = entry.getValue();
            for (Map.Entry<String, List<Object>> values : dataValue.entrySet()) {
                String header = values.getKey();
                String[] excelHeader = header.split("[|]");
                List<String> titleArray = new ArrayList<>();
                List<String> filedArray = new ArrayList<>();
                for (int i = 0; i < excelHeader.length; i++) {
                    String[] tempArray = excelHeader[i].split("#");
                    titleArray.add(tempArray[1]);
                    filedArray.add(tempArray[0]);
                }
                //sheet页添加标题行
                //行数从1开始
                XSSFRow row = sheet.createRow(1);
                sheet.autoSizeColumn(0);
                XSSFCell cell = null;
                for (int i = 0; i < titleArray.size(); i++) {
                    //设置列数值
                    cell = row.createCell(i);
                    cell.setCellValue(titleArray.get(i));
                }
                //设置cell的字体样式
                Field[] fields;
                int i = 2;
                List<Object> objs = values.getValue();
                for (Object obj : objs) {
                    fields = obj.getClass().getDeclaredFields();
                    XSSFRow rowBody = sheet.createRow(i);
                    rowBody.setHeightInPoints(20);
                    int j = 0;
                    for (String field : filedArray) {
                        for (Field fieldObj : fields) {
                            fieldObj.setAccessible(true);
                            Object va = fieldObj.get(obj);
                            if (fieldObj.getName().equals(field)) {
                                if (null == va) {
                                    va = "";
                                }
                                XSSFCell hc = rowBody.createCell(j);
                                hc.setCellValue(va.toString());
                                j++;
                            }
                        }
                    }
                    i++;
                }

            }
        }
        wb.write(outputStream);
        outputStream.flush();
    }

    public static void setStyle(XSSFWorkbook wb) {
        //表头样式（加粗，水平居中，垂直居中）
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //设置边框样式
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框

        XSSFFont fontStyle = wb.createFont();
        fontStyle.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);

        cellStyle.setFont(fontStyle);

        //标题样式（加粗，垂直居中）
        XSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle2.setFont(fontStyle);

        //设置边框样式
        cellStyle2.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
        cellStyle2.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
        cellStyle2.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
        cellStyle2.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框

        //字段样式（垂直居中）
        XSSFCellStyle cellStyle3 = wb.createCellStyle();
        cellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        //设置边框样式
        cellStyle3.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
        cellStyle3.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
        cellStyle3.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
        cellStyle3.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框

    }

    private static void initExportHeader(HttpServletResponse response, String fileName) {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
    }

}
