# ScaffoldPRO v5.0-rc1-gold Release Notes

*May 16, 2025*

## Overview

We are proud to announce the gold release candidate of ScaffoldPRO v5.0, representing a significant milestone in our professional-grade application for construction professionals. This release includes enhanced measurement capabilities, improved color systems, and robust CI/CD pipeline configurations.

## What's New

### ✨ New Features

- **AR Tape Measure**: Complete measurement capabilities for iOS and Android
- **Bubble-Level/Inclinometer**: Integrated level tools for precise alignment
- **Room Scanning**: LiDAR-based room scanning for iOS devices
- **WCAG AA Compliance**: Enhanced color token system across platforms

### 🔧 Improvements

- Updated GitHub Actions workflows with improved configurations
- Added snapshot CI job for automated visual regression testing
- Enabled Git LFS tracking for large binary files
- Added required iOS permissions for camera and photo library access

## Requirements

### iOS
- iOS 16.0+ (iOS 17.0+ recommended for RoomPlan)
- iPhone with LiDAR scanner (iPhone 12 Pro/Pro Max or newer) for Room Scanning

### Android
- Android 9.0+ (API level 28+)
- ARCore compatible device for AR features

## Installation

See the [README.md](README.md) for detailed instructions on building and running the application locally.

## Known Issues

- Room scanning functionality requires good lighting conditions
- AR measurement accuracy varies based on device capabilities and environment

---

Thank you for using ScaffoldPRO! For detailed information about all changes, please refer to the [RELEASE_CHANGELOG.md](RELEASE_CHANGELOG.md) file.