package com.demo.demo.service;

        import org.springframework.web.multipart.MultipartFile;

        import java.io.IOException;

        public interface UserService {
            void processExcelFile(MultipartFile file) throws IOException;
            boolean authenticate(String username, String password);

        }