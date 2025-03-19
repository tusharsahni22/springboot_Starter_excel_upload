package com.demo.demo.service.impl;

    import com.demo.demo.persistence.entity.User;
    import com.demo.demo.persistence.respository.UserRepository;
    import com.demo.demo.service.UserService;
    import lombok.RequiredArgsConstructor;
    import org.apache.poi.ss.usermodel.*;
    import org.apache.poi.xssf.usermodel.XSSFWorkbook;
    import org.springframework.stereotype.Component;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.util.Optional;

    @Component
    @RequiredArgsConstructor
    public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;

        @Override
        public void processExcelFile(MultipartFile file) throws IOException {
            try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    if (row.getRowNum() == 0 || row.getCell(0) == null) {
                        continue; // Skip header row
                    }
                    User user = new User();
                    user.setUsername(getCellValue(row, 0));
                    user.setPassword(getCellValue(row, 1));
                    user.setEmail(getCellValue(row, 2));

                    userRepository.save(user);
                }
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        private String getCellValue(Row row, int cellIndex) {
            Cell cell = row.getCell(cellIndex);
            if (cell == null) {
                return "";
            }
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                return String.valueOf(cell.getNumericCellValue());
            } else if (cell.getCellType() == CellType.BOOLEAN) {
                return String.valueOf(cell.getBooleanCellValue());
            } else {
                return "";
            }
        }

        @Override
        public boolean authenticate(String username, String password) {
            Optional<User> userOptional = userRepository.findByUsername(username);
            return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
        }
    }