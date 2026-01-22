# Story 1.4: Category Management

## Status: DONE

## Story Details

**As a** Finance Head,
**I want to** define asset categories with prefixes (e.g., "Laptop" -> "LAP"),
**So that** new assets can be automatically coded systematically.

## Acceptance Criteria

### AC 1: Create Category
- [x] **Given** I am logged in as Finance Head
- [x] **When** I create a new Category "Laboratory Equipment" with prefix "LAB"
- [x] **Then** The category is saved to the database
- [x] **And** The prefix is validated to be unique and uppercase

### AC 2: View Category List
- [x] **When** I view the Category List
- [x] **Then** I see all categories with their prefixes
- [x] **And** I can search for categories by name or prefix

### AC 3: Update Category
- [x] **When** I edit an existing category
- [x] **Then** I can update the name
- [x] **But** I cannot change the prefix (to maintain asset code consistency)

## Tasks

### Task 1: Backend Implementation
- [x] 1.1. Create `Category` model
- [x] 1.2. Create `CategoryDAO` interface and `CategoryDAOImpl`
- [x] 1.3. Create `CategoryService` interface and `CategoryServiceImpl`
- [x] 1.4. Implement CRUD operations (Create, Read All, Update, Search)

### Task 2: Frontend Implementation
- [x] 2.1. Create `CategoryServlet` (list, create, update, search)
- [x] 2.2. Create `views/category/list.jsp` (with search and table)
- [x] 2.3. Create `views/category/form.jsp` (for add/edit)

### Task 3: Security & Validation
- [x] 3.1. Ensure only Finance Head can access these pages
- [x] 3.2. unique validation for Prefix in Service layer

## Implementation Summary

### Files Created:
- `src/main/java/com/ams/model/Category.java`
- `src/main/java/com/ams/dao/CategoryDAO.java`
- `src/main/java/com/ams/dao/CategoryDAOImpl.java`
- `src/main/java/com/ams/service/CategoryService.java`
- `src/main/java/com/ams/service/CategoryServiceImpl.java`
- `src/main/java/com/ams/controller/CategoryServlet.java`
- `src/main/webapp/WEB-INF/views/category/list.jsp`
- `src/main/webapp/WEB-INF/views/category/form.jsp`

## Technical Notes

- **Validation:** 
  - Prefix converted to uppercase automatically
  - Duplicate Prefix/Name throws exception in Service
- **UI:**
  - Sidebar highlights "Danh mục" when active
  - Form disables Prefix field in Edit mode
- **Security:**
  - Role check `Finance_Head` in Servlet `doGet` and `doPost`

## Dev Notes

- Started: 2026-01-23
- Completed: 2026-01-23
- Technology: Jakarta EE 10
