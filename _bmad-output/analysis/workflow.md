# QUẢN LÝ TÀI SẢN

## MAIN PROCESS 1: QUẢN LÝ DANH MỤC TÀI SẢN

**Actor chính:** Trưởng phòng TC-KT

**Trình tự thực hiện:**

*   **Đăng nhập hệ thống** (UC28)
*   **Truy cập module Quản lý Danh mục**
*   **Xem và tìm kiếm danh sách danh mục** (UC04)
*   **Decision Point:** Muốn thực hiện hành động gì?
    *   **Tạo mới** → UC01: Tạo danh mục tài sản mới
        *   Nhập thông tin danh mục (tên, mô tả, quy tắc mã tự động)
        *   Lưu danh mục
        *   Quay lại danh sách (UC04)
    *   **Cập nhật** → UC02: Cập nhật thông tin danh mục
        *   Chọn danh mục cần sửa
        *   Chỉnh sửa thông tin
        *   Lưu thay đổi
        *   Quay lại danh sách (UC04)
    *   **Xóa** → UC03: Xóa danh mục tài sản
        *   Chọn danh mục cần xóa
        *   **Decision Point:** Có tài sản thuộc danh mục không?
            *   **Có** → Hiển thị cảnh báo, không cho xóa
            *   **Không** → Xác nhận xóa → Xóa thành công
        *   Quay lại danh sách (UC04)
    *   **Xem chi tiết** → Xem thông tin chi tiết danh mục
*   **Đăng xuất** (UC29)

---

## MAIN PROCESS 2: QUẢN LÝ TÀI SẢN

**Actor chính:** Nhân viên quản lý tài sản, Trưởng phòng TC-KT

**Quy trình A: Thêm mới và cập nhật tài sản (Nhân viên quản lý tài sản)**

*   **Đăng nhập hệ thống** (UC28)
*   **Truy cập module Quản lý Tài sản**
*   **Xem và tìm kiếm danh sách tài sản** (UC06)
*   **Decision Point:** Muốn thực hiện hành động gì?
    *   **Thêm mới** → UC05: Thêm mới tài sản vào hệ thống
        *   Chọn danh mục tài sản
        *   Nhập thông tin tài sản (tên, mô tả, giá trị, nhà cung cấp, ngày mua...)
        *   Hệ thống tự động tạo mã tài sản (VD: MT-2024-001)
        *   Lưu tài sản
        *   Quay lại danh sách (UC06)
    *   **Cập nhật thông tin** → UC08: Cập nhật thông tin tài sản
        *   Chọn tài sản cần sửa
        *   Chỉnh sửa thông tin chi tiết, giá trị
        *   Lưu thay đổi
    *   **Cập nhật trạng thái** → UC09: Cập nhật trạng thái tài sản
        *   Chọn tài sản
        *   Thay đổi trạng thái (Mới, Đang sử dụng, Hỏng, Bảo trì, Thanh lý)
        *   Nhập ghi chú (nếu cần)
        *   Lưu thay đổi
    *   **Xem chi tiết** → UC07: Xem chi tiết tài sản
        *   Xem đầy đủ thông tin
        *   Xem lịch sử điều chuyển
        *   Xem vị trí hiện tại
    *   **Xóa/Thanh lý tài sản**
        *   Chọn tài sản cần thanh lý
        *   Nhập lý do thanh lý
        *   Xác nhận thanh lý
        *   Tài sản được đánh dấu trạng thái "Thanh lý"
*   **Đăng xuất** (UC29)

---

## MAIN PROCESS 3: GHI NHẬN TĂNG TÀI SẢN (YÊU CẦU VÀ MUA SẮM)

**Actor:** Trưởng bộ môn → Nhân viên quản lý tài sản → Hiệu trưởng

### BƯỚC 1: Tạo yêu cầu cấp phát (Trưởng bộ môn)

*   **Đăng nhập hệ thống** (UC28)
*   **UC11: Tạo yêu cầu cấp phát tài sản**
    *   Chọn danh mục tài sản cần yêu cầu
    *   Nhập số lượng, mô tả, lý do cần
    *   Chọn phòng học/bộ môn nhận
    *   Gửi yêu cầu
*   **UC12: Xem danh sách yêu cầu cấp phát tài sản**
    *   Theo dõi trạng thái yêu cầu
*   **Decision Point:** Yêu cầu có được phê duyệt không?
    *   **Chờ duyệt** → Tiếp tục theo dõi
    *   **Từ chối** → Xem lý do từ chối → **Decision Point:**
        *   Chấp nhận → Kết thúc
        *   Không chấp nhận → UC15: Hủy yêu cầu và tạo yêu cầu mới
    *   **Đã duyệt** → Chờ nhận tài sản
*   **Đăng xuất** (UC29)

### BƯỚC 2: Xử lý yêu cầu cấp phát (Nhân viên quản lý tài sản)

