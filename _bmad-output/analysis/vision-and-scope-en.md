---
title: Vision and Scope Document
project: School Asset Management System (AMS)
version: 1.0 (English Version)
date: 2026-01-15
author: Business Analyst Team
template_source: "Software Requirements, 3rd Edition (Wiegers & Beatty) - Chapter 5"
---

# VISION AND SCOPE DOCUMENT
**Project:** School Asset Management System (AMS)

---

## 1. BUSINESS REQUIREMENTS

### 1.1. Background
Currently, asset management in the High School is mainly performed through paper books or fragmented Excel files. This leads to several issues:
*   Difficulty in tracking asset transfer history between classrooms and departments.
*   Protracted processes for reporting damages, repairs, and procurement proposals due to manual approval chains.
*   The Principal lacks an immediate overview of facility status to make informed purchasing decisions.
*   End-of-term inventory checks are time-consuming and inaccurate due to non-centralized data.

### 1.2. Business Opportunity
Implementing the Asset Management System (AMS) presents the following opportunities:
*   **Comprehensive Digitalization:** Transforming manual management processes to a digital platform, ensuring centralized and consistent data storage.
*   **Resource Optimization:** Maximizing the use of existing assets, avoiding wasteful purchases of equipment that already exists but is "forgotten" in storage.
*   **Transparency:** All processes from allocation requests, procurement approvals to transfers are recorded on the system, ensuring compliance and accountability.

### 1.3. Business Objectives
The system aims to achieve the following goals within 6 months of deployment:
*   **BO-1 (Reduce Loss):** Reduce unexplained asset loss to below 1% through strict tracking of transfer history.
*   **BO-2 (Increase Process Efficiency):** Reduce the average processing time for an allocation/procurement request from 3 days to under 4 working hours.
*   **BO-3 (Save Reporting Time):** Reduce 90% of the time required to aggregate periodic inventory reports for accountants and the school board.
*   **BO-4 (Budget Optimization):** Save 10% of the annual procurement budget through effective asset reuse and timely maintenance.

### 1.4. Success Metrics
Project success will be measured by the following metrics:
*   **SM-1:** 100% of valuable assets (over 1 million VND) are tagged and managed in the system.
*   **SM-2:** 95% of teachers and staff proficiently use the system to submit requests and report incidents after 1 month of training.
*   **SM-3:** The system can generate accurate consolidated reports instantly (real-time) instead of waiting for manual aggregation.
*   **SM-4:** Elimination of duplicate asset codes or location discrepancies between physical reality and records.

### 1.5. Vision Statement
> *"For the School Board, managers, and teachers who need to manage and utilize facilities effectively, the School Asset Management System (AMS) is a centralized management platform that provides the ability to track the entire asset lifecycle from procurement to liquidation. Unlike the current manual Excel-based process, our product automates the approval workflow, provides instant visual reports, and guarantees absolute accuracy of inventory data, helping the school optimize budgets and improve the quality of teaching services."*

### 1.6. Business Risks
*   **RI-1 (User Resistance):** Older teachers or staff accustomed to the old ways may be reluctant to use new software.
    *   *Mitigation:* Design a simple interface and organize thorough training sessions.
*   **RI-2 (Initial Data):** Data migrated from old Excel files may be unclean (missing info, wrong codes), leading to discrepancies during initial operation.
    *   *Mitigation:* Implement a data cleansing and physical inventory phase before official data entry.
*   **RI-3 (Internal Process):** Approval workflows on software might be more rigid than real-life flexibility, causing bottlenecks if approvers are absent.
    *   *Mitigation:* Allow approval delegation or flexible workflow configuration.

### 1.7. Business Assumptions and Dependencies
*   **AS-1:** It is assumed that the school has a stable Internet infrastructure in departments and classrooms.
*   **AS-2:** It is assumed that the School Board is committed to enforcing the use of software to completely replace paper books (no prolonged parallel systems).
*   **DE-1:** The project depends on an accurate list of asset codes and department/staff lists provided by the Administration Office before launch.

## 2. SCOPE AND LIMITATIONS

### 2.1. Major Features
The system is organized into 6 main feature groups:

*   **FE-1: Category Management**
    *   Create, update, and manage standard asset classifications (e.g., Furniture, Computers, Lab Equipment).
    *   Set up automatic coding rules for each category.

*   **FE-2: Asset Management**
    *   Record new assets with full details (Model, Serial, Value, Supplier).
    *   Update lifecycle status: New -> In Use -> Broken/Maintenance -> Liquidated.
    *   Search and view detailed history of each asset.

*   **FE-3: Acquisition**
    *   Submit allocation requests from Teachers/HODs.
    *   Automatically check inventory stock.
    *   Create and approve Procurement Proposals (if out of stock).

*   **FE-4: Asset Transfer**
    *   Strict 4-step transfer process: Create Ticket -> Finance Approval -> Handover (Source) -> Receipt Confirmation (Dest).
    *   Track movement history for accountability.

