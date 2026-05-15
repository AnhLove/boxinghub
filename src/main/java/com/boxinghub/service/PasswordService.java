package com.boxinghub.service;

public interface PasswordService {
    /** Tạo token và gửi email reset link */
    void initiatePasswordReset(String email);

    /** Xác thực token có hợp lệ không (để hiển thị form) */
    boolean validateResetToken(String token);

    /** Đặt lại mật khẩu mới */
    void resetPassword(String token, String newPassword);

    /** Đổi mật khẩu khi đã đăng nhập (từ trang Profile) */
    void changePassword(String email, String currentPassword, String newPassword);
}