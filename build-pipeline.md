# Build Pipeline Documentation

This document outlines the CI/CD build pipeline for the Scaffold Project.

## Workflows

The pipeline consists of several GitHub Actions workflows:

1.  **`ios-build.yml`**: Handles the build, test, and archiving process for the iOS application. It performs the following key steps:
    *   Checks out the repository.
    *   Sets up the Xcode environment (pinned to Xcode 15.3 on macos-13 runners).
    *   Caches Swift Package Manager dependencies.
    *   Resolves Swift Packages.
    *   Builds the iOS application.
    *   Runs XCUITests.
    *   (Placeholder) Runs ContrastKit Audit and uploads a simulated report.
    *   Archives the iOS build (XCArchive and a placeholder IPA).
    *   Uploads build artifacts (XCArchive, IPA, ContrastKit report) with a 14-day retention and errors if no files are found.
    *   (Placeholder) Pushes snapshot baselines for iOS on main, develop, or rc branches.

2.  **`android-build.yml`**: Manages the build, test, and bundling process for the Android application. Key steps include:
    *   Checks out the repository.
    *   Sets up JDK 17.
    *   Caches Gradle packages.
    *   Runs Detekt for static analysis.
    *   Runs Android Lint.
    *   Runs unit tests.
    *   Builds the Release AAB.
    *   Uploads the AAB with a 14-day retention and errors if no files are found.
    *   (Placeholder) Runs Instrumentation Tests and uploads simulated snapshot artifacts.
    *   (Placeholder) Pushes snapshot baselines for Android on main, develop, or rc branches.

3.  **`smoke.yml`**: Executes end-to-end smoke tests for both iOS and Android platforms. This workflow is crucial for ensuring basic application stability after builds.
    *   **Triggered by**: Manual dispatch, or on pull requests/pushes to `main`, `develop`, and `rc-*` branches.
    *   **Dependencies**: This workflow `needs` the successful completion of `ios-build` and `android-build` jobs (these are conceptual dependencies; the actual GitHub Actions `needs` configuration points to job IDs which are assumed to be resolvable within the GitHub Actions environment, or this workflow is triggered after the build workflows complete).
    *   **iOS Smoke Test Job (`ios_smoke_test`)**:
        *   Runs on `macos-13` with Xcode 15.3.
        *   Sets up Node.js and installs Detox CLI.
        *   Installs project dependencies.
        *   Builds the app for Detox (if not using a pre-built app from `ios-build`).
        *   Runs Detox tests using the `ios.sim.release` configuration.
        *   Uses the `DETOX_IOS_SCHEME` repository secret.
        *   Uploads Detox test artifacts (logs, screenshots) with 14-day retention.
    *   **Android Smoke Test Job (`android_smoke_test`)**:
        *   Runs on `ubuntu-latest`.
        *   Sets up JDK 17 and Maestro.
        *   Runs Maestro Cloud tests using `flows/login.yaml` (or other specified flows).
        *   Uses the `MAESTRO_KEY` repository secret.
        *   Uploads Maestro test artifacts (reports, logs) with 14-day retention.

4.  **`kb-ingest.yml`** (Assumed): This workflow is expected to run and finish green as part of the overall CI process. (Details to be added if specified).

5.  **`graphql-build.yml`** (Assumed): This workflow is expected to run and finish green as part of the overall CI process. (Details to be added if specified).

## Secrets

The following repository secrets are used by the CI pipeline:

*   `MAESTRO_KEY`: API key for Maestro Cloud, used in the `smoke.yml` workflow for Android smoke tests.
*   `DETOX_IOS_SCHEME`: Specifies the Xcode scheme for Detox tests (e.g., `ScaffoldPRO`), used in the `smoke.yml` workflow for iOS smoke tests.
*   `LINEAR_WEBHOOK`: (Assumed based on previous context for notifications) Webhook URL for posting notifications to Linear.

## Artifacts

Key artifacts generated and uploaded by the pipeline include:

*   iOS IPA (`scaffoldpro-ios-ipa`)
*   iOS XCArchive (`scaffoldpro-ios-xcarchive`)
*   iOS ContrastKit Report (`scaffoldpro-contrast-report.html`)
*   Android AAB (`scaffoldpro-android-aab`)
*   Android Snapshots (`scaffoldpro-android-snapshots.zip`)
*   Detox iOS Test Artifacts (`detox-ios-test-artifacts`)
*   Maestro Android Test Artifacts (`maestro-android-test-artifacts`)

All artifacts uploaded via `actions/upload-artifact@v4` are configured with a `retention-days: 14` policy and will `if-no-files-found: error`.

## Protected Branches & Required Status Checks

Branches like `main` and `develop` (and potentially `rc-*` branches) should be protected.
Required status checks must include:
*   `ios-build` (or the primary job name from `ios-build.yml`)
*   `android-build` (or the primary job name from `android-build.yml`)
*   `smoke` (specifically the `ios_smoke_test` and `android_smoke_test` jobs from `smoke.yml`)
*   `kb-ingest` (if applicable)
*   `graphql-build` (if applicable)

This ensures that all critical build, test, and smoke test processes pass before code can be merged.

