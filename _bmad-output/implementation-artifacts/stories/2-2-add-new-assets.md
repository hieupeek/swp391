# Story 2.2: Add New Assets

## Status: DONE

## Story Details

**As a** Asset Staff (and Finance Head),
**I want to** add new assets to the system,
**So that** they are tracked in the digital inventory.

## Acceptance Criteria

### AC 1: Asset Information
- [x] **Given** I am on the Add Asset page
- [x] **When** I fill in Name, Category, Supplier, Cost, and Dates, and Room
- [x] **Then** The asset is saved with status "New" (or selected status)

### AC 2: Auto-Code Generation
- [x] **When** I select a Category (e.g., Laptop - LAP)
- [x] **Then** An asset code is automatically generated (e.g., LAP-2026-0001) ensuring uniqueness

### AC 3: Validation
- [x] **When** I submit incomplete data
- [x] **Then** I see error messages
- [x] Cost must be positive, Dates must be valid

## Tasks

### Task 1: Backend - Model & DAO
- [x] 1.1. Create `Asset` model
- [x] 1.2. Create `AssetDAO` interface & impl

### Task 2: Service Layer & Code Gen
- [x] 2.1. Create `AssetService`
- [x] 2.2. Implement `generateAssetCode(categoryId)` logic: `PREFIX + YEAR + SEQ`

### Task 3: Controller & View
- [x] 3.1. Create `AssetServlet` (create action)
- [x] 3.2. Create `views/asset/form.jsp` with dropdowns for Category, Supplier, Room

## Technical Notes

- **Code Gen Logic:** Implemented in `AssetServiceImpl.createAsset`.
- **Prefix:** Fetched from `categories` table.
- **Sequence:** Calculated by finding max code with current prefix.

## Dev Notes

- Started: 2026-01-23
- Completed: 2026-01-23
- Technology: Jakarta EE 10
