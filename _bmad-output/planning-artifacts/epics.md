---
stepsCompleted:
  - step-01-validate-prerequisites
  - step-02-design-epics
  - step-03-create-stories
  - step-04-final-validation
inputDocuments:
  - _bmad-output/analysis/vision-and-scope.md
  - _bmad-output/analysis/use case.md
  - _bmad-output/design/ui-mockups.md
  - _bmad-output/analysis/context-diagram.md
  - _bmad-output/SQL/db_final.sql
---

# swp391 - Epic Breakdown

## Overview

This document provides the complete epic and story breakdown for swp391, decomposing the requirements from the PRD, UX Design if it exists, and Architecture requirements into implementable stories.

## Requirements Inventory

### Functional Requirements

FR1: Category Management
- System must allow creating, updating, and deleting asset categories.
- System must support hierarchical category management.
- System must allow searching and filtering categories.
- System must generate automatic asset codes based on category prefix.

FR2: Asset Management
- System must allow adding new assets with detailed technical and financial info.
- System must track asset lifecycle status (New, In Use, Maintenance, Broken, Liquidated, Lost).
- System must allow updating asset details and viewing full history.
- System must allow searching assets by multiple criteria (code, name, category, location, status).

FR3: Acquisition & Procurement
- System must allow HODs to create allocation requests.
- System must allow checking stock availability for requests.
- System must allow Staff/Finance to approve or reject allocation requests.
- System must allow consolidating requests into Procurement Plans for Principal approval.

FR4: Internal Asset Transfer
- System must allow creating transfer tickets (Source Room -> Dest Room).
- System must support transfer approval workflow by Finance Head.
- System must require 2-step confirmation: Sender confirms Handover, Receiver confirms Receipt.
- System must track transfer history for each asset.

FR5: Maintenance & Repair
- System must allow reporting broken assets (with image proof).
- System must track repair progress (Reported -> In Progress -> Fixed/Cannot Fix).
- System must record repair costs and technician notes.

FR6: Asset Liquidation
- System must allow creating liquidation minutes for broken/expired assets.
- System must support Principal approval for liquidation.
- System must auto-update asset status to 'Liquidated' upon approval.

FR7: Reporting & Analytics
- System must provide role-specific dashboards (Principal, Finance, Staff).
- System must generate standard reports (Inventory, Transfer History, Procurement).
- System must allow exporting reports to Excel/PDF.

FR8: System Administration & Security
- System must support Login/Logout and Password Management.
- System must enforce strict Role-Based Access Control (4 roles).
- System must allow users to manage their profiles.

### NonFunctional Requirements

NFR1: Performance
- System must generate dashboards and reports in real-time.
- Application must be responsive on desktop web browsers.

NFR2: Data Integrity & Security
- System must ensure 100% data accuracy for asset status and location (no duplication).
- Access must be strictly restricted based on the 4 defined roles.
- Passwords must be hashed.

NFR3: Usability
- Interface must be simple and user-friendly for non-technical staff.
- Forms should use wizards or split screens for complex flows (as per UX).

NFR4: Reliability & Availability
- System relies on stable Internet connection (No offline mode).

### Additional Requirements

From Architecture (Database & Context):
- **Roles:** Strictly implement 4 roles: Principal, Finance Head, Asset Staff, Head of Dept.
- **Workflow:** Status transitions (e.g., Asset Status, Transfer Status) must follow the state machine defined in SQL/Architecture.
- **Boundaries:** No integration with external Vendor systems or Accounting software.
- **Database:** Use the finalized SQL schema structure for all entities.

From UX Design (ui-mockups.md):
- **Layout:** Admin Dashboard Layout (Sidebar + Header + Content).
- **Principal Dashboard:** Must show KPI Cards + Charts (Bar/Pie) + Action List.
- **Staff Dashboard:** Task-focused (Transfer tickets, Maintenance requests).
- **Transfer Interaction:** Implementation of "My Tasks" view for HOD with separate "Handover" and "Receive" actions.
- **Procurement:** Split-screen UI for selecting requests to add to a Procurement Plan.

