package org.example.ex.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ex.entity.Course;
import org.example.ex.entity.Lesson;
import org.example.ex.repository.CourseRepository;
import org.example.ex.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class LessonImportService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository; // Thêm repository của Course

    public void importLessonsFromExcel(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream(); // ✅ Sửa lỗi ở đây
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua header

                Lesson lesson = new Lesson();
                lesson.setName(getCellValue(row.getCell(0)));
                lesson.setDescription(getCellValue(row.getCell(1)));

                // Lấy courseId từ Excel và tìm Course trong database
                Integer courseId = (int) getDoubleValue(row.getCell(2));
                Course course = courseRepository.findById(courseId).orElse(null);

                if (course == null) {
                    System.err.println("❌ Không tìm thấy Course với ID: " + courseId);
                    continue; // Bỏ qua nếu không tìm thấy khóa học
                }

                lesson.setCourse(course);
                lessonRepository.save(lesson);
            }
            System.out.println("✅ Import dữ liệu từ Excel thành công!");
        } catch (IOException e) {
            System.err.println("❌ Lỗi khi import file Excel: " + e.getMessage());
        }
    }


    private double getDoubleValue(Cell cell) {
        if (cell == null) return 0.0;
        if (cell.getCellType() == CellType.NUMERIC) return cell.getNumericCellValue();
        if (cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                System.err.println("⚠ Không thể chuyển đổi sang double: " + cell.getStringCellValue());
                return 0.0;
            }
        }
        return 0.0;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue();
        if (cell.getCellType() == CellType.NUMERIC) return String.valueOf(cell.getNumericCellValue());
        return "";
    }
}
