# ASSET MANAGEMENT SYSTEM (AMS) - REQUIREMENTS SPECIFICATION
**Version:** 2.0 (Restructured based on RDS Template)
**Date:** 2026-01-15

---

# I. OVERVIEW

## 1. System Context
The Asset Management System (AMS) is a web-based platform designed for public high schools. It streamlines the lifecycle of assets—from the initial request, procurement, allocation, maintenance, to final liquidation. It replaces manual, paper-based tracking and Excel spreadsheets with a centralized digital ledger to ensure accountability, transparency, and efficiency in school resource management.

The system interacts with various stakeholders within the school (Principal, Finance Head, Staff, Teachers) to facilitate asset tracking and reporting.

## 2. External Entities
| Entity | Description |
| :--- | :--- |
| **Principal (Hiệu trưởng)** | The highest authority in the school, responsible for final approval of procurement and high-value asset transfers. Needs comprehensive reports for decision making. |
| **Finance Head (Trưởng phòng TC-KT)** | Responsible for managing the asset budget, approving purchases, overseeing the asset registry, and generating financial reports. |
| **Asset Staff (Nhân viên quản lý tài sản)** | The primary operator of the system. Responsibilities include physical inventory, updating asset status, managing maintenance requests, and executing transfers. |
| **Head of Department (Trưởng bộ môn)** | Responsible for assets assigned to their department. Initiates requests for new equipment, reports damages, and approves transfers within their department. |

## 3. Business Processes
The system supports the following core business processes:

*   **category_management_flow:** Finance Head creates and manages asset categories -> System generates codes automatically.
*   **acquisition_flow:** HOD Request -> Asset Staff Checks Inventory -> Finance Head Approves -> Procurement (Outside System) -> Asset Staff Inputs New Asset -> Asset Assigned.
*   **asset_transfer_flow:** Asset Staff initiates Transfer Ticket -> Finance Head Approves -> Current HOD hands over -> New HOD accepts.
*   **maintenance_flow:** HOD reports damage -> Asset Staff verifies -> Finance Head approves repair -> Status updated to "Maintenance" -> Repair completed -> Status updated to "In Use".
*   **liquidation_flow:** Asset Staff identifies broken/obsolete assets -> Proposal created -> Finance Head & Principal Approve -> Asset Status set to "Liquidated".

## 4. User Requirements
User requirements are defined as Use Cases (UC) categorized by user roles and modules.

*   **Group 1: Category Management** (Primary: Finance Head)
    *   UC01: Create new asset category
    *   UC02: Update category information
    *   UC03: Delete/Deactivate category
    *   UC04: View/Search categories

*   **Group 2: Asset Management** (Primary: Asset Staff, Finance Head)
    *   UC05: Import New Asset (Procurement)
    *   UC06: Update Asset Information
    *   UC07: Update Asset Status (Operational/Broken/Maintenance)
    *   UC08: Search and View Asset Details
    *   UC09: Delete Asset (Soft delete)

*   **Group 3: Acquisition & Procurement** (Primary: HOD, Finance Head)
    *   UC10: Submit Resource Request (HOD)
    *   UC11: Review & Approve Request (Asset Staff/Principal)
    *   UC12: Create Procurement Plan
    *   UC13: Approve Procurement Plan

*   **Group 4: Transfer & Handover** (Primary: Asset Staff, HOD)
    *   UC14: Create Transfer Ticket
    *   UC15: Approve/Reject Transfer Ticket
    *   UC16: Confirm Handover (Sending Dept)
    *   UC17: Confirm Receipt (Receiving Dept)

*   **Group 5: Reporting** (Primary: Principal, Finance Head)
    *   UC21: Generate Asset Inventory Report
    *   UC22: Generate Maintenance/Repair Report
    *   UC23: View Dashboard (Statistics)

*   **Group 6: Common** (All Users)
    *   UC28: Login
    *   UC29: Logout
    *   UC30: Change Password
    *   UC31: Update Profile
    *   UC32: Notification System