*   **Đăng nhập hệ thống** (UC28)
*   **UC12: Xem danh sách yêu cầu cấp phát tài sản**
    *   Xem các yêu cầu đang chờ duyệt
*   **UC13: Kiểm tra tồn kho tài sản**
    *   Kiểm tra tài sản có sẵn trong kho
*   **Decision Point:** Có đủ tài sản trong kho không?
    *   **TH1: CÓ đủ tài sản**
        *   **UC14: Phê duyệt yêu cầu cấp phát tài sản**
            *   Xác nhận phê duyệt
            *   Gán tài sản cụ thể cho yêu cầu
            *   Thông báo cho người yêu cầu
        *   **Kết thúc quy trình**
    *   **TH2: KHÔNG đủ tài sản**
        *   **UC14: Từ chối yêu cầu cấp phát (tạm thời)**
            *   Nhập lý do: "Không đủ tài sản trong kho"
        *   **UC16: Tạo yêu cầu mua sắm tài sản**
            *   Lập đề xuất mua sắm
            *   Nhập thông tin: tài sản cần mua, số lượng, giá ước tính, nhà cung cấp
            *   Đính kèm yêu cầu cấp phát gốc
            *   Gửi yêu cầu mua sắm lên Hiệu trưởng
        *   **Chuyển sang BƯỚC 3**
*   **Đăng xuất** (UC29)

### BƯỚC 3: Phê duyệt mua sắm (Hiệu trưởng)

*   **Đăng nhập hệ thống** (UC28)
*   **UC17: Xem danh sách yêu cầu mua sắm tài sản**
    *   Xem các yêu cầu đang chờ phê duyệt
*   **Xem chi tiết yêu cầu mua sắm**
    *   Kiểm tra thông tin: lý do, giá trị, nguồn kinh phí
*   **Decision Point:** Phê duyệt hay từ chối?
    *   **Phê duyệt:**
        *   **UC18: Phê duyệt yêu cầu mua sắm**
            *   Xác nhận phê duyệt
            *   Chọn nguồn kinh phí
            *   Thông báo cho Nhân viên quản lý tài sản
        *   Sau khi mua về → Nhân viên quản lý tài sản thực hiện **UC05: Thêm mới tài sản vào hệ thống**
        *   Quay lại **UC14: Phê duyệt yêu cầu cấp phát** cho yêu cầu gốc
    *   **Từ chối:**
        *   **UC18: Từ chối yêu cầu mua sắm**
            *   Nhập lý do từ chối
            *   Thông báo cho Nhân viên quản lý tài sản
        *   Nhân viên quản lý tài sản thực hiện **UC14: Từ chối yêu cầu cấp phát** cho yêu cầu gốc (với lý do cụ thể)
*   **Đăng xuất** (UC29)

**Lưu ý:**
*   Người tạo yêu cầu có thể thực hiện **UC15: Hủy yêu cầu cấp phát** hoặc **UC19: Hủy yêu cầu mua sắm** khi trạng thái còn "Chờ duyệt"

---

## MAIN PROCESS 4: ĐIỀU CHUYỂN TÀI SẢN

**Actor:** Nhân viên quản lý tài sản → Trưởng phòng TC-KT → Trưởng bộ môn (bàn giao) → Trưởng bộ môn (nhận)

### BƯỚC 1: Tạo phiếu điều chuyển (Nhân viên quản lý tài sản)

*   **Đăng nhập hệ thống** (UC28)
*   **UC20: Tạo phiếu điều chuyển tài sản**
    *   Chọn tài sản cần điều chuyển
    *   Chọn phòng/bộ môn nguồn (hiện tại)
    *   Chọn phòng/bộ môn đích (nơi nhận)
    *   Nhập lý do điều chuyển
    *   Nhập ngày dự kiến điều chuyển
    *   Tạo phiếu
*   **UC21: Xem và tìm kiếm phiếu điều chuyển**
    *   Theo dõi trạng thái phiếu
*   **Decision Point:** Cần hủy phiếu không?
    *   **Có** → UC25: Hủy phiếu điều chuyển (nếu chưa được phê duyệt)
    *   **Không** → Chờ phê duyệt
*   **Đăng xuất** (UC29)

### BƯỚC 2: Phê duyệt điều chuyển (Trưởng phòng TC-KT)

*   **Đăng nhập hệ thống** (UC28)
*   **UC21: Xem và tìm kiếm phiếu điều chuyển**
    *   Lọc phiếu "Chờ duyệt"
*   **Xem chi tiết phiếu điều chuyển**
    *   Kiểm tra tài sản, phòng nguồn, phòng đích, lý do
