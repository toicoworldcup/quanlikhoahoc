package org.example.ex.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ex.entity.Lesson;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LessonExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Lesson> lessonList;

    public LessonExcelExporter(List<Lesson> lessonList) {
        this.lessonList = lessonList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderRow() {
        sheet = workbook.createSheet("Lessons");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setFontName("Arial"); // Sử dụng Arial để hỗ trợ UTF-8
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        String[] headers = {"ID", "Name", "Description",  "Course ID"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private void writeDataRows() {
        int rowCount = 1;
        for (Lesson lesson : lessonList) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(lesson.getId());
            row.createCell(1).setCellValue(lesson.getName());
            row.createCell(2).setCellValue(lesson.getDescription());
            row.createCell(3).setCellValue(
                    lesson.getCourse() != null ? lesson.getCourse().getId() : null
            );
        }
    }

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
