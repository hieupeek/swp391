# Story 2.1: Master Data Management (Rooms & Suppliers)

## Status: DONE

## Story Details

**As a** Finance Head,
**I want to** manage the list of Rooms and Suppliers,
**So that** assets can be correctly assigned to locations and vendors.

## Acceptance Criteria

### AC 1: Manage Suppliers
- [x] **Given** I am logged in as Finance Head
- [x] **When** I add/edit a Supplier (Name, Contact Info)
- [x] **Then** The supplier is saved to the database

### AC 2: Manage Rooms
- [x] **Given** I am logged in as Finance Head
- [x] **When** I add/edit a Room (Name, Capacity) and assign it to a Department
- [x] **Then** The room is saved with proper department linkage

### AC 3: View Access
- [x] **Given** I am logged in as Asset Staff
- [x] **Then** I can view the lists but cannot add/edit

## Tasks

### Task 1: Backend - Models & DAOs
- [x] 1.1. Create `Room` and `Supplier` models
- [x] 1.2. Create `RoomDAO` and `SupplierDAO` interfaces & impls
- [x] 1.3. Create `RoomService` and `SupplierService`

### Task 2: Frontend - Controllers & Views
- [x] 2.1. Create `SupplierServlet` (CRUD)
- [x] 2.2. Create `views/supplier/list.jsp` & `form.jsp`
- [x] 2.3. Create `RoomServlet` (CRUD)
- [x] 2.4. Create `views/room/list.jsp` & `form.jsp`

### Task 3: Security
- [x] 3.1. Enforce Role-Based Access (Finance Head: Write, Asset Staff: Read)

## Technical Notes

- **Access Control:** implemented in `RoomServlet` and `SupplierServlet`.
- **Sidebar:** Updated to include Rooms and Suppliers menus.
- **Validation:** Server-side validation for required fields and unique checks.

## Dev Notes

- Started: 2026-01-23
- Completed: 2026-01-23
- Technology: Jakarta EE 10, JSP, JDBC
