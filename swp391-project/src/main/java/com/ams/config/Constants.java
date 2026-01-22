package com.ams.config;

/**
 * Class chứa các hằng số toàn cục của ứng dụng.
 * Tránh magic values, tập trung quản lý các constant.
 */
public final class Constants {

    // ========== Tên Session Attributes ==========

    /**
     * Key lưu trữ user đã đăng nhập trong session.
     */
    public static final String SESSION_USER = "loggedInUser";

    /**
     * Key lưu trữ role của user trong session.
     */
    public static final String SESSION_ROLE = "userRole";

    // ========== Role Names (phải khớp với database) ==========

    /**
     * Vai trò Hiệu trưởng - quyền duyệt tối cao.
     */
    public static final String ROLE_PRINCIPAL = "Principal";

    /**
     * Vai trò Trưởng phòng TC-KT - quản lý tài sản, ngân sách.
     */
    public static final String ROLE_FINANCE_HEAD = "Finance_Head";

    /**
     * Vai trò Nhân viên thiết bị - tác nghiệp nhập liệu, bảo trì.
     */
    public static final String ROLE_ASSET_STAFF = "Asset_Staff";

    /**
     * Vai trò Trưởng bộ môn - yêu cầu cấp phát, quản lý phòng.
     */
    public static final String ROLE_HEAD_OF_DEPT = "Head_of_Dept";

    // ========== Role IDs (phải khớp với database seed data) ==========

    public static final int ROLE_ID_PRINCIPAL = 1;
    public static final int ROLE_ID_FINANCE_HEAD = 2;
    public static final int ROLE_ID_ASSET_STAFF = 3;
    public static final int ROLE_ID_HEAD_OF_DEPT = 4;

    // ========== URLs / Paths ==========

    /**
     * Trang login.
     */
    public static final String PAGE_LOGIN = "/login";

    /**
     * Trang dashboard chính sau khi đăng nhập.
     */
    public static final String PAGE_DASHBOARD = "/dashboard";

    /**
     * Prefix cho các trang trong WEB-INF.
     */
    public static final String VIEW_PREFIX = "/WEB-INF/views/";

    /**
     * Suffix cho JSP files.
     */
    public static final String VIEW_SUFFIX = ".jsp";

    // ========== Validation & Limits ==========

    /**
     * Độ dài tối thiểu của password.
     */
    public static final int PASSWORD_MIN_LENGTH = 6;

    /**
     * Số items mặc định trên mỗi trang khi phân trang.
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Kích thước tối đa của file upload (5MB).
     */
    public static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    // ========== Asset Status Values (phải khớp với ENUM trong database) ==========

    public static final String ASSET_STATUS_NEW = "New";
    public static final String ASSET_STATUS_IN_USE = "In_Use";
    public static final String ASSET_STATUS_MAINTENANCE = "Maintenance";
    public static final String ASSET_STATUS_BROKEN = "Broken";
    public static final String ASSET_STATUS_LIQUIDATED = "Liquidated";
    public static final String ASSET_STATUS_LOST = "Lost";

    // ========== Transfer Status Values ==========

    public static final String TRANSFER_STATUS_PENDING = "Pending";
    public static final String TRANSFER_STATUS_APPROVED = "Approved";
    public static final String TRANSFER_STATUS_HANDING_OVER = "Handing_Over";
    public static final String TRANSFER_STATUS_COMPLETED = "Completed";
    public static final String TRANSFER_STATUS_REJECTED = "Rejected";

    // ========== Messages ==========

    public static final String MSG_LOGIN_SUCCESS = "Đăng nhập thành công!";
    public static final String MSG_LOGIN_FAILED = "Tên đăng nhập hoặc mật khẩu không đúng.";
    public static final String MSG_LOGIN_INACTIVE = "Tài khoản đã bị vô hiệu hóa. Vui lòng liên hệ quản trị viên.";
    public static final String MSG_LOGOUT_SUCCESS = "Đăng xuất thành công!";
    public static final String MSG_ACCESS_DENIED = "Bạn không có quyền truy cập trang này.";
    public static final String MSG_SESSION_EXPIRED = "Phiên làm việc đã hết hạn. Vui lòng đăng nhập lại.";

    // ========== Private Constructor ==========

    /**
     * Private constructor để ngăn tạo instance.
     */
    private Constants() {
        throw new UnsupportedOperationException("Utility class - không thể tạo instance");
    }
}
