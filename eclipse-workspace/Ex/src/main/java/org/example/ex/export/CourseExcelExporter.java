package org.example.ex.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ex.entity.Course;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CourseExcelExporter {

    private XSSFWorkbook workbook;
    private Sheet sheet;
    private List<Course> courses;

    public CourseExcelExporter(List<Course> courses) {
        this.courses = courses;
        workbook = new XSSFWorkbook();
    }

    //  Tạo tiêu đề cột
    private void writeHeaderRow() {
        sheet = workbook.createSheet("Courses");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setFontName("Arial");
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        String[] headers = {"ID", "Tên khóa học", "Mô tả", "Chủ đề"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    //  Ghi dữ liệu khóa học vào các dòng tiếp theo
    private void writeDataRows() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        for (Course course : courses) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(course.getId());
            row.createCell(1).setCellValue(course.getName());
            row.createCell(2).setCellValue(course.getDescription() != null ? course.getDescription() : "");
            row.createCell(3).setCellValue(course.getTopic() != null ? course.getTopic().getName() : "");
        }

        //  Tự động điều chỉnh độ rộng cột
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    //  Xuất file Excel qua response HTTP
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();

        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