### FR Coverage Map

FR1 (Category Management): Epic 1 - System Foundation & Category Management
FR2 (Asset Management): Epic 2 - Asset Inventory Management
FR3 (Acquisition & Procurement): Epic 4 - Acquisition & Procurement Planning
FR4 (Internal Asset Transfer): Epic 3 - Internal Asset Operations
FR5 (Maintenance & Repair): Epic 3 - Internal Asset Operations
FR6 (Asset Liquidation): Epic 2 - Asset Inventory Management
FR7 (Reporting & Analytics): Epic 5 - Consolidated Reporting & Analytics
FR8 (System Administration & Security): Epic 1 - System Foundation & Category Management

## Epic List

### Epic 1: System Foundation & Category Management
**Goal:** Establish the system foundation including security, user roles, and standardized asset categories to prepare for master data entry.
**User Value:** Administrators can manage access control; Finance roles can define standardized asset structures.
**FRs covered:** FR1, FR8

### Epic 2: Asset Inventory Management
**Goal:** Digitize and manage the complete lifecycle of assets from entry to liquidation.
**User Value:** Staff can efficiently track asset status details; Leadership has visibility into the asset registry.
**FRs covered:** FR2, FR6

### Epic 3: Internal Asset Operations
**Goal:** Streamline daily internal operations including asset transfers between rooms and maintenance reporting.
**User Value:** Departments can request repairs and manage asset location changes with clear accountability.
**FRs covered:** FR4, FR5

### Epic 4: Acquisition & Procurement Planning
**Goal:** Manage the end-to-end flow of acquiring new assets, from department requests to consolidation into procurement plans.
**User Value:** Departments can request needed equipment; Finance can optimize purchases by consolidating needs.
**FRs covered:** FR3

### Epic 5: Consolidated Reporting & Analytics
**Goal:** Provide actionable insights and mandatory reports for decision-making and compliance.
**User Value:** Principal and Finance Head can view real-time metrics and export required reports instantly.
**FRs covered:** FR7

----

## Epic 1: System Foundation & Category Management

Establish the system foundation including security, user roles, and standardized asset categories to prepare for master data entry.

### Story 1.1: System Initialization & Role Configuration

As a System Administrator,
I want to initialize the system with defined user roles and basic configuration,
So that the application enforces the correct security model from day one.

**Acceptance Criteria:**

**Given** A fresh database installation
**When** The application starts which runs the Flyway/Liquibase migration
**Then** The database tables (users, roles, departments) are created
**And** The 4 standard roles (Principal, Finance_Head, Asset_Staff, Head_of_Dept) are inserted
**And** The initial 'admin' user is seeded
**And** The project structure in Java/Spring Boot is ready for development

### Story 1.2: User Authentication

As a User (All Roles),
I want to log in to the system securely,
So that I can access features authorized for my role.

**Acceptance Criteria:**

**Given** A registered user account exists
**When** I enter valid username and password on the Login screen
**Then** I am authenticated and redirected to the Dashboard
**Given** An invalid username or password
**When** I attempt to login
**Then** An error message "Invalid credentials" is displayed
**And** Passwords are stored using BCrypt encryption

### Story 1.3: User Profile & Navigation Structure

As a User,
I want to see a navigation menu tailored to my role and manage my profile,
So that I can easily access my functions and keep my info updated.

**Acceptance Criteria:**

**Given** I am logged in as Asset Staff
**When** I view the Sidebar
**Then** I see "Asset Mgmt", "Transfer", "Maintenance" menus
**But** I do not see "Approve Procurement" (Finance function)
**When** I access "My Profile"
**Then** I can update my Phone Number and Avatar

### Story 1.4: Category Management

As a Finance Head,
I want to define asset categories with prefixes (e.g., "Laptop" -> "LAP"),
So that new assets can be automatically coded systematically.

**Acceptance Criteria:**

