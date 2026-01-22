---
project_name: 'swp391'
user_name: 'swp391'
date: '2026-01-22T22:47:35+07:00'
sections_completed: ['technology_stack', 'language_rules', 'framework_rules', 'testing_rules', 'quality_rules', 'workflow_rules', 'anti_patterns']
status: 'complete'
rule_count: 21
optimized_for_llm: true
---

# Project Context for AI Agents

_This file contains critical rules and patterns that AI agents must follow when implementing code in this project. Focus on unobvious details that agents might otherwise miss._

---

## Technology Stack & Versions

- **Language:** Java 21 (LTS)
- **Web Framework:** Jakarta Servlet 4.0.1 (javax.servlet), JSP 2.3.3
- **Template Engine:** JSTL 1.2 (for JSP)
- **Database driver:** MySQL Connector/J 8.0.33
- **Build Tool:** Maven 3+ (packaging: war)
- **Utilities:** 
  - Lombok 1.18.30 (Use for Models/Entities)
  - BCrypt 0.4 (org.mindrot.jbcrypt) for password hashing

## Critical Implementation Rules

### Language-Specific Rules (Java 21)

- **Modern Java Features:** You ARE ALLOWED and ENCOURAGED to use:
  - `var` for local variable type inference.
  - `record` for immutable data carriers (DTOs).
  - Text Blocks (`"""`) for SQL strings and HTML templates.
  - Switch Expressions (enhanced `switch`).
  - Pattern Matching for `instanceof`.
- **Resource Management:** ALWAYS use `try-with-resources` for `Connection`, `PreparedStatement`, and `ResultSet` to prevent leaks.
- **Streams & Lambdas:** Encouraged for collection processing but keep them readable.
- **Null Handling:** Use `Optional<T>` for return types where a value might be missing, but avoid using it as a parameter / field.

### Framework-Specific Rules (Servlet/JSP/JDBC)

- **No Frameworks:** DO NOT use Spring, Hibernate, or JPA. This is a raw Servlet/JDBC project.
- **Architecture:** Follow the layered pattern:
  - `com.ams.controller` (Servlets)
  - `com.ams.service` (Business Logic)
  - `com.ams.dao` (Data Access - Raw JDBC)
  - `com.ams.model` (POJOs with Lombok)
- **Routing:** Servlets must use `@WebServlet` annotations. Map clearly (e.g., `/login`, `/assets/*`).
- **JSP Views:** 
  - Place valid JSPs inside `WEB-INF` to prevent direct access.
  - Use `request.getRequestDispatcher("/WEB-INF/views/...").forward(req, resp)`.
- **Frontend Logic:** 
  - PREFER JSTL (`<c:forEach>`, `<c:if>`) and EL (`${user.name}`) over Java Scriptlets (`<% ... %>`).
  - Keep JSPs logic-free; do all processing in the Servlet.

### Testing Rules

- **Unit Testing:** If adding tests, use JUnit 5 and Mockito.
- **Integration:** Focus on Service layer logic. Testing Servlets/DAOs is secondary unless using specific integration test setups.

### Code Quality & Style Rules

- **SOLID Principles (Critical):**
  - **SRP:** Strict separation of concerns. Servlet = HTTP handling ONLY. Service = Business Logic. DAO = Database access.
  - **DIP:** Depend on abstractions. Controllers should rely on `Service` interfaces, Services on `DAO` interfaces.
  - **Manual DI:** Since no Spring, use Constructor Injection or a simple Factory pattern to wire dependencies.
- **Clean Code Standards:**
  - **Meaningful Names:** Variable/method names must reveal intent (e.g., `isOverdue()` vs `check()`).
  - **Small Functions:** Methods should accept minimal arguments (max 3) and do exactly one thing.
  - **No Magic Values:** Extract hardcoded strings/numbers to `Constant` classes or Enums.
  - **DRY:** Extract duplicated logic (e.g., JSON parsing, Session checks) to `util` classes or `BaseServlet`.
- **Naming Conventions:**
  - Classes: `PascalCase` (e.g., `AssetService`, `UserDAO`).
  - Methods/Variables: `camelCase`.
  - Constants: `UPPER_SNAKE_CASE`.
  - Database: `snake_case` for tables and columns.
- **Lombok Usage:** Use `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor` for models to reduce boilerplate.
- **Comments:** Javadoc for public methods in Service/DAO interfaces. Inline comments for complex regex or business logic logic.
- **Package Structure:** strict adherence to `com.ams.*`.

### Development Workflow Rules

- **Database Changes:** 
  - Any schema change MUST be updated in `_bmad-output/SQL/db_final_mysql.sql`.
  - Do NOT assume the database is auto-updated.
- **Build:** Always verify with `mvn clean package`.

### Critical Don't-Miss Rules

- **SQL Injection:** NEVER concatenate strings for SQL. ALWAYS use `PreparedStatement` with `?` placeholders.
- **Password Security:** NEVER store plain text passwords. Use BCrypt `checkpw` and `hashpw`.
- **Date Handling:** Prefer `java.time` (LocalDate/LocalDateTime) in Java code, converting to `java.sql.Date`/`Timestamp` only at the JDBC boundary.
- **Encoding:** Always set `request.setCharacterEncoding("UTF-8")` and `response.setContentType("text/html; charset=UTF-8")` in filters or base servlets.

---

## Usage Guidelines

**For AI Agents:**

- Read this file before implementing any code
- Follow ALL rules exactly as documented
- When in doubt, prefer the more restrictive option
- Update this file if new patterns emerge

**For Humans:**

- Keep this file lean and focused on agent needs
- Update when technology stack changes
- Review quarterly for outdated rules
- Remove rules that become obvious over time

Last Updated: 2026-01-22
