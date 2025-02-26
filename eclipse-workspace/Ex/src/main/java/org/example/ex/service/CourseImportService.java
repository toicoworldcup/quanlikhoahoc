package org.example.ex.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ex.entity.Course;
import org.example.ex.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class CourseImportService {
    @Autowired
    private CourseRepository courseRepository;
    public void importCoursesFromExcel(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua header

                Course course = new Course();
                course.setName(getCellValue(row.getCell(0)));
                course.setDescription(getCellValue(row.getCell(1)));

                courseRepository.save(course);
            }
            System.out.println("✅ Import dữ liệu từ Excel thành công!");
        } catch (IOException e) {
            System.err.println("❌ Lỗi khi import file Excel: " + e.getMessage());
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue();
        if (cell.getCellType() == CellType.NUMERIC) return String.valueOf(cell.getNumericCellValue());
        return "";
    }
}
