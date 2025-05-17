// swift-tools-version: 5.7
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "ScaffoldPRO",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "ScaffoldPRO",
            targets: ["ScaffoldPRO"]),
    ],
    dependencies: [
        // Add RoomPlan neutral fork
        .package(url: "https://github.com/roomplan-neutral/roomplan.git", from: "0.3.0"),
        // Add SmartSnap-iOS dependency
        .package(url: "https://github.com/maxxfrazer/SmartSnap-iOS.git", .branch("main")),
    ],
    targets: [
        .target(
            name: "ScaffoldPRO",
            dependencies: [
                .product(name: "RoomPlan", package: "roomplan"),
                .product(name: "SmartSnap", package: "SmartSnap-iOS"),
            ]),
        .testTarget(
            name: "ScaffoldPROTests",
            dependencies: ["ScaffoldPRO"]),
    ]
)