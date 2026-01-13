---
selected_approach: 'progressive-flow'
techniques_used: ['What If Scenarios', 'Context Analysis', 'Mind Mapping', 'SCAMPER Method', 'Solution Matrix']
stepsCompleted: [1, 2]
---

# Software Requirements Specification (SRS) - Group 6

**Topic:** Asset Management System (AMS)
**Context:** Public High School
**Date:** 2026-01-12

---

## I. Overview

### 1. System Context
The Asset Management System (AMS) is a web-based platform designed for public high schools. It streamlines the lifecycle of assets—from the initial request to the Ministry of Education, through intake, allocation, maintenance, and final return or liquidation. It replaces manual, paper-based tracking with a digital ledger to ensure accountability and efficiency.

### 2. External Entities
| # | Entity | Description |
|---|--------|-------------|
| 1 | **Guest** | Anonymous users (Students/Visitors/Teachers) who report damaged property via QR codes. |
| 2 | **Facilities Staff** | Operational users who manage inventory, verify reports, and handle physical asset movements. |
| 3 | **Vice Principal** | Secondary approver and internal overseer of facility status and maintenance requests. |
| 4 | **Principal** | High-level approver for provisioning requests and consumer of executive reports. |

### 3. Business Processes
The system follows the "Provisioning & Handover" model typical of public institutions:
- **Provisioning Flow:** Staff summarizes needs -> Vice Principal reviews -> Principal approves -> Export PDF to Ministry.
- **Intake Flow:** Ministry delivers items -> Staff performs physical verify -> Input data into System (Inventory Addition).
- **Maintenance Flow:** Guest/Staff reports damage -> Staff verifies -> Vice Principal approves repair -> Staff records result.

---

## 4. User Requirements
This section identifies all Use Cases (UC) for the system actors.

| ID | Use Case | Feature | Main Actor | Use Case Description |
|---|---|---|---|---|
| UC01 | Report Damage | Maintenance/Repair | Guest | Report asset issues via QR code without login (photo + description). |
| UC02 | View Inventory | Asset Management | Facilities Staff | Browse, filter, and search all school assets. |
| UC03 | Add Inventory | Inventory Addition | Facilities Staff | Input new items from Ministry documentation. |
| UC04 | Allocate Asset | Asset Allocation | Facilities Staff | Assign assets to specific rooms or personnel. |
| UC05 | Create Provisioning | Provisioning | Facilities Staff | Compile needs and generate request documents. |
| UC06 | Review Request | Provisioning | Vice Principal | Initial review/revision of provisioning or maintenance requests. |
| UC07 | Final Approval | Provisioning | Principal | Final check and formal approval of annual asset requests. |
| UC08 | View Dashboard | Reports/Stats | Principal | Access interactive charts and summary reports of school CSVC. |
| UC09 | Manage Master Data| System Admin | Facilities Staff | Coordinate settings (Room types, Asset categories, Statuses). |

### 4.1 UCs for Guest
- **UC01: Report Damage:** Allows any person on site to scan a QR code on an object/room to report a fault.

### 4.2 UCs for Facilities Staff
- **UC02-UC05, UC09:** Full operational control over assets, allocations, and system master data.

### 4.3 UCs for Vice Principal & Principal
- **UC06-UC08:** High-level oversight, multi-stage approval workflow, and strategic reporting.

---

## 5. System Functionalities

### 5.1 Screens Flow
- **Public:** Landing Page (QR Form) -> Success Message.
- **Internal:** Login -> Dashboard -> [Module Lists] -> [Detail Views] -> [Approval Workflows].

### 5.2 Screen Authorization
| Screen Name | Guest | Facilities Staff | Vice Principal | Principal |
|---|:---:|:---:|:---:|:---:|
| Anonymous Report Form | X | | | |
| Dashboard Overview | | X (View) | X (View) | X (Full) |
| Asset Inventory List | | X (Full) | X (View) | X (View) |
| Provisioning Workflow | | X (Create) | X (Review) | X (Approve) |
| Master Data Settings | | X (Full) | | |
| User Management | | X (Admin) | | |

### 5.3 Non-UI Functions
| # | Feature | System Function | Description |
|---|---|---|---|
| 1 | Provisioning | PDF Generator | Generates the official Ministry request form as a PDF document. |
| 2 | QR Management | QR Seed Generator | System generates unique URLs for each asset/room to be printed as QR codes. |
| 3 | Notifications | Activity Logger | Tracks all approval steps (Who, When, Action) for audit purposes. |

---

## II. Functional Requirements

### 1. Main Features (Group 6 Asset Modules)
Based on the defined scope, the system includes 10 core modules:

#### 1.1 Core Inventory Modules (Mandatory)
- **M01. Asset Category Management:** Manage classification (Furniture, IT, Lab Equipment).
- **M02. Asset Management:** Detailed ledger of every asset (Batch or Single item tracking).
- **M05. Asset Inventory Addition:** Recording new arrivals from the Ministry.
- **M08. Asset Transfer/Movement:** Moving assets between rooms or departments.
- **M09. Asset Allocation/Handover:** Assigning responsibility to specific personnel/rooms.
- **M10. Reports and Statistics:** Dashboard and PDF/Excel export for school leadership.

#### 1.2 Extended Workflow Modules (Optional/Added)
- **M03. Provisioning Request Process:** Collecting needs and generating the Ministry request form.
- **M04. Asset Intake Process:** Structured verification of goods arriving from external sources.
- **M06. Maintenance/Repair Process:** Tracking the repair lifecycle including Guest QR reporting.
- **M07. Asset Return/Liquidation Process:** Formal process for returning items to the Ministry or decommissioning.

### 2. User Authentication
- **F01. Login/Logout:** Secure access for defined roles.
- **F02. Profile Management:** Users can update their personal information.

### 3. System Administration (Standard RDS Requirements)
#### 3.1 Master Data (Settings)
- **F03. Setting List:** View, filter, and search master data (e.g., Asset Statuses, Room Types, Roles).
- **F04. Setting Details:** Add/Update master data entries, including setting priority and status (Active/Inactive).

#### 3.2 User Management
- **F05. User List:** Administrator can view and manage all system users (Principal, Staff, etc.).
- **F06. Role Authorization:** Assigning permissions to specific modules based on organizational structure.

---

## III. System Design

### 1. Context Diagram (Data Flow)
- **Guest** -> [Damage Description, Photo] -> **System**
- **Facilities Staff** -> [Inventory Data, Allocation Update, Verification] -> **System**
- **Vice Principal** -> [Approval Commands] -> **System**
- **Principal** -> [Final Approval, Report Request] -> **System**
- **System** -> [PDF Asset Forms, Handover Receipts, Dashboard Charts] -> **Users**

### 2. Database Design (Entity Relationships)
- **Entities:** `Users`, `Roles`, `Settings`, `Assets`, `Categories`, `Rooms`, `Allocations`, `Transfers`, `MaintenanceRequests`.

---
*This document aligns with the Group6 RDS structuring requirements and the provided project scope.*
