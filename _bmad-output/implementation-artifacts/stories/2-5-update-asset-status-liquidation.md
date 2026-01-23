# Story 2.5: Update Asset Status & Liquidation

## Status: DONE

## Story Details

**As a** Asset Staff,
**I want to** update an asset's status or mark it for liquidation,
**So that** the system reflects reality (e.g., item is broken).

## Acceptance Criteria

### AC 1: Update Asset Status
- [x] **Given** An asset exists
- [x] **When** I change its status (e.g., In_Use → Broken)
- [x] **Then** The status is updated in database
- [x] **And** History is logged

### AC 2: Filter by Status
- [x] **Given** I am on the Asset List
- [x] **When** I select a status filter (e.g., "Broken")
- [x] **Then** Only assets with that status are shown

### AC 3: Create Liquidation Minute
- [x] **Given** Assets with status Broken/Maintenance/Lost exist
- [x] **When** I create a Liquidation Minute and select those assets
- [x] **Then** The minute is created with status "Pending" for Principal approval

### AC 4: Principal Approval
- [x] **Given** A pending Liquidation Minute exists
- [x] **When** Principal clicks "Approve"
- [x] **Then** The minute status becomes "Approved"
- [x] **And** All assets in the minute are updated to status "Liquidated"
- [x] **And** History is logged for each asset

### AC 5: Principal Rejection
- [x] **Given** A pending Liquidation Minute exists
- [x] **When** Principal clicks "Reject"
- [x] **Then** The minute status becomes "Rejected"
- [x] **And** Assets remain unchanged

## Tasks

### Task 1: Backend - Models & DAOs
- [x] 1.1. Create `LiquidationMinute` and `LiquidationDetail` models
- [x] 1.2. Create DAOs for Liquidation entities
- [x] 1.3. Add `findByStatus`, `findEligibleForLiquidation`, `updateStatus` to AssetDAO

### Task 2: Service Layer
- [x] 2.1. Create `LiquidationService` with approval workflow
- [x] 2.2. Implement auto-update asset status on approval
- [x] 2.3. Add `getAssetsByStatus` to `AssetService`

### Task 3: Controllers & Views
- [x] 3.1. Create `LiquidationServlet` with CRUD and approval actions
- [x] 3.2. Create `views/liquidation/list.jsp` with approval buttons for Principal
- [x] 3.3. Create `views/liquidation/form.jsp` with asset selection
- [x] 3.4. Create `views/liquidation/detail.jsp` with asset list
- [x] 3.5. Update sidebar to include Liquidation menu

### Task 4: Asset List Enhancement
- [x] 4.1. Add Status Filter dropdown to Asset List
- [x] 4.2. Update AssetServlet to handle status filter

## Technical Notes

- **Tables Used:** `liquidation_minutes`, `liquidation_details`, `assets`, `asset_history`
- **Workflow:** 
  - Staff creates minute → status "Pending"
  - Principal approves → status "Approved", assets → "Liquidated"
  - Principal rejects → status "Rejected", assets unchanged
- **Access Control:**
  - Finance Head & Asset Staff: Create minutes
  - Principal: Approve/Reject
  - All 3 roles: View list

## Dev Notes

- Started: 2026-01-23
- Completed: 2026-01-23
- Technology: Jakarta EE 10, JSP, JDBC
- Implementation Highlights:
  - Full CRUD for Liquidation Minutes
  - Auto-update asset status on approval with history logging
  - Status filter added to Asset List (Enhancement to Story 2.3)
