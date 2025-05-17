# ScaffoldPRO

[![CI ✔︎](https://github.com/starnickell/ScaffoldPRO/actions/workflows/smoke.yml/badge.svg)](https://github.com/starnickell/ScaffoldPRO/actions/workflows/smoke.yml) | v5.0-rc1-gold (May 16, 2025) | [![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

ScaffoldPRO is a professional-grade application for construction professionals to accurately measure, model, and plan scaffold installations using augmented reality. With cutting-edge spatial recognition technology, it enables precise measurements, hazard identification, and collaborative workflow planning directly on site. This gold release candidate (v5.0-rc1-gold) includes enhanced WCAG AA compliant color systems, AR measurement tools with bubble-level functionality, and LiDAR-based room scanning capabilities.

## Field-Tools

ScaffoldPRO integrates with powerful spatial measurement technologies:

- [RoomPlan](https://developer.apple.com/augmented-reality/roomplan/) - iOS LiDAR-based room scanning and reconstruction
- [SmartSnapKit](https://www.smartsnapkit.com) - Precision measurement tool suite for construction
- [ARCoreMeasure](https://developers.google.com/ar/develop/java/depth/overview) - Android AR-based measurement capabilities
- Bubble-Level/Inclinometer - Built-in leveling functionality for accurate alignment

## System Requirements

### iOS
- iOS 16.0+ (iOS 17.0+ recommended for full RoomPlan functionality)
- iPhone with LiDAR scanner (iPhone 12 Pro/Pro Max or newer) for Room Scanning feature

### Android
- Android 9.0+ (API level 28+)
- ARCore compatible device for AR measurement features

## Running smoke tests locally

### iOS (Detox)
Ensure you have the necessary environment setup (Node.js, Detox CLI, Xcode, iOS Simulator).

1.  Navigate to the `ios` directory:
    ```bash
    cd ios
    ```
2.  Install dependencies (if not already done):
    ```bash
    npm install 
    # or yarn install
    ```
3.  Build the app for Detox (Release configuration):
    ```bash
    detox build -c ios.sim.release
    ```
4.  Run Detox tests:
    ```bash
    detox test -c ios.sim.release --cleanup
    ```
    *(Ensure `DETOX_IOS_SCHEME` is set to `ScaffoldPRO` in your environment or CI if needed by your local setup)*

### Android (Maestro)
Ensure you have Maestro installed and an Android emulator/device running.

1.  Navigate to the project root directory.
2.  Run Maestro tests. The CI uses `maestro cloud`. For local execution against a running emulator/device (assuming your flows are in a `flows` directory, e.g., `flows/login.yaml`):
    ```bash
    # Ensure Maestro is in your PATH or use ~/.maestro/bin/maestro
    maestro test flows/login.yaml
    ```
    If you intend to run against Maestro Cloud locally, you would use:
    ```bash
    maestro cloud --apiKey YOUR_MAESTRO_API_KEY flows/login.yaml
    ```
    *(Replace `YOUR_MAESTRO_API_KEY` with your actual key. The CI script uses `secrets.MAESTRO_KEY`.)*