## 5. System Functionalities

### 5.1. Screens Flow
The system navigation is designed based on user roles. Below are the primary screen flows:

*   **Authentication Flow (All Users):**
    *   `Login Screen` -> (Success) -> `Role-Based Dashboard`
    *   `Login Screen` -> (Forgot Password) -> `Password Recovery Screen` -> (Email Sent) -> `Reset Password Screen`

*   **Asset Management Flow (Asset Staff/Finance Head):**
    *   `Dashboard` -> `Asset List` -> `Asset Details` -> `Edit Asset` -> `Save`
    *   `Dashboard` -> `Import Asset` -> `Preview Import` -> `Confirm Import`

*   **Request & Procurement Flow:**
    *   **Requester (HOD):** `Dashboard` -> `My Requests` -> `Create New Request` -> `Submit` -> `Request Details (Pending)`
    *   **Approver (Staff/Principal):** `Dashboard` -> `Pending Approvals List` -> `Request Details` -> `Approve/Reject` with Comment

*   **Transfer Flow:**
    *   `Dashboard` -> `Transfer Management` -> `Create Transfer Ticket` -> `Select Asset & Destination` -> `Submit for Approval`
    *   `Dashboard` -> `Transfer Tasks` -> `Confirm Handover(Source)` -> `Confirm Receipt(Destination)` -> `Complete`

### 5.2. Screen Authorization Matrix
Key: **C** (Create), **R** (Read/View), **U** (Update/Edit), **D** (Delete), **A** (Approve), **X** (No Access)

| Feature / Module | Principal | Finance Head | Asset Staff | HOD |
| :--- | :---: | :---: | :---: | :---: |
| **Dashboard** | R (Executive) | R (Financial) | R (Operational) | R (Dept) |
| **Category Mgmt** | R | C, R, U, D | R | X |
| **Asset Mgmt** | R | C, R, U, D | C, R, U | R (Dept Assets) |
| **Request Creation** | C, R | C, R | C, R | C, R |
| **Request Approval** | **A** (High Value) | **A** (Budget) | R | X |
| **Procurement Plan** | **A** | C, R, U | R | R |
| **Transfer Ticket** | **A** | **A** | C, R, U | **A** (Dept Transfer) |
| **Reports** | R, Export | R, Export | R (Basic) | R (Dept) |
| **User Mgmt** | R | R | X | X |

### 5.3. Non-UI Functions (System Services)
These functions run in the background without direct user interaction via screens:

*   **SCH-01: Asset Code Generator:**
    *   **Trigger:** When a new asset is imported or created.
    *   **Logic:** Generates a unique ID based on format: `[CATEGORY-PREFIX]-[YEAR]-[SEQ]` (e.g., `LAP-2024-0056`). Ensures no duplicates in DB.

*   **SCH-02: Depreciation Calculator (Scheduled Job):**
    *   **Trigger:** Runs automatically on the 1st of every month (Cron job).
    *   **Logic:** Updates the "Current Value" of assets based on the purchase date, initial value, and predefined annual depreciation rate (e.g., 10%/year).

*   **SCH-03: Status Auto-Update:**
    *   **Trigger:** When a Transfer Ticket is completed (Both Handover and Receipt confirmed).
    *   **Logic:** Automatically updates the `Current Room` field of the Asset entity to the new location and sets Asset Status to "In Use".

*   **SEC-01: Session Timeout:**
    *   **Logic:** Automatically logs out users after 30 minutes of inactivity to ensure security on shared school computers.

*   **NOT-01: Email Notification Service:**
    *   **Trigger:** Events like "New Request Submitted", "Transfer Approved".
    *   **Logic:** Asynchronously sends an HTML email to the relevant Approver or Requester using SMTP.

---

# II. FUNCTIONAL REQUIREMENTS

## 1. Feature Group: Category Management (Map to UC01-UC04)
This module allows the Finance Head to manage standardized asset classifications.