*   **FE-5: Reporting**
    *   Dashboard for Principal (Asset Overview, Procurement Budget).
    *   Detailed reports for Finance (Depreciation, Inventory, Liquidation).

*   **FE-6: Common Functions**
    *   Secure Login/Logout.
    *   Profile management and password change.

### 2.2. Scope of Initial and Subsequent Releases

The table below describes the feature development roadmap across versions:

| Feature Group | Release 1.0 (Current MVP) | Release 2.0 (Future Enhancement) |
| :--- | :--- | :--- |
| **FE-1: Category Mgmt** | **Full:** Create, edit, delete, permission mgmt. | - |
| **FE-2: Asset Mgmt** | **Core:** Manual entry, status checks, basic search. | **Advanced:** Automatic Depreciation Calculation. |
| **FE-3: Acquisition** | **Basic:** Create request, 2-level approval (HOD -> Principal). | **Integration:** API connection to send auto-emails to Vendors. |
| **FE-4: Asset Transfer** | **Full:** Internal transfer workflow & Handover confirmation. | **History Visualization:** Visual asset flow charts. |
| **FE-5: Reporting** | **Standard:** Export inventory/dept reports (PDF/Excel). | **Analytics:** AI-driven budget forecasting & trends dashboard. |
| **FE-6: Common** | **Basic Auth:** Login, Role-based access, Password change. | **SSO:** Single Sign-On integration with school email. |
| **Platform Support** | **Web App (Desktop Focus):** Optimized for PCs/Laptops. | **Mobile Web:** Optimized interface for HOD mobile approvals. |

*Note: Release 1.0 focuses entirely on replacing manual paper processes with a digital Web-based process.*

### 2.3. Limitations and Exclusions
*   **EX-1 (No Detail Financial Mgmt):** The system records initial purchase value and approves proposals but DOES NOT perform payments, invoicing, or detailed accounting bookkeeping.
*   **EX-2 (No Vendor Interaction):** The system does not send orders directly to suppliers (Vendors). Contact and purchasing happen outside the system.
*   **EX-3 (No Offline Support):** The system requires a continuous Internet connection; there is no offline-sync mode.
*   **EX-4 (No Mobile App yet):** The initial version is a Web Application; the interface may be responsive but there is no native App for iOS/Android.


## 3. BUSINESS CONTEXT

### 3.1. Stakeholder Profiles

| Stakeholder | Value/Benefit | Attitude | Key Interests | Constraints |
| :--- | :--- | :--- | :--- | :--- |
| **Principal** | Transparency of public assets, optimized budget spending. | Strongly supportive, expects quick overview reports. | Tracking total asset value, approving large purchases, avoiding loss. | Limited time, does not use system frequently (only views dashboard). |
| **Finance Head** | Reduced inventory time, accurate depreciation reporting. | Highly concerned with data accuracy. | Standard category management, control procurement process, settlement reports. | System must comply with state accounting regulations. |
| **Asset Staff** | Reduced manual entry load (Excel), easy asset location tracking. | Willing to learn new tech to reduce manual labor. | Tracking asset lifecycle (in, out, broken, liquidated), printing labels/barcodes. | IT skills may vary, requires simple interface. |
| **Head of Dept (HOD)** | clear view of dept assets, proactive in new purchase proposals. | Desires streamlined approval process. | Managing lab/functional room assets, confirming internal handovers. | Only interested in assets within their management scope. |

### 3.2. Project Priorities
The table below defines the project priorities across three dimensions to facilitate decision-making:

| Dimension | Constraint (Must adhere to) | Driver (Key success factor) | Degree of Freedom (Adjustable) |
| :--- | :--- | :--- | :--- |
| **Schedule** | Must complete Release 1.0 before [End Date] for acceptance. | | |
| **Budget** | Implementation cost ~0 (Using student manpower, free tier). | | |
| **Quality** | | Data accuracy is paramount. System stability. | |
| **Usability** | | Extremely friendly interface for non-IT staff. | |
| **Scope** | | | Advanced features (Mobile App, Accounting API) can be cut or delayed to Release 2.0. |

### 3.3. Deployment Considerations
*   **Technical Infrastructure:**
    *   System deployed as a **Web Application** on internal School Server or low-cost Cloud Server.
    *   Requires modern web browsers (Chrome, Edge, Firefox) on staff workstations.

*   **Data Migration:**
    *   Major challenge. Need a powerful **Excel Import** tool to load thousands of rows of legacy asset data from scattered Excel files upon Go-live.
    *   Process for Data Cleansing required before import.

*   **Training & Support:**
    *   Centralized training session for Asset Team and Finance Dept (Detailed User Manual).
    *   Quick Guide for Teachers on reporting damages and creating requests.

*   **Maintenance & Backup:**
    *   Establish automatic Daily Database Backup mechanism to prevent data loss.
