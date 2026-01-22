# Story 1.2: User Authentication

## Story

**As a** User (All Roles),
**I want to** log in to the system securely,
**So that** I can access features authorized for my role.

## Status

done

## Acceptance Criteria

- [x] AC1: Login page displays username and password fields
- [x] AC2: Valid credentials redirect to Dashboard
- [x] AC3: Invalid credentials show error message "Invalid credentials"
- [x] AC4: Passwords are verified using BCrypt
- [x] AC5: User session stores logged in user information
- [x] AC6: Logout clears session and redirects to login
- [x] AC7: Inactive users cannot login
- [x] AC8: Role-specific dashboards are displayed (Principal, Finance, Staff, HOD)

## Tasks / Subtasks

### Task 1: Create Controllers
- [x] 1.1: Create `LoginServlet.java` handling GET (show form) and POST (authenticate)
- [x] 1.2: Create `LogoutServlet.java` to invalidate session
- [x] 1.3: Create `DashboardServlet.java` to route to role-specific dashboards

### Task 2: Create Login View
- [x] 2.1: Create `login.jsp` with styled form
- [x] 2.2: Add error message display functionality
- [x] 2.3: Add test accounts info for development

### Task 3: Create Dashboard Views
- [x] 3.1: Create `principal.jsp` - Principal dashboard with KPIs and pending approvals
- [x] 3.2: Create `finance.jsp` - Finance Head dashboard with quick actions
- [x] 3.3: Create `staff.jsp` - Asset Staff dashboard with task list
- [x] 3.4: Create `hod.jsp` - Head of Dept dashboard with transfer confirmations
- [x] 3.5: Create `default.jsp` - Default dashboard fallback
- [x] 3.6: Create `dashboard.css` - Modern responsive styles

### Task 4: Create Authentication Filter
- [x] 4.1: Create `AuthenticationFilter.java` to protect pages
- [x] 4.2: Configure filter to allow public pages (login, logout, assets, /)

---

## Dev Agent Record

### Implementation Plan
- Implemented MVC pattern: LoginServlet → UserService → UserDAO
- BCrypt password verification via UserService.authenticate()
- Session management with Constants.SESSION_USER and SESSION_ROLE
- AuthenticationFilter protects all non-public URLs
- Role-based dashboard routing in DashboardServlet

### Debug Log
- Build verified with `mvn clean compile` - SUCCESS

### Completion Notes
✅ Story 1.2 completed. Full authentication flow implemented:
- Login with BCrypt verification
- Session management
- Role-based dashboard routing
- Logout with session invalidation
- Protected routes via AuthenticationFilter

---

## File List

### New Files Created
- `src/main/java/com/ams/controller/LoginServlet.java`
- `src/main/java/com/ams/controller/LogoutServlet.java`
- `src/main/java/com/ams/controller/DashboardServlet.java`
- `src/main/java/com/ams/config/AuthenticationFilter.java`
- `src/main/webapp/WEB-INF/views/auth/login.jsp`
- `src/main/webapp/WEB-INF/views/dashboard/principal.jsp`
- `src/main/webapp/WEB-INF/views/dashboard/finance.jsp`
- `src/main/webapp/WEB-INF/views/dashboard/staff.jsp`
- `src/main/webapp/WEB-INF/views/dashboard/hod.jsp`
- `src/main/webapp/WEB-INF/views/dashboard/default.jsp`
- `src/main/webapp/assets/css/dashboard.css`

---

## Change Log

| Date | Change | Author |
|------|--------|--------|
| 2026-01-22 | Story created | Dev Agent |
| 2026-01-22 | Completed all tasks - Story DONE | Dev Agent |