**Given** I am logged in as Finance Head
**When** I create a new Category "Laboratory Equipment" with prefix "LAB"
**Then** The category is saved to the database
**When** I view the Category List
**Then** I can search for "LAB" and see the entry
**And** I can edit the category name if needed

## Epic 2: Asset Inventory Management

Digitize and manage the complete lifecycle of assets from entry to liquidation.

### Story 2.1: Master Data Management (Rooms & Suppliers)

As a Finance Head,
I want to manage the list of Rooms and Suppliers,
So that assets can be correctly assigned to locations and vendors.

**Acceptance Criteria:**

**Given** I am authorized
**When** I add a Supplier "Phong Vu Computer"
**Then** The supplier is available in the system
**When** I add a Room "Room 101" assigned to "Dept IT"
**Then** The room is recorded and linked to the department

### Story 2.2: Add New Assets

As an Asset Staff,
I want to add new assets to the system,
So that they are tracked in the digital inventory.

**Acceptance Criteria:**

**Given** I have a new laptop to enter
**When** I select Category "Laptop" and enter details (Model: Dell XPS, Price: 30M, Room: 101)
**Then** The system automatically generates Asset Code "LAP-2026-001" (or next sequence)
**And** The asset status is set to "New"
**And** The asset record is saved with purchase date and warranty info

### Story 2.3: Asset List & Search

As a User (All Roles),
I want to search and filter the asset list,
So that I can quickly find specific equipment.

**Acceptance Criteria:**

**Given** The asset list has 100 items
**When** I search for "Dell"
**Then** Only assets with "Dell" in the name or model are shown
**When** I filter by Status "Broken"
**Then** Only broken assets are listed
**And** Staff can see the "Edit" button for each row

### Story 2.4: Asset Details & History

As a User,
I want to view the full details and history of an asset,
So that I know its origin and what happened to it over time.

**Acceptance Criteria:**

**Given** I am viewing the Asset List
**When** I click "View Details" on Asset "LAP-001"
**Then** I see the technical specs, image, and price
**And** I see a "History Timeline" showing "Created", "Transferred", or "Maintained" events sorted by date

### Story 2.5: Update Asset Status & Liquidation

As an Asset Staff,
I want to update an asset's status or mark it for liquidation,
So that the system reflects reality (e.g., item is broken).

**Acceptance Criteria:**

**Given** An asset is currently "In Use"
**When** I change status to "Broken"
**Then** I must provide a reason/note
**When** I create a Liquidation Minute for this asset and Principal approves
**Then** The asset status updates to "Liquidated" automatically

## Epic 3: Internal Asset Operations

Streamline daily internal operations including asset transfers between rooms and maintenance reporting.

### Story 3.1: Create Transfer Ticket

As an Asset Staff,
I want to create a transfer ticket to move assets from one room to another,
So that the movement is officially documented.

**Acceptance Criteria:**

**Given** Asset "PJT-001" is in Room A
**When** I create a ticket to move it to Room B
**Then** The ticket is created with status "Pending"
**And** I can select multiple assets for one ticket

### Story 3.2: Transfer Approval Workflow

As a Finance Head,
I want to approve pending transfer tickets,
So that I control the movement of valuable assets.

**Acceptance Criteria:**

**Given** A pending transfer ticket exists
**When** I approve the ticket
**Then** The status changes to "Approved"
**And** Users involved are notified (via system or status check)
**When** I reject, I must provide a reason

### Story 3.3: Handover and Receipt Confirmation

As a Head of Department,
I want to confirm handing over or receiving assets,
So that accountability is transferred correctly.

**Acceptance Criteria:**

**Given** An Approved transfer ticket from my room to another
**When** I click "Confirm Handover"
**Then** Status becomes "Handing_Over"
**Given** A ticket in "Handing_Over" status coming to my room
**When** I click "Confirm Receipt"
**Then** Status becomes "Completed"
**And** The asset's current location in DB updates to the new Room

### Story 3.4: Report Maintenance Issue

