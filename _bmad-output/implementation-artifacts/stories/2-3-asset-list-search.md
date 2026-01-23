# Story 2.3: Asset List & Search

## Status: DONE

## Story Details

**As a** Asset Staff / Finance Head,
**I want to** view and search for assets,
**So that** I can manage the inventory efficiently.

## Acceptance Criteria

### AC 1: List Assets
- [x] **Given** I am authorized
- [x] **When** I view the Asset List
- [x] **Then** I see columns: Asset Code, Name, Category, Room, Status, Price

### AC 2: Search Assets
- [x] **When** I enter a keyword (Name or Code)
- [x] **Then** The list is filtered matching the keyword

### AC 3: Status Indication
- [x] **Then** Status is displayed with visual indicators (colors/badges)

## Tasks

### Task 1: Backend
- [x] 1.1. Implement `findAll` and `searchAssets` in `AssetDAO` & `AssetService`
- [x] 1.2. Join tables (`categories`, `rooms`, `suppliers`) to fetch display names

### Task 2: Frontend
- [x] 2.1. Implement `list` action in `AssetServlet`
- [x] 2.2. Create `views/asset/list.jsp` with Search form and DataTable
- [x] 2.3. Style status badges (CSS)

## Technical Notes

- **Implementation:** Integrated into `AssetServlet` and `AssetDAOImpl`.
- **Search:** Supports case-insensitive `LIKE` query on Name and Code.

## Dev Notes

- Started: 2026-01-23
- Completed: 2026-01-23
