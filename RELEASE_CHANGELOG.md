# ScaffoldPRO v5.0-rc1-gold Release

*Released: May 16, 2025*

[![CI ✔︎](https://github.com/starnickell/ScaffoldPRO/actions/workflows/smoke.yml/badge.svg)](https://github.com/starnickell/ScaffoldPRO/actions/workflows/smoke.yml) [![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## Overview

ScaffoldPRO v5.0-rc1-gold represents a significant milestone in our professional-grade application for construction professionals. This gold release candidate finalizes the cross-platform color token system, adds advanced measurement capabilities including bubble-level functionality, integrates RoomPlan and SmartSnap modules, and ensures proper CI/CD pipeline configuration with automated testing.

## Major Features

### AR Measurement Tools

- **AR Tape Measure**: Added complete AR-based measurement capabilities for both iOS and Android
  - iOS implementation via ARMeasure SPM dependency
  - Android implementation via Depth Lab Gradle dependency
- **Bubble-Level/Inclinometer**: Implemented using Android SensorManager and iOS CoreMotion
- **Room Scanning**: Added LiDAR RoomPlan integration for iOS devices

### Design System Enhancements

- **Color Token System**: Enhanced with WCAG AA compliance verification
  - Added additional semantic colors for improved UI consistency
  - Implemented complete cross-platform compatibility between iOS and Android

### CI/CD and Build Improvements

- **Workflows**: Updated GitHub Actions workflows
  - Improved iOS and Android build configurations
  - Added E2E smoke test workflow with iOS Detox and Android Maestro jobs
  - Added snapshot CI job for automated visual regression testing
- **Artifact Management**: Configured automatic artifact uploads for release preparation
  - Added USDZ artifact upload for RoomPlan scans
  - Enabled Git LFS tracking for snapshot baseline files

### Documentation

- **Build Pipeline**: Added comprehensive documentation of CI workflows, secrets, artifacts, and status checks
- **Project Plan**: Created detailed documentation outlining RC-polish delta tasks and objectives
- **README**: Updated with clear instructions for running smoke tests locally

## File Changes

### New Files

- `scaffold_project/ios/ScaffoldPRO/Features/ARMeasure/ARMeasuringScreen.swift`
- `scaffold_project/android/app/src/main/java/com/scaffoldpro/app/features/armeasure/MeasureActivity.kt`
- `scaffold_project/ios/ScaffoldPRO/Features/RoomScan/RoomScanningScreen.swift`
- `scaffold_project/ios/RoomScanKit/RoomScanKit.swift`
- `scaffold_project/ios/RoomScanKit/RoomCaptureViewController.swift`
- `scaffold_project/.github/workflows/smoke.yml`
- `scaffold_project/.github/workflows/snapshot.yml`
- `scaffold_project/README.md`
- `scaffold_project/docs/build-pipeline.md`
- `scaffold_project/docs/project_plan.md`

### Modified Files

- `.gitattributes`
- `HCTColor.swift`
- `Theme.kt`
- `MeasureActivity.kt`
- `scaffold_project/ios/Package.swift`
- `scaffold_project/android/app/build.gradle.kts`
- `scaffold_project/.github/workflows/ios-build.yml`
- `scaffold_project/.github/workflows/android-build.yml`
- `Info.plist`
- `README.md`

## Requirements

### iOS

- iOS 16.0+ (iOS 17.0+ recommended for full RoomPlan functionality)
- Xcode 15.3+
- iPhone with LiDAR scanner (iPhone 12 Pro/Pro Max or newer) for Room Scanning feature

### Android

- Android 9.0+ (API level 28+)
- ARCore compatible device for AR measurement features
- Android Studio Iguana or newer

## Contributors

- Development Team
- Design Team
- QA Team

## Installation

See the [README.md](README.md) for detailed instructions on building and running the application locally.

---

All checklist items have been completed with green workflows and all artifacts properly uploaded. This gold release candidate is ready for distribution.