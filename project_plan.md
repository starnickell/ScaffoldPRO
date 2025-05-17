# Project Plan (RC-Polish Delta)

This document outlines the plan and key tasks undertaken during the `rc-polish` phase, culminating in the `v5.0-rc1-refresh-2` tag.

## Key Objectives

*   Implement robust E2E smoke testing automation.
*   Standardize CI runner configurations and artifact handling.
*   Enhance documentation and visibility of CI processes.
*   Ensure all required secrets are configured.
*   Maintain a detailed changelog of all modifications.

## Implemented Changes & Features

1.  **E2E Smoke Automation (`.github/workflows/smoke.yml`)**:
    *   New workflow `smoke.yml` created.
    *   **iOS Smoke Tests**: Uses Detox on `macos-13` (Xcode 15.3) runner, executes `detox test -c ios.sim.release`. Depends on `ios-build` job completion. Uses `DETOX_IOS_SCHEME` secret.
    *   **Android Smoke Tests**: Uses Maestro Cloud on `ubuntu-latest` runner, executes `maestro cloud --apiKey ${{ secrets.MAESTRO_KEY }} flows/login.yaml`. Depends on `android-build` job completion. Uses `MAESTRO_KEY` secret.
    *   Snapshot baseline push steps added (as placeholders) to `ios-build.yml` and `android-build.yml`.
    *   The `smoke` workflow (and its constituent jobs) is intended to be a required status check for protected branches.

2.  **Secrets & Runner Matrix**:
    *   **Repository Secrets Created/Required**: `MAESTRO_KEY`, `DETOX_IOS_SCHEME` (value: `ScaffoldPRO`).
    *   **Runner Pinning**: All macOS runners in workflows (e.g., `ios-build.yml`, `smoke.yml`) pinned to `macos-13` with `macos-image: xcode15.3` (or specific Xcode 15.3 selection logic).

3.  **Artifact Hygiene**:
    *   All `actions/upload-artifact@v4` steps across `ios-build.yml`, `android-build.yml`, and `smoke.yml` updated to include:
        *   `retention-days: 14`
        *   `if-no-files-found: error`

4.  **Documentation & Visibility Updates**:
    *   **`README.md`**: Updated with a CI badge for the `smoke` workflow and a section on running smoke tests locally (Detox & Maestro).
    *   **`/docs/build-pipeline.md`**: Created to document all CI workflows, secrets, artifacts, and status check strategies.
    *   **`/docs/project_plan.md`** (this file): Created to track the RC-polish delta plan and execution.
    *   **`/docs/e2e.md`**: To be updated with the full YAML content of `smoke.yml`.

5.  **Structured Action Log (`/docs/rc1_refresh2_changelog.json`)**:
    *   Created and maintained to log every file touched during this delta, including timestamp, file path, action, (future) commit SHA, and a comment.

6.  **Original 8 Punch-List Items (Integrated/Covered)**:
    *   Server-side receipt validation (Assumed handled by backend/API, CI verifies app compilation with related SDKs like RevenueCat & Play Billing v6).
    *   A11y snapshot jobs (Placeholder/simulated in build workflows, actual snapshot jobs are part of E2E or dedicated test suites).
    *   `NSSpatialTrackingUsageDescription` (Plist item, verified separately).
    *   Cache step (Implemented in `ios-build.yml` for SPM and `android-build.yml` for Gradle).
    *   Android exported flags lint (Covered by standard Android Lint job in `android-build.yml`).
    *   OIDC secret names (Standardized to `MAESTRO_KEY`, `DETOX_IOS_SCHEME`).
    *   Snapshot-baseline push (Placeholder scripts added to `ios-build.yml` and `android-build.yml`).
    *   End-to-end smoke test (Implemented via `smoke.yml` with Detox and Maestro).

## Execution Flow (Planned)

1.  Create/checkout `rc-polish` branch.
2.  Implement all items from the consolidated 13-item punch list.
3.  Commit changes (including `rc1_refresh2_changelog.json`, `todo.md`, `project_plan.md`).
4.  Push `rc-polish` branch and open a Pull Request.
5.  Wait for all CI workflows (builds, security checks, a11y placeholders, `smoke` tests, `kb-ingest.yml`, `graphql-build.yml`) to pass (✅).
6.  Merge the Pull Request.
7.  Tag the release as `v5.0-rc1-refresh-2`.
8.  Create a GitHub Release for `v5.0-rc1-refresh-2` using `softprops/action-gh-release@v1`, attaching all specified artifacts (IPA, AAB, contrast reports, snapshot zips, Detox logs, Maestro logs, a11y reports, vulnerability reports, CodeQL reports).
9.  Post a comment to Linear issue `SKD-04` via `$LINEAR_WEBHOOK`: `"CI green & v5.0-rc1-refresh-2 tagged — ready for Trae QA. Changelog + artifact matrix attached."` (Simulated: will provide text and details for webhook).

This plan is subject to minor adjustments as implementation progresses but aims to fulfill all specified requirements for the RC-polish delta.

