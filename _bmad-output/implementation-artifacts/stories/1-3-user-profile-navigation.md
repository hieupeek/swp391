# Story 1.3: User Profile & Navigation Structure

## Status: DONE

## Story Details

**As a** User,
**I want to** see a navigation menu tailored to my role and manage my profile,
**So that** I can easily access my functions and keep my info updated.

## Acceptance Criteria

### AC 1: Role-Based Navigation Menu
- [x] **Given** I am logged in as Asset Staff
- [x] **When** I view the Sidebar
- [x] **Then** I see "Asset Mgmt", "Transfer", "Maintenance" menus
- [x] **But** I do not see "Approve Procurement" (Finance function)

### AC 2: Profile Access
- [x] **When** I access "My Profile"
- [x] **Then** I can view my user information

### AC 3: Profile Update
- [x] **When** I update my Phone Number
- [x] **Then** The changes are saved to the database

## Tasks

### Task 1: Create Reusable Layout Components
- [x] 1.1. Create `layouts/` folder structure
- [x] 1.2. Create `header.jsp` includes (common header)
- [x] 1.3. Create `sidebar.jsp` with role-based menu rendering
- [x] 1.4. Create `footer.jsp` includes (not needed for now)

### Task 2: Implement Role-Based Navigation
- [x] 2.1. Define menu items for each role in sidebar.jsp using JSTL
- [x] 2.2. Create Navigation using JSTL conditional rendering
- [x] 2.3. Implement role-to-menu mapping logic with `<c:if>` tags
- [x] 2.4. Update sidebar.jsp to render menus dynamically

### Task 3: Create Profile Management
- [x] 3.1. Create ProfileServlet (GET/POST)
- [x] 3.2. Create profile view (`views/user/profile.jsp`)
- [x] 3.3. Add updateProfile method to UserService (already existed)
- [x] 3.4. Add update method to UserDAO (already existed)

### Task 4: Refactor Dashboard Views
- [x] 4.1. Refactor all dashboard JSPs to use shared layout (principal, finance, staff, hod, default)
- [x] 4.2. Ensure consistent styling across all views

## Implementation Summary

### Files Created:
- `src/main/webapp/WEB-INF/views/layouts/sidebar.jsp` - Role-based navigation component
- `src/main/webapp/WEB-INF/views/layouts/header.jsp` - Common header component
- `src/main/webapp/WEB-INF/views/user/profile.jsp` - User profile view
- `src/main/java/com/ams/controller/ProfileServlet.java` - Profile controller

### Files Updated:
- `src/main/webapp/WEB-INF/views/dashboard/principal.jsp` - Uses shared layout
- `src/main/webapp/WEB-INF/views/dashboard/finance.jsp` - Uses shared layout
- `src/main/webapp/WEB-INF/views/dashboard/staff.jsp` - Uses shared layout
- `src/main/webapp/WEB-INF/views/dashboard/hod.jsp` - Uses shared layout
- `src/main/webapp/WEB-INF/views/dashboard/default.jsp` - Uses shared layout
- `src/main/webapp/assets/css/dashboard.css` - Added new styles (priority, badges, user-link)

## Technical Notes

- Navigation menu uses JSTL `<c:if>` for role-based conditional rendering
- Sidebar dynamically shows different menu items based on `roleName` session attribute
- Profile update validates email format and phone number (10-11 digits)
- All dashboard views now use JSP includes for consistency

## Dev Notes

- Started: 2026-01-22
- Completed: 2026-01-23
- Technology: Jakarta EE 10 (jakarta.servlet.*, jakarta.tags.core)
