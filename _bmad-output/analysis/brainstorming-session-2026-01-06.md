---
selected_approach: 'progressive-flow'
techniques_used: ['Context Analysis', 'Use Case Mapping', 'Process Design']
stepsCompleted: [1, 2, 3]
---

# Asset Management System (AMS) - Requirements Specification

**Project:** Asset Management System for High Schools (AMS)
**Framework:** JSP/Servlet, Java, MySQL
**Last Updated:** 2026-01-15

---

## I. ORGANIZATIONAL STRUCTURE (5 ROLES)

Based on the official Use Case document, the system defines 5 core users:

1.  **Principal/Vice Principal (Hiệu trưởng/Phó hiệu trưởng):** High-level management, approves major procurement requests (UC18), views strategic dashboards.
2.  **Head of Finance & Accounting (Trưởng phòng TC-KT):** Manages Asset Categories (UC01-03), Approves Transfers (UC22), Liquidates assets (UC10), and views financial reports.
3.  **Asset Management Staff (Nhân viên quản lý tài sản):** Primary operator. Handles daily CRUD of assets (UC05, UC08), Approves Allocation Requests (UC14), Creates Purchase Proposals (UC16), and Initiates Transfers (UC20).
4.  **Head of Department (Trưởng/Phụ trách bộ môn):** Requests assets (UC11), Confirms Handover/Receipt of transfers for their department (UC23, UC24).
5.  **Teacher (Giáo viên):** End user. Requests assets for classrooms (UC11) and Updates asset status/Reports issues (UC09).

---

## II. SYSTEM SCOPE (6 MODULES)

The system is structured into 6 main functional groups corresponding to the 32 Use Cases:

| # | Group | Use Cases | Description |
|---|---|---|---|
| I | **Category Management** | UC01 - UC04 | Managing asset standard templates/types. (Role: Finance Head) |
| II | **Asset Management** | UC05 - UC10 | Managing individual asset records, status updates, and liquidation. |
| III | **Acquisition (Increase)** | UC11 - UC19 | Flow from Allocation Request -> Stock Check -> Purchase Proposal -> Approval. |
| IV | **Asset Transfer** | UC20 - UC25 | Moving assets between rooms/departments (Request -> Approve -> Handover -> Receive). |
| V | **Reporting** | UC26 - UC27 | View and Export reports based on role permissions. |
| VI | **Common** | UC28 - UC32 | Authentication and Profile management. |

---

## III. USER REQUIREMENTS (USE CASE LIST)

### 1. Group 1: Asset Category Management
*   **UC01:** Create New Category (Head of Finance)
*   **UC02:** Update Category Information (Head of Finance)
*   **UC03:** Delete Category (Head of Finance - validation required)
*   **UC04:** View/Search Categories (All Actors)

### 2. Group 2: Asset Management
*   **UC05:** Add New Asset (Asset Staff) - Auto-generate Asset ID (e.g., MT-2024-001).
*   **UC06:** View/Search Assets (All Actors)
*   **UC07:** View Asset Details (All Actors) - Full history and location.
*   **UC08:** Update Asset Info (Asset Staff) - Edit details/value.
*   **UC09:** Update Asset Status (Asset Staff, Teacher) - Report status (New, Used, Broken, Maintenance, Liquidated).
*   **UC10:** Liquidate/Delete Asset (Head of Finance)

### 3. Group 3: Asset Acquisition (Request & Procurement)
*   **UC11:** Create Allocation Request (Teacher, HOD)
*   **UC12:** View Allocation Requests (All involved actors)
*   **UC13:** Check Inventory Availability (Asset Staff)
*   **UC14:** Approve/Reject Personnel Request (Asset Staff)
*   **UC15:** Cancel Allocation Request (Requester)
*   **UC16:** Create Purchase Proposal (Asset Staff - if stock is insufficient)
*   **UC17:** View Purchase Requests (Asset Staff, Finance, Principal)
*   **UC18:** Approve/Reject Purchase Proposal (Principal/VP)
*   **UC19:** Cancel Purchase Proposal (Asset Staff)

### 4. Group 4: Asset Transfer
*   **UC20:** Create Transfer Voucher (Asset Staff) - From Source Room to Target Room.
*   **UC21:** View/Search Transfer Vouchers (Asset Staff, Finance, HOD)
*   **UC22:** Approve/Reject Transfer (Head of Finance)
*   **UC23:** Confirm Handover (HOD - Source Room)
*   **UC24:** Confirm Receipt (HOD - Target Room)
*   **UC25:** Cancel Transfer Voucher (Asset Staff, Finance)

### 5. Group 5: Reporting & Analytics
*   **UC26:** View Reports (Role-based views):
    *   *Principal:* Dashboards, Procurement stats.
    *   *Finance:* Dashboard, Detailed Inventory, Depreciation, Transfers.
    *   *Asset Staff:* Inventory, Repairs/Status, Transfer logs.
    *   *HOD:* Departmental Assets & History.
*   **UC27:** Export Reports (Excel/PDF)

### 6. Group 6: Common Functions
*   **UC28:** Login
*   **UC29:** Logout
*   **UC30:** Profile Management
*   **UC31:** Forgot Password
*   **UC32:** Change Password

---

## IV. KEY PROCESS FLOWS (ALIGNED WITH WORKFLOW.MD)

### 1. Request & Procurement Process
1.  **Start:** Teacher/HOD creates **Allocation Request** (UC11).
2.  **Check:** Asset Staff checks inventory (UC13).
    *   *Case A (Stock Available):* Staff **Approves** (UC14) -> Asset assigned.
    *   *Case B (Out of Stock):* Staff creates **Purchase Proposal** (UC16).
3.  **Approval (If B):** Principal reviews Proposal (UC18).
    *   *Approved:* Items bought -> Staff **Adds to System** (UC05) -> Staff **Approves** original Request (UC14).
    *   *Rejected:* Staff **Rejects** original Request (UC14).

### 2. Asset Transfer Process
1.  **Start:** Staff creates **Transfer Voucher** (UC20).
2.  **Approval:** Head of Finance approves (UC22). (Status: *Approved - Waiting Handover*)
3.  **Handover:** Source HOD confirms handover (UC23). (Status: *Handed Over - Waiting Receipt*)
4.  **Receipt:** Target HOD confirms receipt (UC24). (Status: *Completed*) -> Location updated.

---
*This specification is now fully synchronized with project artifacts `use case.md` and `workflow.md`.*
