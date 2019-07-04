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
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
            for (int j = 0; j < list.size(); j++) {
                HSSFRow contentRow = sheet.createRow(j + 1);
                for (int k = 0; k < title.size(); k++) {
                    HSSFCell cell = contentRow.createCell(k);
                    switch (k) {
                        // 用的时候把这一块释放开，改成自己的
                        /*case 0:
                            if (list.get(j).getAddress() != null) {// 每一列的名称
                                cell.setCellValue(list.get(j).getAddress());
                            }
                            break;
                        case 1:
                            if (list.get(j).getShake() != null) {//每一列的名称
                                cell.setCellValue(list.get(j).getShake());
                            }
                            break;
                        case 2:
                            if (list.get(j).getNetname() != null) {//每一列的名称
                                cell.setCellValue(list.get(j).getNetname());
                            }
                            break;
                        case 3:
                            if (list.get(j).getNet() != null) {
                                cell.setCellValue(list.get(j).getNet());
                            }
                            break;
                        case 4:
                            if (list.get(j).getState() != null) {
                                cell.setCellValue(list.get(j).getState());
                            }
                            break;
                        case 5:
                            if (list.get(j).getUsetime() > 0) {
                                cell.setCellValue(list.get(j).getUsetime());
                            }
                            break;
                        case 6:
                            if (list.get(j).getTime() != null) {
                                cell.setCellValue(list.get(j).getTime());
                            }
                            break;
                        default:
                            break;*/
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
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes("gb2312"), "iso-8859-1"));
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