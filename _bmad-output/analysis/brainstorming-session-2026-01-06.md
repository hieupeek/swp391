---
selected_approach: 'progressive-flow'
techniques_used: ['What If Scenarios', 'Context Analysis', 'Mind Mapping', 'SCAMPER Method', 'Solution Matrix']
stepsCompleted: [1, 2]
---

# Brainstorming Session Results

**Facilitator:** swp391
**Date:** 2026-01-06

## Session Overview

**Topic:** Asset Management System (AMS)
**Goals:** Detailed business analysis and workflow design for 10 functional modules, specifically focusing on procurement, maintenance, and liquidation.

### Context Guidance

Based on the project context, we will focus on:
- **Detailed Workflows:** Process flows for each routine (Procurement, Maintenance, Liquidation...).
- **Data Structure:** Storage in MySQL to ensure consistency.
- **User Interface:** Implementation using JSP/Servlet.

### Session Setup

The user has provided a list of 10 specific features divided into mandatory and optional items.
1. Asset Category Management
2. Asset Management
3. Provisioning Request Process (Optional)
4. Asset Intake Process (Optional)
5. Asset Inventory Addition
6. Maintenance/Repair Process (Optional)
7. Asset Return/Liquidation Process (Optional)
8. Asset Transfer/Movement
9. Asset Allocation/Handover
10. Reports and Statistics

*Note: Modules 3, 4, 6, and 7 are optional; the rest are mandatory.*

## Technique Selection

**Approach:** Progressive Technique Flow
**Journey Design:** Developing the system from initial idea discovery to concrete implementation planning.

**Progressive Techniques:**

- **Phase 1 - Exploration:** **What If Scenarios** to identify edge cases and complex business flows.
- **Phase 2 - Context Analysis (3 Steps):** Defining Roles, Tasks, and Context Diagrams to shape real-world data flow.
- **Phase 3 - Pattern Recognition:** **Mind Mapping** to organize data and entity relationships.
- **Phase 4 - Development:** **SCAMPER Method** to optimize and detail each feature.
- **Phase 5 - Action Planning:** **Solution Matrix** to finalize Database architecture and Servlet/JSP structure.

**Journey Rationale:** Starting with questions about existing systems, then restructuring, sharpening details, and finally creating technical blueprints to begin coding.

## Context Analysis (3 Steps)

### B1: Organizational Structure (User Roles)

Based on real-world surveys of public schools, the system includes the following user groups:

1.  **Facilities Staff:** The main operator. Summarizes needs, inventories goods received from the Ministry, allocates assets, and manages spare inventory.
2.  **Principal:** The highest decision-maker. Approves "Provisioning Tables" to be sent to the Ministry for asset requests.
3.  **Vice Principal:** Overseer and initial approver. Reviews needs and reports submitted by staff before they reach the Principal.
4.  **Guest:** Anonymous users (Students/Visitors). Reports asset damage through QR Code scans located on devices/rooms.

### B2: User Tasks (Roles & Responsibilities)

**1. Guest:**
*   *Task 1:* Scan QR Codes to access a quick reporting form.
*   *Task 2:* Submit incident information (with photos) without needing to log in.

**2. Facilities Staff:**
*   *Task 1:* Summarize school-wide needs into a "Grand Provisioning Table."
*   *Task 2:* Record new inventory arrivals (based on Ministry handover documents).
*   *Task 3:* Allocate/Handover assets to specific rooms or departments.
*   *Task 4:* Manage spare inventory and coordinate replacement of damaged items.
*   *Task 5:* Prepare lists for liquidation or return to the Ministry.
*   *Task 6:* Verify damage reports submitted by Guests (confirming if damage is real).

**3. Vice Principal:**
*   *Task 1:* Review and perform initial approval/rejection of "Provisioning Tables" and "Maintenance Requests."
*   *Task 2:* Monitor internal asset movements and inventory status.

**4. Principal:**
*   *Task 1:* Final approval (Approve) of major Provisioning Tables for high-level submission.
*   *Task 2:* View comprehensive statistical reports and dashboards on school assets.

### B3: Context Diagram (Context & Data Flow)

Description of Data Input/Output between Actors and the System:

*   **Interaction 1: Guest -> System**
    *   *Input:* Anonymous damage report form (Photos, Status description).
    *   *Output:* Success notification ("Report submitted, awaiting verification").

*   **Interaction 2: Facilities Staff -> System**
    *   *Input:* Actual quantities received (Manual entry from Ministry docs), Allocation details, Verified damage status, Maintenance results.
    *   *Output:* PDF "Asset Request Form" (for signing), Inventory logs, Handover receipts.

*   **Interaction 3: Vice Principal -> System**
    *   *Input:* Initial approval/rejection commands.
    *   *Output:* Pending request lists, Status summary reports.

*   **Interaction 4: Principal -> System**
    *   *Input:* Final Approval command (Click Approve).
    *   *Output:* Dashboard summary reports, Finalized Provisioning PDF for external submission.

*(Note: The system does NOT connect directly to the Ministry of Education. Sending requests and receiving goods are manual external processes; the system only supports document generation and data entry).*
