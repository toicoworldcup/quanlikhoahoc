package org.example.ex.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ex.entity.Student;
import org.example.ex.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class StudentImportService {
    @Autowired
    private StudentRepository studentRepository;

    public void importStudentsFromExcel(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua header

                Student student = new Student();
                student.setName(getCellValue(row.getCell(0)));
                student.setEmail(getCellValue(row.getCell(1)));
                student.setPhone(getCellValue(row.getCell(2)));
                student.setDateOfBirth(parseDateCell(row.getCell(3)));
                student.setAddress(getCellValue(row.getCell(4)));

                studentRepository.save(student);
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
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    private LocalDate parseDateCell(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC || !DateUtil.isCellDateFormatted(cell)) {
            return null;
        }
        Date date = cell.getDateCellValue();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