*   **Decision Point:** Phê duyệt hay từ chối?
    *   **Phê duyệt:**
        *   **UC22: Phê duyệt điều chuyển tài sản**
            *   Xác nhận phê duyệt
            *   Phiếu chuyển sang trạng thái "Đã duyệt - Chờ bàn giao"
            *   Thông báo cho Trưởng bộ môn nguồn
    *   **Từ chối:**
        *   **UC22: Từ chối điều chuyển tài sản**
            *   Nhập lý do từ chối
            *   Thông báo cho Nhân viên quản lý tài sản
        *   **Kết thúc quy trình**
*   **Đăng xuất** (UC29)

### BƯỚC 3: Bàn giao tài sản (Trưởng bộ môn - Phòng nguồn)

*   **Đăng nhập hệ thống** (UC28)
*   **UC21: Xem và tìm kiếm phiếu điều chuyển**
    *   Xem phiếu cần bàn giao
*   **UC23: Xác nhận bàn giao tài sản**
    *   Kiểm tra tài sản thực tế
    *   Xác nhận đã bàn giao
    *   Nhập ghi chú (tình trạng tài sản lúc bàn giao)
    *   Phiếu chuyển sang trạng thái "Đã bàn giao - Chờ nhận"
    *   Thông báo cho Trưởng bộ môn nhận
*   **Đăng xuất** (UC29)

### BƯỚC 4: Nhận tài sản (Trưởng bộ môn - Phòng đích)

*   **Đăng nhập hệ thống** (UC28)
*   **UC21: Xem và tìm kiếm phiếu điều chuyển**
    *   Xem phiếu cần nhận
*   **UC24: Xác nhận nhận tài sản**
    *   Kiểm tra tài sản thực tế
    *   **Decision Point:** Tài sản có vấn đề không?
        *   **Có vấn đề** → Nhập ghi chú vấn đề → Vẫn xác nhận nhận (để xử lý sau)
        *   **Không vấn đề** → Xác nhận nhận bình thường
    *   Xác nhận đã nhận
    *   Phiếu chuyển sang trạng thái "Hoàn thành"
    *   Hệ thống tự động cập nhật vị trí tài sản
*   **Đăng xuất** (UC29)

**Lưu ý:**
*   Có thể thực hiện **UC25: Hủy phiếu điều chuyển** ở các trạng thái:
    *   "Chờ duyệt": Nhân viên quản lý tài sản hoặc Trưởng phòng TC-KT
    *   "Đã duyệt - Chờ bàn giao": Trưởng phòng TC-KT (với lý do đặc biệt)

---

## MAIN PROCESS 5: BÁO CÁO VÀ PHÂN TÍCH

**Actor:** Hiệu trưởng, Trưởng phòng TC-KT, Nhân viên quản lý tài sản, Trưởng bộ môn

**Quy trình chung cho tất cả Actor:**

*   **Đăng nhập hệ thống** (UC28)
*   **Truy cập module Báo cáo**
*   **UC26: Xem báo cáo tài sản**
    *   **Decision Point:** Chọn loại báo cáo (tùy theo quyền hạn của actor)
*   **Hiệu trưởng:**
    *   Chọn "Báo cáo tổng quan tài sản (Dashboard)"
    *   Chọn "Báo cáo yêu cầu và mua sắm"
    *   Thiết lập bộ lọc (thời gian, trạng thái...)
*   **Trưởng phòng TC-KT:**
    *   Chọn "Báo cáo tổng quan tài sản (Dashboard)"
    *   Chọn "Báo cáo tài sản chi tiết"
    *   Chọn "Báo cáo yêu cầu và mua sắm"
    *   Chọn "Báo cáo điều chuyển"
    *   Thiết lập bộ lọc (danh mục, phòng học, trạng thái, nguồn kinh phí, thời gian...)
*   **Nhân viên quản lý tài sản:**
    *   Chọn "Báo cáo tài sản chi tiết"
    *   Chọn "Báo cáo yêu cầu và mua sắm"
    *   Chọn "Báo cáo điều chuyển"
    *   Thiết lập bộ lọc (danh mục, phòng học, trạng thái, thời gian...)
*   **Trưởng bộ môn:**
    *   Chọn "Báo cáo tài sản bộ môn" (chỉ thấy bộ môn mình)
    *   Chọn "Báo cáo yêu cầu" (lịch sử yêu cầu của bộ môn)
    *   Thiết lập bộ lọc (phòng học, trạng thái, thời gian...)
*   **Hệ thống hiển thị báo cáo**
    *   Xem dữ liệu dưới dạng bảng, biểu đồ
    *   Phân tích thông tin
*   **Decision Point:** Cần xuất báo cáo không?
    *   **Có** → UC27: Xuất báo cáo ra file
        *   Chọn định dạng (Excel hoặc PDF)
        *   Xác nhận xuất
        *   Tải file về
    *   **Không** → Tiếp tục xem hoặc chuyển sang báo cáo khác
*   **Đăng xuất** (UC29)
