package com.daye.common.util;

import com.daye.sys.entity.vt.VT_Jfjl;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class ExcelUtil {

    public static void createExcel(HttpServletRequest request, HttpServletResponse response,
                                   List<VT_Jfjl> list, String fileName, List<String> title) {
        try {
            // 创建Excel的工作书册 Workbook,对应到一个excel文档
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFCellStyle style = workbook.createCellStyle();
            // 生成一个字体
            HSSFFont font = workbook.createFont();
            // 字体增粗
            //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setFontHeightInPoints((short) 11);
            style.setFont(font);
            // 创建Excel的工作sheet,对应到一个excel文档的tab
            HSSFSheet sheet = workbook.createSheet("sheet1");
            // 创建Excel的sheet的一行 (表头)
            HSSFRow row = sheet.createRow(0);
            // 表头内容填充
            for (int i = 0; i < title.size(); i++) {
                // 设置excel每列宽度
                sheet.setColumnWidth(i, 5000);
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(title.get(i));
                cell.setCellStyle(style);
            }
            // 创建内容行
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setWrapText(true);// 自动换行
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
            for (int j = 0; j < list.size(); j++) {
                HSSFRow contentRow = sheet.createRow(j + 1);
                for (int k = 0; k < title.size(); k++) {
                    HSSFCell cell = contentRow.createCell(k);
                    switch (k) {
                        // 用的时候把这一块释放开，改成自己的
                        case 0:
                            if (list.get(j).getSort() != null) {// 每一列的名称
                                cell.setCellValue(list.get(j).getSort().toString());
                            }
                            break;
                        case 1:
                            if (list.get(j).getMeterId() != null) {// 每一列的名称
                                cell.setCellValue(list.get(j).getMeterId());
                            }
                            break;
                        case 2:
                            if (list.get(j).getName() != null) {//每一列的名称
                                cell.setCellValue(list.get(j).getName());
                            }
                            break;
                        case 3:
                            if (list.get(j).getIdCode() != null) {//每一列的名称
                                cell.setCellValue(list.get(j).getIdCode());
                            }
                            break;
                        case 4:
                            if (list.get(j).getAddress() != null) {
                                cell.setCellValue(list.get(j).getAddress());
                            }
                            break;
                        case 5:
                            if (list.get(j).getPhoneNum() != null) {
                                cell.setCellValue(list.get(j).getPhoneNum());
                            }
                            break;
                        case 6:
                            if (list.get(j).getPayStatu()!=null) {
                                int sta = list.get(j).getPayStatu();
                                if(sta==0) cell.setCellValue("未缴费");
                                if(sta==1) cell.setCellValue("欠费");
                                if(sta==2) cell.setCellValue("已缴费");
                            }
                            break;
                        case 7:
                            if (list.get(j).getPeriodElecNum() != null) {
                                cell.setCellValue(list.get(j).getPeriodElecNum().toString());
                            }
                            break;
                        case 8:
                            if (list.get(j).getTate() != null) {
                                cell.setCellValue(list.get(j).getTate());
                            }
                            break;
                        case 9:
                            if (list.get(j).getAmountDue() != null) {
                                cell.setCellValue(list.get(j).getAmountDue());
                            }
                            break;
                        case 10:
                            if (list.get(j).getActualAmount() != null) {
                                cell.setCellValue(list.get(j).getActualAmount());
                            }
                            break;
                        case 11:
                            if (list.get(j).getCreatedTime() != null) {
                                cell.setCellValue(list.get(j).getCreatedTime());
                            }
                            break;
                        case 12:
                            if (list.get(j).getModifiedTime() != null) {
                                cell.setCellValue(list.get(j).getModifiedTime());
                            }
                            break;
                        case 13:
                            if (list.get(j).getReceiptStatus() != null) {
                                int ptin = list.get(j).getPayStatu();
                                if(ptin==0) cell.setCellValue("未打印");
                                if(ptin==1) cell.setCellValue("打印");
                            }
                            break;
                        case 14:
                            if (list.get(j).getNote() != null) {
                                cell.setCellValue(list.get(j).getNote());
                            }
                            break;
                        case 15:
                            if (list.get(j).getValid() != null) {
                                int val = list.get(j).getValid();
                                if(val==0) cell.setCellValue("禁用");
                                if(val==1) cell.setCellValue("启用");
                            }
                            break;
                        case 16:
                            if (list.get(j).getCreatedUserTime() != null) {
                                cell.setCellValue(list.get(j).getCreatedUserTime());
                            }
                            break;
                        case 17:
                            if (list.get(j).getModifiedUserTime() != null) {
                                cell.setCellValue(list.get(j).getModifiedUserTime());
                            }
                            break;
                        case 18:
                            if (list.get(j).getUserNote() != null) {
                                cell.setCellValue(list.get(j).getUserNote());
                            }
                            break;
                        default:
                            break;
                    }
                    cell.setCellStyle(cellStyle);
                }
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                workbook.write(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes("GBK"), "ISO8859_1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}