As a Head of Department,
I want to report a broken asset with an image,
So that the technical team knows about the issue.

**Acceptance Criteria:**

**Given** I notice a broken Projector
**When** I submit a maintenance request with a photo and description
**Then** The request is saved with status "Reported"
**And** Asset Staff sees this in their dashboard

### Story 3.5: Process Maintenance Request

As an Asset Staff,
I want to update the progress of a maintenance request,
So that everyone knows if it's being fixed or needs replacement.

**Acceptance Criteria:**

**Given** A reported request
**When** I start fixing it, I update status to "In Progress"
**When** It is fixed, I update status to "Fixed" and enter the Repair Cost
**Then** The related Asset status automatically returns to "In Use"

## Epic 4: Acquisition & Procurement Planning

Manage the end-to-end flow of acquiring new assets, from department requests to consolidation into procurement plans.

### Story 4.1: Create Allocation Request

As a Head of Department,
I want to request new equipment for my department,
So that I have the necessary tools for teaching.

**Acceptance Criteria:**

**Given** I need 5 new chairs
**When** I create a Request specifying "Chair" category and quantity 5
**Then** The request is submitted to the Asset Staff
**And** I can track its status in "My Requests"

### Story 4.2: Review Request & Check Stock

As an Asset Staff,
I want to check if requested items are in stock,
So that I can fulfill them immediately without buying new ones.

**Acceptance Criteria:**

**Given** A request for 5 Chairs
**When** I check stock and find 5 unused chairs
**Then** I can "Approve & Allocate" directly (Asset locations update)
**When** Stock is empty
**Then** I forward the request to "Procurement Needed" status

### Story 4.3: Create Procurement Plan

As a Finance Head,
I want to combine multiple pending requests into a single Procurement Plan,
So that the school can purchase in bulk.

**Acceptance Criteria:**

**Given** 3 different requests for Laptops from different depts
**When** I create a "Q1 Procurement Plan"
**And** I select these 3 requests to include
**Then** The plan calculates the total estimated quantity and budget
**And** The individual requests are linked to this Plan

### Story 4.4: Approve Procurement Plan

As a Principal,
I want to review and approve procurement plans,
So that budget is spent correctly.

**Acceptance Criteria:**

**Given** A submitted plan
**When** I approve it
**Then** The plan status becomes "Approved"
**And** Finance Head can proceed with purchasing

### Story 4.5: Receive Assets from Plan

As an Asset Staff,
I want to mark a procurement plan as completed and enter the new assets,
So that the requesters get their items.

**Acceptance Criteria:**

**Given** An Approved plan has been purchased
**When** I receive the physical goods
**Then** I can use a "Receive All" or similar function to generate the Asset Records
**Or** Manually enter assets and link them to closing the Plan requests

## Epic 5: Consolidated Reporting & Analytics

Provide actionable insights and mandatory reports for decision-making and compliance.

### Story 5.1: Export Inventory Report

As a Finance Head,
I want to export the full asset inventory to Excel/PDF,
So that I can sign and file it for physical auditing.

**Acceptance Criteria:**

**Given** The current asset data
**When** I click "Export Inventory"
**Then** A file is downloaded containing all assets with columns: Code, Name, Room, Value, Status

### Story 5.2: Principal Dashboard

As a Principal,
I want to see high-level metrics on the dashboard,
So that I know the school's asset health instantly.

**Acceptance Criteria:**

**Given** I log in as Principal
**When** I view the dashboard
**Then** I see "Total Asset Value", "Budget Used", and "Pending Approvals" cards
**And** I see a chart of "Asset Status Distribution"

### Story 5.3: Staff Work Dashboard

As an Asset Staff,
I want to see my to-do list on the dashboard,
So that I don't miss any pending tickets or maintenance requests.

**Acceptance Criteria:**

**Given** Pending tasks exist
**When** I log in as Staff
**Then** I see "Pending Transfer Tickets" list
**And** I see "New Maintenance Requests" list ordered by date