### 1.1. Category List (UC04)
*   **Screen:** `Category Management List`
*   **Description:** Displays a table of all asset categories (e.g., "IT Equipment", "Lab Tables").
*   **Operations:** Search by name, Filter by status (Active/Inactive).
*   **Fields:** Category ID, Name, Prefix Code (e.g., "COMP"), Description, Status.

### 1.2. Create/Update Category (UC01, UC02)
*   **Screen:** `Category Details Form`
*   **Description:** Modal or separate page to input category details.
*   **Validation:** "Prefix Code" must be unique (used for asset ID generation).
*   **Logic:** Creating a category automatically enables it for selection in Asset Import.

### 1.3. Delete/Deactivate Category (UC03)
*   **Function:** `Soft Delete`
*   **Description:** Marking a category as "Inactive".
*   **Constraint:** Cannot delete a category if it already contains assets.

## 2. Feature Group: Asset Management (Map to UC05-UC09)
This is the core operational module for Asset Staff.

### 2.1. Asset Dashboard & List (UC08)
*   **Screen:** `Asset Registry`
*   **Description:** The central view of all school assets.
*   **Features:**
    *   **Advanced Search:** By Code, Name, Room, category.
    *   **Quick Scan:** Search input focused for Barcode scanner.
    *   **Status Filter:** View only "Broken" or "In Use" items.

### 2.2. Import New Asset (UC05)
*   **Screen:** `New Asset Entry`
*   **Description:** Registering new assets into the system, typically after procurement.
*   **Fields:** Name, Category (Select), Supplier, Cost, Purchase Date, Warranty Date, Initial Room Location.
*   **Logic:** System auto-generates `Asset Code` upon specific Category selection (SCH-01).

### 2.3. Edit Asset Info (UC06)
*   **Screen:** `Asset Details View` -> `Edit Mode`
*   **Description:** Updating non-critical info (e.g., Description, Image) or correcting typos.
*   **Constraint:** Cannot change `Asset Code` or `Purchase Date` without Admin privileges.

### 2.4. Update Asset Status (UC07)
*   **Screen:** `Asset Status Dialog`
*   **Description:** Manually changing status (e.g., from "In Use" to "Broken" or "Under Maintenance").
*   **Requirement:** Must provide a comment/reason when changing status to Broken.

### 2.5. Delete Asset (UC09)
*   **Function:** `Archive Asset`
*   **Description:** Removing an asset record (Soft delete). Only allowed for "Wrong Entry" or "Draft" assets. Liquidated assets use a different process (Liquidation).

## 3. Feature Group: Acquisition & Procurement (Map to UC10-UC13)
Handles the flow from "Need" to "Plan".

### 3.1. Request Submission (UC10)
*   **Screen:** `My Requests` -> `New Request`
*   **Actor:** HOD.
*   **Description:** Form to request general supplies or specific equipment.
*   **Fields:** Item Name, Quantity, Urgency (Low/Med/High), Justification, Desired Date.

### 3.2. Request Approval (UC11)
*   **Screen:** `Pending Approvals`
*   **Actor:** Finance Head, Principal.
*   **Description:** List of requests waiting for action.
*   **Actions:** Approve, Reject (with reason), Request Info.

### 3.3. Procurement Plan Management (UC12, UC13)
*   **Screen:** `Procurement Planning`
*   **Actor:** Finance Head.
*   **Description:** Aggregating approved requests into a purchasing plan.
*   **Logic:** Once a plan is Approved (UC13) by Principal, the status of linked Requests changes to "In Procurement".

## 4. Feature Group: Transfer & Handover (Map to UC14-UC17)
Strictly controls asset movement between locations/owners.

### 4.1. Create Transfer Ticket (UC14)
*   **Screen:** `New Transfer Ticket`
*   **Actor:** Asset Staff.
*   **Description:** Initiating a move.
*   **Fields:** Source Room, Destination Room, List of Assets (Multi-select), Transfer Date, Reason.

