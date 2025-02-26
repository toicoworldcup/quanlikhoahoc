package org.example.ex.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ex.entity.Course;
import org.example.ex.entity.Student;
import org.example.ex.entity.Teacher;
import org.example.ex.repository.CourseRepository;
import org.example.ex.repository.StudentRepository;
import org.example.ex.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TeacherImportService {
    @Autowired
    private TeacherRepository teacherRepository;
    public void importTeachersFromExcel(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua header

                Teacher teacher = new Teacher();
                teacher.setName(getCellValue(row.getCell(0)));
                teacher.setEmail(getCellValue(row.getCell(1)));
                teacher.setPhoneNumber(getCellValue(row.getCell(2)));
                teacher.setDescription(getCellValue(row.getCell(3)));


                teacherRepository.save(teacher);
            }
            System.out.println("✅ Import dữ liệu từ Excel thành công!");
        } catch (IOException e) {
            System.err.println("❌ Lỗi khi import file Excel: " + e.getMessage());
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                // 🔎 Kiểm tra nếu ô là kiểu ngày tháng
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return localDate.toString(); // 🗓 Trả về chuỗi định dạng yyyy-MM-dd
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case BLANK:
                return "";

            default:
                return "";
        }
    }
}
