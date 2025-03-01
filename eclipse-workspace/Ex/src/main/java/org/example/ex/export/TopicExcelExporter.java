package org.example.ex.export;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ex.entity.Topic;
import org.example.ex.entity.Course;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TopicExcelExporter {

    private XSSFWorkbook workbook;
    private Sheet sheet;
    private List<Topic> topics;

    public TopicExcelExporter(List<Topic> topics) {
        this.topics = topics;
        workbook = new XSSFWorkbook();
    }

    // Tạo tiêu đề cột
    private void writeHeaderRow() {
        sheet = workbook.createSheet("Topics");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setFontName("Arial");
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        String[] headers = {"ID", "Tên chủ đề", "Mô tả", "Danh sách khóa học"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    // Ghi dữ liệu các chủ đề vào các dòng
    private void writeDataRows() {
        int rowCount = 1;

        for (Topic topic : topics) {
            Row row = sheet.createRow(rowCount++);

            row.createCell(0).setCellValue(topic.getId());
            row.createCell(1).setCellValue(topic.getName());
            row.createCell(2).setCellValue(topic.getDescription());


        }

        //  điều chỉnh độ rộng cột
        for (int i = 0; i < 4; i++) {
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
