// ARMeasuringScreen with integrated bubble-level/inclinometer
// Provides AR-based measurement tools with precision leveling capability

import SwiftUI
import ARKit
import CoreMotion // Required for accelerometer/inclinometer access

// Define a placeholder for MeasureMode as the actual enum would come from the ARMeasure package
enum MeasureMode: String, CaseIterable, Identifiable {
    case length = "Length"
    case area = "Area"
    case level = "Level"
    var id: String { self.rawValue }
}

struct ARMeasuringScreen: View {
    @State private var currentMode: MeasureMode = .length
    @State private var measurementResult: String = "No measurement yet."
    
    // Motion manager for inclinometer/bubble level functionality
    @StateObject private var motionManager = MotionManager()
    
    // Angle properties for bubble level display
    @State private var xDegrees: Double = 0.0
    @State private var yDegrees: Double = 0.0

    var body: some View {
        VStack {
            Text("AR Tape Measure")
                .font(.largeTitle)
                .padding()

            Picker("Measurement Mode", selection: $currentMode) {
                ForEach(MeasureMode.allCases) {
                    mode in Text(mode.rawValue).tag(mode)
                }
            }
            .pickerStyle(SegmentedPickerStyle())
            .padding()

            Text("Selected Mode: \(currentMode.rawValue)")
                .padding(.bottom)
            
            // Placeholder for the actual AR Measurement View from the imported package
            // This view would interact with ARKit and the ARMeasure library.
            if currentMode == .level {
                // Bubble level UI
                BubbleLevelView(xDegrees: xDegrees, yDegrees: yDegrees)
                    .frame(minHeight: 300)
                    .padding()
                    .background(Color(HCTColor.surface))
                    .cornerRadius(12)
                    .overlay(
                        RoundedRectangle(cornerRadius: 12)
                            .stroke(Color(HCTColor.outline), lineWidth: 1)
                    )
            } else {
                // AR measurement view
                ZStack {
                    // ARMeasureViewRepresentable exists in the real implementation
                    Rectangle()
                        .fill(Color(HCTColor.surface).opacity(0.2))
                        .overlay(
                            Text("AR Measurement View")
                                .foregroundColor(Color(HCTColor.onSurface))
                                .multilineTextAlignment(.center)
                        )
                        .frame(minHeight: 300)
                }
            }
            .padding()

            Text("Result: \(measurementResult)")
                .padding()

            Spacer()
        }
        .navigationTitle("AR Measure Tool")
        .onAppear {
            // Start motion updates for inclinometer functionality
            motionManager.start()
            
            // Check ARKit availability for measuring modes
            if !ARWorldTrackingConfiguration.isSupported {
                measurementResult = "ARKit is not supported on this device."
            }
        }
        .onDisappear {
            // Stop the motion manager when view disappears
            motionManager.stop()
        }
        .onChange(of: motionManager.pitch) { newValue in
            // Update the X angle for bubble level
            xDegrees = motionManager.roll * 180 / .pi
        }
        .onChange(of: motionManager.roll) { newValue in
            // Update the Y angle for bubble level
            yDegrees = motionManager.pitch * 180 / .pi
        }
    }
}

// Placeholder for a UIViewRepresentable or UIViewControllerRepresentable 
// if the ARMeasure package provides a UIKit view.
// struct ARMeasureViewRepresentable: UIViewRepresentable {
//     @Binding var mode: MeasureMode
//     @Binding var result: String

//     func makeUIView(context: Context) -> ARMeasure.MeasureView { // Assuming MeasureView exists
//         let measureView = ARMeasure.MeasureView()
//         // Configure measureView based on mode
//         return measureView
//     }

//     func updateUIView(_ uiView: ARMeasure.MeasureView, context: Context) {
//         // Update view based on mode changes
//         // uiView.currentMode = mode 
//         // Potentially get results via delegate and update @Binding result
//     }
// }

// Motion manager class to handle device orientation
class MotionManager: ObservableObject {
    private let motionManager = CMMotionManager()
    
    @Published var pitch: Double = 0.0
    @Published var roll: Double = 0.0
    
    func start() {
        if motionManager.isDeviceMotionAvailable {
            motionManager.deviceMotionUpdateInterval = 0.1
            motionManager.startDeviceMotionUpdates(to: .main) { [weak self] motion, error in
                guard let motion = motion, error == nil else { return }
                self?.pitch = motion.attitude.pitch
                self?.roll = motion.attitude.roll
            }
        }
    }
    
    func stop() {
        motionManager.stopDeviceMotionUpdates()
    }
}

// Bubble level visualization component
struct BubbleLevelView: View {
    var xDegrees: Double
    var yDegrees: Double
    
    private var isLevel: Bool {
        return abs(xDegrees) < 0.5 && abs(yDegrees) < 0.5
    }
    
    var body: some View {
        VStack(spacing: 20) {
            Text("Bubble Level")
                .font(.headline)
                .foregroundColor(Color(HCTColor.primary))
            
            // Circular bubble level indicator
            ZStack {
                // Outer circle (container)
                Circle()
                    .stroke(Color(HCTColor.outline), lineWidth: 2)
                    .background(Circle().fill(Color(HCTColor.surface)))
                    .frame(width: 200, height: 200)
                
                // Center circle (target)
                Circle()
                    .stroke(Color(HCTColor.primary), lineWidth: 1)
                    .frame(width: 40, height: 40)
                
                // Bubble
                Circle()
                    .fill(isLevel ? Color.green : Color(HCTColor.primary))
                    .frame(width: 30, height: 30)
                    .offset(x: max(-75, min(75, CGFloat(xDegrees * 5))), 
                            y: max(-75, min(75, CGFloat(yDegrees * 5))))
            }
            
            // Angle readouts
            VStack(alignment: .leading, spacing: 10) {
                Text("X-Axis: \(xDegrees, specifier: "%.1f")°")
                    .foregroundColor(Color(HCTColor.onSurface))
                Text("Y-Axis: \(yDegrees, specifier: "%.1f")°")
                    .foregroundColor(Color(HCTColor.onSurface))
                Text(isLevel ? "Level!" : "Not Level")
                    .foregroundColor(isLevel ? .green : Color(HCTColor.error))
                    .fontWeight(.bold)
            }
        }
        .padding()
    }
}

struct ARMeasuringScreen_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            ARMeasuringScreen()
        }
        .preferredColorScheme(.light)
        
        NavigationView {
            ARMeasuringScreen()
        }
        .preferredColorScheme(.dark)
    }
}

