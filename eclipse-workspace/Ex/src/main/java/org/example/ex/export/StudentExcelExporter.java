package org.example.ex.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ex.entity.Student;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StudentExcelExporter {

    private XSSFWorkbook workbook;
    private Sheet sheet;
    private List<Student> students;

    public StudentExcelExporter(List<Student> students) {
        this.students = students;
        workbook = new XSSFWorkbook();
    }
    //ghi tieu de
    private void writeHeaderRow() {
        sheet = workbook.createSheet("Students");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setFontName("Arial");
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        String[] headers = {"ID", "Họ và Tên", "Email", "Số điện thoại", "Ngày sinh", "Địa chỉ"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    //  Ghi dữ liệu sinh viên vào file
    private void writeDataRows() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);//xuong dong
        style.setVerticalAlignment(VerticalAlignment.CENTER);//can chinh

        for (Student student : students) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(student.getId());
            row.createCell(1).setCellValue(student.getName());
            row.createCell(2).setCellValue(student.getEmail());
            row.createCell(3).setCellValue("'"+student.getPhone());
            row.createCell(4).setCellValue(student.getDateOfBirth().toString());
            row.createCell(5).setCellValue(student.getAddress());
        }

        // điều chỉnh độ rộng cột
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    // Xuất file Excel
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
