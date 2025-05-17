# ScaffoldPRO v5.0

*Professional-grade AR measurement and room scanning tools for construction professionals*

## Overview

ScaffoldPRO is a comprehensive toolkit for construction professionals, providing advanced AR measurement capabilities, room scanning, and accessibility features. Version 5.0 represents our Gold-Pass release with all planned features implemented and verified.

## Key Features

- **AR Tape Measure**: Precise measurement tools for iOS and Android
- **Bubble-Level/Inclinometer**: Digital level tools for alignment verification
- **Room Scanning**: LiDAR-based room scanning (iOS 16+ with LiDAR sensor)
- **WCAG AA Compliance**: Accessible design with enhanced color token system

## System Requirements

### iOS
- iOS 14.0+ (basic features)
- iOS 16.0+ (for Room Scanning feature)
- LiDAR sensor required for Room Scanning

### Android
- Android 8.0+ (API level 26)
- ARCore compatible device for AR features

## Development Setup

### iOS
```bash
# Install dependencies
cd ios
xcodebuild -resolvePackageDependencies -scheme ScaffoldPRO -project ScaffoldPRO.xcodeproj

# Build and run
open ScaffoldPRO.xcodeproj
```

### Android
```bash
# Install dependencies and build
cd android
./gradlew assembleDebug

# Run the app
./gradlew installDebug
```

## Running Tests

### Smoke Tests
```bash
# iOS Smoke Tests
cd ios/detox
npm install
npx detox build --configuration ios.sim.debug
npx detox test --configuration ios.sim.debug

# Android Smoke Tests
cd android/maestro
maestro test flow.yaml
```

### Snapshot Tests
```bash
# iOS Snapshot Tests
cd ios
xcodebuild test -scheme ScaffoldPROTests -destination 'platform=iOS Simulator,name=iPhone 14'

# Android Snapshot Tests
cd android
./gradlew verifyDebugAndroidTestScreenshotTest
```

## Release Information

- **Version**: 5.0 (Gold-Pass)
- **Release Date**: May 16, 2025
- **Status**: Released - Ready for App Store/Play Store submissions

## Documentation

- [Release Notes](./RELEASE_NOTES.md)
- [Changelog](./RELEASE_CHANGELOG.md)
- [Build Pipeline](./build-pipeline.md)
- [Project Plan](./project_plan.md)

## License

Copyright © 2025 ScaffoldPRO Team. All rights reserved.