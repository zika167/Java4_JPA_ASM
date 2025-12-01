package com.fpt.java4_asm.utils.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Lớp tiện ích để xử lý mật khẩu
 * Cung cấp các phương thức mã hóa và xác thực mật khẩu
 */
public class PasswordValidation {
    
    private PasswordValidation() {
        throw new UnsupportedOperationException("Không thể tạo thể hiện của lớp tiện ích");
    }

    /**
     * Mã hóa mật khẩu bằng SHA-256
     * 
     * @param password Mật khẩu cần mã hóa
     * @return Mật khẩu đã mã hóa (Base64)
     */
    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được để trống");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi khi mã hóa mật khẩu: " + e.getMessage(), e);
        }
    }

    /**
     * Xác thực mật khẩu bằng cách so sánh với mật khẩu đã mã hóa
     * 
     * @param rawPassword Mật khẩu chưa mã hóa
     * @param hashedPassword Mật khẩu đã mã hóa
     * @return true nếu mật khẩu khớp, false nếu không
     */
    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        if (rawPassword == null || rawPassword.isEmpty() || hashedPassword == null || hashedPassword.isEmpty()) {
            return false;
        }

        try {
            String hashedInput = hashPassword(rawPassword);
            return hashedInput.equals(hashedPassword);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Kiểm tra mật khẩu có hợp lệ không
     * Yêu cầu: ít nhất 6 ký tự
     * 
     * @param password Mật khẩu cần kiểm tra
     * @return true nếu hợp lệ, false nếu không
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        
        // Ít nhất 6 ký tự
        if (password.length() < 6) {
            return false;
        }
        
        return true;
    }
}
