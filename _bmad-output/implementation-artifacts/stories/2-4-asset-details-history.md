# Story 2.4: Asset Details & History

## Status: DONE

## Story Details

**As a** Asset Staff / Finance Head / Principal,
**I want to** view detailed information and history of an asset,
**So that** I can track its lifecycle and usage.

## Acceptance Criteria

### AC 1: View Asset Details
- [x] **When** I click "View Detail" on an asset
- [x] **Then** I see all asset information (Code, Name, Specs, History Timeline)

### AC 2: Asset History Log
- [x] **Given** Actions are performed on an asset (Create, Update, Transfer...)
- [x] **Then** The system logs these actions in `asset_history`
- [x] **And** The history is displayed in the Detail view sorted by date (newest first)

## Tasks

### Task 1: Backend - Model & DAO for History
- [x] 1.1. Create `AssetHistory` Model
- [x] 1.2. Create `AssetHistoryDAO`
- [x] 1.3. Update `AssetService` to log history on Create/Update

### Task 2: Frontend - Detail View
- [x] 2.1. Update `AssetServlet` to handle `view` action
- [x] 2.2. Create `views/asset/detail.jsp` displaying info and history table

## Technical Notes

- **Table:** `asset_history`
- **Trigger Points:** 
    - Asset Creation (Action: "Created")
    - Asset Update (Action: "Updated info")
- Transfer/Maintenance triggers will be implemented in their respective Epics/Stories, but the infrastructure is built here.

## Dev Notes

- Started: 2026-01-23
- Completed: 2026-01-23
- Technology: Jakarta EE 10
- Implementation:
    - Added `AssetHistory` model and DAO.
    - Updated `AssetService` to include user context for auditing.
    - Added "Xem" button in asset list.
    - Created responsive Detail page with Timeline.
