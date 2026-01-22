# Story 1.1: System Initialization & Role Configuration

## Story

**As a** System Administrator,
**I want to** initialize the system with defined user roles and basic configuration,
**So that** the application enforces the correct security model from day one.

## Status

done

## Acceptance Criteria

- [x] AC1: Database tables (users, roles, departments) are created via SQL script
- [x] AC2: The 4 standard roles (Principal, Finance_Head, Asset_Staff, Head_of_Dept) are inserted
- [x] AC3: The initial admin users are seeded with BCrypt hashed password (password: 123456)
- [x] AC4: The project structure follows the layered architecture (Controller → Service → DAO → Model)
- [x] AC5: Model classes with Lombok are created for User, Role, Department
- [x] AC6: DAO interfaces and implementations are created for User, Role, Department
- [x] AC7: Service interfaces and implementations are created for User, Role
- [x] AC8: Base infrastructure (web.xml, EncodingFilter, Constants) is set up

## Tasks / Subtasks

### Task 1: Create Model Classes
- [x] 1.1: Create `Role.java` model with Lombok annotations
- [x] 1.2: Create `Department.java` model with Lombok annotations
- [x] 1.3: Create `User.java` model with Lombok annotations
- [x] 1.4: Create `UserStatus.java` enum (Active, Inactive)

### Task 2: Create DAO Layer
- [x] 2.1: Create `RoleDAO.java` interface
- [x] 2.2: Create `RoleDAOImpl.java` with JDBC implementation
- [x] 2.3: Create `DepartmentDAO.java` interface
- [x] 2.4: Create `DepartmentDAOImpl.java` with JDBC implementation
- [x] 2.5: Create `UserDAO.java` interface
- [x] 2.6: Create `UserDAOImpl.java` with JDBC implementation

### Task 3: Create Service Layer
- [x] 3.1: Create `RoleService.java` interface
- [x] 3.2: Create `RoleServiceImpl.java` implementation
- [x] 3.3: Create `UserService.java` interface
- [x] 3.4: Create `UserServiceImpl.java` implementation (with BCrypt password hashing)

### Task 4: Base Infrastructure Setup
- [x] 4.1: Create `web.xml` with basic servlet configuration
- [x] 4.2: Create `EncodingFilter.java` for UTF-8 encoding
- [x] 4.3: Create `Constants.java` for application constants
- [x] 4.4: Update seed data in SQL to use BCrypt hashed password
- [x] 4.5: Create error pages (404, 403, 500)
- [x] 4.6: Create index.jsp welcome page

## Dev Notes

### Architecture Requirements
- Follow strict layered architecture: Controller → Service → DAO → Model
- Use Lombok for Model boilerplate reduction
- Use try-with-resources for all JDBC operations
- BCrypt for password hashing (org.mindrot.jbcrypt)
- PreparedStatement only (NO string concatenation for SQL)

### Database Schema Reference
- `roles`: role_id, role_name, description
- `departments`: dept_id, dept_name, description
- `users`: user_id, username, password_hash, full_name, email, phone, role_id, dept_id, status, created_at

### Technical Stack
- Java 21, Servlet 4.0.1, JSP 2.3.3, JSTL 1.2
- MySQL 8.0 with Connector/J 8.0.33
- Lombok 1.18.30, BCrypt 0.4

### Test Accounts (password: 123456)
| Username | Role | Department |
|----------|------|------------|
| principal | Principal | Ban Giám Hiệu |
| finance | Finance_Head | Phòng Hành chính - Kế toán |
| staff | Asset_Staff | Tổ Kỹ thuật - Tài sản |
| hod_it | Head_of_Dept | Bộ môn Tin học |
| hod_physics | Head_of_Dept | Bộ môn Vật lý |

---

## Dev Agent Record

### Implementation Plan
- Task 1: Created 4 model classes with Lombok (@Data, @NoArgsConstructor, @AllArgsConstructor)
- Task 2: Created 6 DAO files with JDBC using PreparedStatement and try-with-resources
- Task 3: Created 4 Service files with BCrypt password hashing
- Task 4: Created web.xml, EncodingFilter, Constants, error pages, index.jsp

### Debug Log
- Build verified with `mvn clean compile` - SUCCESS

### Completion Notes
✅ Story 1.1 completed successfully. All model, DAO, service layers created. Infrastructure ready for authentication implementation.

---

## File List

### New Files Created
- `src/main/java/com/ams/model/UserStatus.java`
- `src/main/java/com/ams/model/Role.java`
- `src/main/java/com/ams/model/Department.java`
- `src/main/java/com/ams/model/User.java`
- `src/main/java/com/ams/dao/RoleDAO.java`
- `src/main/java/com/ams/dao/RoleDAOImpl.java`
- `src/main/java/com/ams/dao/DepartmentDAO.java`
- `src/main/java/com/ams/dao/DepartmentDAOImpl.java`
- `src/main/java/com/ams/dao/UserDAO.java`
- `src/main/java/com/ams/dao/UserDAOImpl.java`
- `src/main/java/com/ams/service/RoleService.java`
- `src/main/java/com/ams/service/RoleServiceImpl.java`
- `src/main/java/com/ams/service/UserService.java`
- `src/main/java/com/ams/service/UserServiceImpl.java`
- `src/main/java/com/ams/config/Constants.java`
- `src/main/java/com/ams/config/EncodingFilter.java`
- `src/main/webapp/WEB-INF/web.xml`
- `src/main/webapp/WEB-INF/views/error/404.jsp`
- `src/main/webapp/WEB-INF/views/error/403.jsp`
- `src/main/webapp/WEB-INF/views/error/500.jsp`
- `src/main/webapp/index.jsp`

### Modified Files
- `_bmad-output/SQL/db_final_mysql.sql` (updated seed data with BCrypt passwords)

---

## Change Log

| Date | Change | Author |
|------|--------|--------|
| 2026-01-22 | Story created | Dev Agent |
| 2026-01-22 | Completed all tasks - Story DONE | Dev Agent |
