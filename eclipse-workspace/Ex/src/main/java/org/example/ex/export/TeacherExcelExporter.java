package org.example.ex.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ex.entity.Teacher;
import org.example.ex.entity.Course;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherExcelExporter {

    private XSSFWorkbook workbook;
    private Sheet sheet;
    private List<Teacher> teachers;

    public TeacherExcelExporter(List<Teacher> teachers) {
        this.teachers = teachers;
        workbook = new XSSFWorkbook();
    }

    // 🎯 Tạo tiêu đề cột với font chữ hỗ trợ UTF-8
    private void writeHeaderRow() {
        sheet = workbook.createSheet("Teachers");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setFontName("Arial"); // Sử dụng Arial hỗ trợ UTF-8
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        String[] headers = {"ID", "Họ và Tên", "Email", "Số điện thoại", "Mô tả", "Danh sách môn học"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    // 🎯 Ghi dữ liệu giáo viên vào các dòng tiếp theo
    private void writeDataRows() {
        int rowCount = 1;

        for (Teacher teacher : teachers) {
            Row row = sheet.createRow(rowCount++);

            row.createCell(0).setCellValue(teacher.getId());
            row.createCell(1).setCellValue(teacher.getName());
            row.createCell(2).setCellValue(teacher.getEmail());
            row.createCell(3).setCellValue(teacher.getPhoneNumber());
            row.createCell(4).setCellValue(teacher.getDescription());

            // 📝 Lấy danh sách tên môn học cách nhau bởi dấu phẩy
            String courseList = teacher.getCourses().stream()
                    .map(Course::getName)
                    .collect(Collectors.joining(", "));
            row.createCell(5).setCellValue(courseList);
        }

        // 📏 Tự động điều chỉnh độ rộng cột
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    // 📤 Xuất file Excel qua response HTTP
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