### 4.2. Ticket Approval (UC15)
*   **Screen:** `Transfer Approvals`
*   **Actor:** Finance Head / Principal.
*   **Description:** Required for high-value or inter-department transfers.

### 4.3. Handover & Receipt (UC16, UC17)
*   **Screen:** `My Transfer Tasks`
*   **Actor:** HOD (Source & Destination).
*   **Description:** Digital signature/confirmation step.
    *   **Step 1:** Source HOD clicks "Confirm Handover" -> Assets transit.
    *   **Step 2:** Dest HOD clicks "Confirm Receipt" -> Transfer Closing.
*   **Logic:** Trigger SCH-03 (Update Asset Location) upon Step 2.

## 5. Feature Group: Maintenance & Reporting (Map to UC18-UC27)
*(Assuming UC18-20 are Maintenance, UC21-27 are Reporting based on typical AMS structure)*

### 5.1. Maintenance Feature (UC18, UC19, UC20)
*   **UC18: Report Damage:** HOD reports an issue -> Asset Status becomes "Reported".
*   **UC19: Verify Damage:** Asset Staff checks -> Status becomes "Broken" or "Maintenance".
*   **UC20: Record Repair Result:** Updates cost of repair and sets status back to "In Use".

### 5.2. Reporting Module (UC21-UC27)
*   **Screen:** `Reports Hub`
*   **UC21: Asset Inventory Report:** PDF export of all assets by Room/Category.
*   **UC22: Maintenance History Report:** List of all repairs and costs.
*   **UC23: Transfer History Log:** Audit trail of movements.
*   **UC24: Budget/Procurement Report:** Financial summary.
*   **UC25: Dashboard Stats (Principal):** Charts (Pie/Bar).
*   **UC26: Depreciation Report:** Value loss analysis.
*   **UC27: Cancellation/Liquidation Report:** List of disposed assets.

## 6. Feature Group: Common System Functions (Map to UC28-UC32)
### 6.1. Authentication (UC28, UC29)
*   **Screen:** `Login Page`
*   **Features:** JWT Authentication, Session management, Logout.

### 6.2. User Profile (UC30, UC31)
*   **Screen:** `My Profile`
*   **Features:** Change Password (UC30), Update Email/Phone (UC31).

### 6.3. Notifications (UC32)
*   **Screen:** `Notification Bell` (Top bar)
*   **Features:** Real-time dropdown list of alerts (e.g., "Your request #123 was approved"). Also handles sending of emails (Non-UI).

---

# III. SYSTEM DESIGN

## 1. Software Architecture
*   **Architecture Pattern:** MVC (Model-View-Controller) using JSP/Servlet.
*   **Frontend:** JSP, HTML5, CSS3, JavaScript (Bootstrap/Plain CSS).
*   **Backend:** Java Servlets.
*   **Database:** MySQL.
*   **External Diagram:** (See `architecture.png` - *to be created*)

## 2. Code Package Design
*   `com.ams.controller`: Servlet classes handling HTTP requests.
*   `com.ams.model`: JAVA POJO classes representing DB entities (Asset, User, Category).
*   `com.ams.dao`: Data Access Object classes for DB interaction.
*   `com.ams.service`: Business logic layer.
*   `com.ams.utils`: Helper classes (DBConnection, PasswordHasher).

## 3. Database Design
### 3.1. Database Schema
*(Placeholder for ERD Image)*

### 3.2. Table Descriptions
*   **Users:** `user_id` (PK), `username`, `password`, `role`, `full_name`, `email`.
*   **Categories:** `category_id` (PK), `name`, `code_prefix`.
*   **Assets:** `asset_id` (PK), `category_id` (FK), `name`, `code`, `status`, `location_id` (FK).
*   **Requests:** `request_id` (PK), `requester_id` (FK), `type`, `status`, `created_date`.
*   **Transfers:** `transfer_id` (PK), `asset_id` (FK), `from_room`, `to_room`, `status`.
