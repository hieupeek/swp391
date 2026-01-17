# CONTEXT DIAGRAM DESCRIPTION
**System:** School Asset Management System (AMS)

This document describes the boundary of the AMS system (Scope) by listing all interactions with External Entities.

## 1. Head of Department (Trưởng bộ môn)
*   **Input (-> System):**
    *   `Resource Request`: Request for supplies or equipment for the department.
    *   `Damage Report`: Reporting broken items in department rooms.
    *   `Handover Confirmation`: Confirming assets leaving their department.
    *   `Receipt Confirmation`: Confirming assets arriving at their department.
*   **Output (System ->):**
    *   `Request Status`: Notification of approval or rejection.
    *   `Pending Approval Alert`: Notification of transfers requiring attention.
    *   `Dept Asset Report`: Inventory list of their specific department.

## 2. Asset Staff (Nhân viên tài sản)
*   **Input (-> System):**
    *   `New Asset Details`: Registering new procurements (Name, Price, Model).
    *   `Transfer Ticket Creation`: Initiating movement of assets.
    *   `Maintenance Status Update`: Log repair progress.
*   **Output (System ->):**
    *   `Asset Label Info`: Data for printing physical tags/stickers.
    *   `Task List`: Daily to-do list (Items to repair, Items to move).

## 3. Finance Head (Kế toán trưởng)
*   **Input (-> System):**
    *   `Category Rules`: Defining asset categories and depreciation rates.
    *   `Procurement Approval`: Budgetary approval for purchasing plans.
*   **Output (System ->):**
    *   `Inventory Report`: Full school asset audit.
    *   `Depreciation Statistics`: Financial value of assets over time.

## 4. Principal (Hiệu trưởng)
*   **Input (-> System):**
    *   `High-Value Approval`: Final sign-off for expensive items or liquidation.
*   **Output (System ->):**
    *   `Executive Dashboard`: High-level charts (Budget used, Total Asset Value).

## 5. Barcode Scanner (Hardware)
*   **Input (-> System):**
    *   `Scanned Asset ID`: Barcode string sent typically during inventory checks.
*   **Output (System ->):**
    *   (None - Input only device)

---
**Note on Exclusions (Out of Scope):**
*   **Vendors:** The system does NOT send purchase orders directly to suppliers.
*   **Accounting Software:** The system does NOT sync automatically with external accounting tools (e.g., MISA/SAP) in this version.
