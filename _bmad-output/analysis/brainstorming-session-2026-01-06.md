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
| **Head of Department (Trưởng bộ môn)** | Responsible for assets assigned to their department. Initiates requests for new equipment and approves transfers within their department. |
| **Teacher (Giáo viên)** | End-users of assets. They report issues (damages/loss) and can view the assets assigned to the rooms they are teaching in. |

## 3. Business Processes
The system supports the following core business processes:

*   **category_management_flow:** Finance Head creates and manages asset categories -> System generates codes automatically.
*   **acquisition_flow:** Teacher/HOD Request -> Asset Staff Checks Inventory -> Finance Head Approves -> Procurement (Outside System) -> Asset Staff Inputs New Asset -> Asset Assigned.
*   **asset_transfer_flow:** Asset Staff initiates Transfer Ticket -> Finance Head Approves -> Current HOD hands over -> New HOD accepts.
*   **maintenance_flow:** Teacher reports damage -> Asset Staff verifies -> Finance Head approves repair -> Status updated to "Maintenance" -> Repair completed -> Status updated to "In Use".
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

*   **Group 3: Acquisition & Procurement** (Primary: Teacher, HOD, Finance Head)
    *   UC10: Submit Resource Request (Teacher/Staff)
    *   UC11: Review & Approve Request (HOD/Principal)
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
    *   **Requester (Teacher):** `Dashboard` -> `My Requests` -> `Create New Request` -> `Submit` -> `Request Details (Pending)`
    *   **Approver (HOD/Principal):** `Dashboard` -> `Pending Approvals List` -> `Request Details` -> `Approve/Reject` with Comment

*   **Transfer Flow:**
    *   `Dashboard` -> `Transfer Management` -> `Create Transfer Ticket` -> `Select Asset & Destination` -> `Submit for Approval`
    *   `Dashboard` -> `Transfer Tasks` -> `Confirm Handover(Source)` -> `Confirm Receipt(Destination)` -> `Complete`

### 5.2. Screen Authorization Matrix
Key: **C** (Create), **R** (Read/View), **U** (Update/Edit), **D** (Delete), **A** (Approve), **X** (No Access)

| Feature / Module | Principal | Finance Head | Asset Staff | HOD | Teacher |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **Dashboard** | R (Executive) | R (Financial) | R (Operational) | R (Dept) | R (Personal) |
| **Category Mgmt** | R | C, R, U, D | R | X | X |
| **Asset Mgmt** | R | C, R, U, D | C, R, U | R (Dept Assets) | R (Room Assets) |
| **Request Creation** | C, R | C, R | C, R | C, R | C, R |
| **Request Approval** | **A** (High Value) | **A** (Budget) | R | **A** (Dept Level) | R (Own only) |
| **Procurement Plan** | **A** | C, R, U | R | R | X |
| **Transfer Ticket** | **A** | **A** | C, R, U | **A** (Dept Transfer) | X |
| **Reports** | R, Export | R, Export | R (Basic) | R (Dept) | X |
| **User Mgmt** | R | R | X | X | X |

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

## 1. Feature: Account Management (Common)
### 1.1. Login (UC28)
*   **Screen:** Login Page
*   **Description:** Allows users to access the system using Email/Username and Password.
*   **Fields:** Username, Password, Remember Me (checkbox), Login Button, Forgot Password Link.

## 2. Feature: Asset Management
### 2.1. Feature: Category Management
#### 2.1.1. List Categories (UC04)
*   **Description:** Display a paginated list of asset categories.
*   **Fields:** Category Code, Category Name, Description, Active Status, Action Buttons (Edit, Delete).

#### 2.1.2. Create/Edit Category (UC01, UC02)
*   **Description:** Form to add or update an asset category.
*   **Fields:** Name (Required), Prefix Code (Required, unique), Description.

### 2.2. Feature: Asset Lifecycle
#### 2.2.1. Asset List (UC08)
*   **Description:** Main operational screen for Asset Staff. Filters by Category, Status, Room.
*   **Fields:** Asset Code, Name, Category, Purchase Date, Price, Status (New, Good, Broken, Maintenance, Liquidated), Current Room.

#### 2.2.2. Import Asset (UC05)
*   **Description:** Form to register a new asset into the system (usually after procurement).
*   **Fields:** Name, Category (Dropdown), Supplier, Price, Purchase Date, Warranty Expiry, Initial Status, Image Upload.

## 3. Feature: Business Processes
### 3.1. Transfer Management
#### 3.1.1. Transfer Ticket List
*   **Description:** Tracks all movement of assets.
*   **Fields:** Ticket ID, Asset Name, From Room, To Room, Created By, Status (Pending, Approved, Completed).

#### 3.1.2. Create Transfer Ticket (UC14)
*   **Description:** Asset Staff selects an asset and a destination room.
*   **Fields:** Asset (Searchable), Destination Room, Reason for Transfer, Transfer Date.

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
