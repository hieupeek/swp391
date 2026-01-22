# Story 2.1: Master Data Management (Rooms & Suppliers)

## Status: IN_PROGRESS

## Story Details

**As a** Finance Head,
**I want to** manage the list of Rooms and Suppliers,
**So that** assets can be correctly assigned to locations and vendors.

## Acceptance Criteria

### AC 1: Manage Suppliers
- [ ] **Given** I am logged in as Finance Head
- [ ] **When** I add/edit a Supplier (Name, Contact Info)
- [ ] **Then** The supplier is saved to the database

### AC 2: Manage Rooms
- [ ] **Given** I am logged in as Finance Head
- [ ] **When** I add/edit a Room (Name, Capacity) and assign it to a Department
- [ ] **Then** The room is saved with proper department linkage

### AC 3: View Access
- [ ] **Given** I am logged in as Asset Staff
- [ ] **Then** I can view the lists but cannot add/edit

## Tasks

### Task 1: Backend - Models & DAOs
- [ ] 1.1. Create `Room` and `Supplier` models
- [ ] 1.2. Create `RoomDAO` and `SupplierDAO` interfaces & impls
- [ ] 1.3. Create `RoomService` and `SupplierService`

### Task 2: Frontend - Controllers & Views
- [ ] 2.1. Create `SupplierServlet` (CRUD)
- [ ] 2.2. Create `views/supplier/list.jsp` & `form.jsp`
- [ ] 2.3. Create `RoomServlet` (CRUD)
- [ ] 2.4. Create `views/room/list.jsp` & `form.jsp`

### Task 3: Security
- [ ] 3.1. Enforce Role-Based Access (Finance Head: Write, Asset Staff: Read)

## Technical Notes

- **Tables:** `rooms` (room_id, room_name, dept_id, capacity), `suppliers` (supplier_id, supplier_name, address, contact_person, phone, email)
- **Validation:** Room name unique, Supplier name unique
- **Dependencies:** `departments` table (already exists)

## Dev Notes

- Started: 2026-01-23
- Technology: Jakarta EE 10
