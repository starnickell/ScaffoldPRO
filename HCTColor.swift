import UIKit

/// HCTColor - ScaffoldPRO color system implementation for iOS
/// This class provides harmonized color tokens that match Android implementation
/// HCT stands for Hue, Chroma, Tone - the color system used for Material You
public struct HCTColor {
    // MARK: - Light Theme Colors
    
    // Primary colors
    public static let lightPrimary = UIColor(red: 0/255, green: 101/255, blue: 142/255, alpha: 1) // #00658E
    public static let lightOnPrimary = UIColor(red: 255/255, green: 255/255, blue: 255/255, alpha: 1) // #FFFFFF
    public static let lightPrimaryContainer = UIColor(red: 199/255, green: 231/255, blue: 255/255, alpha: 1) // #C7E7FF
    public static let lightOnPrimaryContainer = UIColor(red: 0/255, green: 30/255, blue: 46/255, alpha: 1) // #001E2E
    
    // Secondary colors
    public static let lightSecondary = UIColor(red: 79/255, green: 97/255, blue: 110/255, alpha: 1) // #4F616E
    public static let lightOnSecondary = UIColor(red: 255/255, green: 255/255, blue: 255/255, alpha: 1) // #FFFFFF
    public static let lightSecondaryContainer = UIColor(red: 210/255, green: 229/255, blue: 245/255, alpha: 1) // #D2E5F5
    public static let lightOnSecondaryContainer = UIColor(red: 11/255, green: 30/255, blue: 41/255, alpha: 1) // #0B1E29
    
    // Tertiary colors
    public static let lightTertiary = UIColor(red: 100/255, green: 89/255, blue: 123/255, alpha: 1) // #64597B
    public static let lightOnTertiary = UIColor(red: 255/255, green: 255/255, blue: 255/255, alpha: 1) // #FFFFFF
    public static let lightTertiaryContainer = UIColor(red: 234/255, green: 221/255, blue: 255/255, alpha: 1) // #EADDFF
    public static let lightOnTertiaryContainer = UIColor(red: 32/255, green: 22/255, blue: 53/255, alpha: 1) // #201635
    
    // Error colors
    public static let lightError = UIColor(red: 186/255, green: 26/255, blue: 26/255, alpha: 1) // #BA1A1A
    public static let lightOnError = UIColor(red: 255/255, green: 255/255, blue: 255/255, alpha: 1) // #FFFFFF
    public static let lightErrorContainer = UIColor(red: 255/255, green: 218/255, blue: 214/255, alpha: 1) // #FFDAD6
    public static let lightOnErrorContainer = UIColor(red: 65/255, green: 0/255, blue: 2/255, alpha: 1) // #410002
    
    // Background & Surface colors
    public static let lightBackground = UIColor(red: 252/255, green: 252/255, blue: 255/255, alpha: 1) // #FCFCFF
    public static let lightOnBackground = UIColor(red: 26/255, green: 28/255, blue: 30/255, alpha: 1) // #1A1C1E
    public static let lightSurface = UIColor(red: 252/255, green: 252/255, blue: 255/255, alpha: 1) // #FCFCFF
    public static let lightOnSurface = UIColor(red: 26/255, green: 28/255, blue: 30/255, alpha: 1) // #1A1C1E
    public static let lightSurfaceVariant = UIColor(red: 221/255, green: 227/255, blue: 234/255, alpha: 1) // #DDE3EA
    public static let lightOnSurfaceVariant = UIColor(red: 65/255, green: 72/255, blue: 77/255, alpha: 1) // #41484D
    
    // Other UI colors
    public static let lightOutline = UIColor(red: 113/255, green: 120/255, blue: 126/255, alpha: 1) // #71787E
    public static let lightInverseOnSurface = UIColor(red: 240/255, green: 241/255, blue: 243/255, alpha: 1) // #F0F1F3
    public static let lightInverseSurface = UIColor(red: 46/255, green: 49/255, blue: 51/255, alpha: 1) // #2E3133
    public static let lightInversePrimary = UIColor(red: 138/255, green: 206/255, blue: 255/255, alpha: 1) // #8ACEFF
    public static let lightSurfaceTint = UIColor(red: 0/255, green: 101/255, blue: 142/255, alpha: 1) // #00658E
    public static let lightOutlineVariant = UIColor(red: 193/255, green: 199/255, blue: 206/255, alpha: 1) // #C1C7CE
    public static let lightScrim = UIColor(red: 0/255, green: 0/255, blue: 0/255, alpha: 1) // #000000
    
    // MARK: - Dark Theme Colors
    
    // Primary colors
    public static let darkPrimary = UIColor(red: 138/255, green: 206/255, blue: 255/255, alpha: 1) // #8ACEFF
    public static let darkOnPrimary = UIColor(red: 0/255, green: 52/255, blue: 76/255, alpha: 1) // #00344C
    public static let darkPrimaryContainer = UIColor(red: 0/255, green: 76/255, blue: 109/255, alpha: 1) // #004C6D
    public static let darkOnPrimaryContainer = UIColor(red: 199/255, green: 231/255, blue: 255/255, alpha: 1) // #C7E7FF
    
    // Secondary colors
    public static let darkSecondary = UIColor(red: 182/255, green: 201/255, blue: 216/255, alpha: 1) // #B6C9D8
    public static let darkOnSecondary = UIColor(red: 33/255, green: 51/255, blue: 63/255, alpha: 1) // #21333F
    public static let darkSecondaryContainer = UIColor(red: 56/255, green: 73/255, blue: 86/255, alpha: 1) // #384956
    public static let darkOnSecondaryContainer = UIColor(red: 210/255, green: 229/255, blue: 245/255, alpha: 1) // #D2E5F5
    
    // Tertiary colors
    public static let darkTertiary = UIColor(red: 206/255, green: 192/255, blue: 232/255, alpha: 1) // #CEC0E8
    public static let darkOnTertiary = UIColor(red: 53/255, green: 43/255, blue: 75/255, alpha: 1) // #352B4B
    public static let darkTertiaryContainer = UIColor(red: 76/255, green: 66/255, blue: 99/255, alpha: 1) // #4C4263
    public static let darkOnTertiaryContainer = UIColor(red: 234/255, green: 221/255, blue: 255/255, alpha: 1) // #EADDFF
    
    // Error colors
    public static let darkError = UIColor(red: 255/255, green: 180/255, blue: 171/255, alpha: 1) // #FFB4AB
    public static let darkOnError = UIColor(red: 105/255, green: 0/255, blue: 5/255, alpha: 1) // #690005
    public static let darkErrorContainer = UIColor(red: 147/255, green: 0/255, blue: 10/255, alpha: 1) // #93000A
    public static let darkOnErrorContainer = UIColor(red: 255/255, green: 218/255, blue: 214/255, alpha: 1) // #FFDAD6
    
    // Background & Surface colors
    public static let darkBackground = UIColor(red: 26/255, green: 28/255, blue: 30/255, alpha: 1) // #1A1C1E
    public static let darkOnBackground = UIColor(red: 226/255, green: 226/255, blue: 229/255, alpha: 1) // #E2E2E5
    public static let darkSurface = UIColor(red: 26/255, green: 28/255, blue: 30/255, alpha: 1) // #1A1C1E
    public static let darkOnSurface = UIColor(red: 226/255, green: 226/255, blue: 229/255, alpha: 1) // #E2E2E5
    public static let darkSurfaceVariant = UIColor(red: 65/255, green: 72/255, blue: 77/255, alpha: 1) // #41484D
    public static let darkOnSurfaceVariant = UIColor(red: 193/255, green: 199/255, blue: 206/255, alpha: 1) // #C1C7CE
    
    // Other UI colors
    public static let darkOutline = UIColor(red: 139/255, green: 145/255, blue: 152/255, alpha: 1) // #8B9198
    public static let darkInverseOnSurface = UIColor(red: 26/255, green: 28/255, blue: 30/255, alpha: 1) // #1A1C1E
    public static let darkInverseSurface = UIColor(red: 226/255, green: 226/255, blue: 229/255, alpha: 1) // #E2E2E5
    public static let darkInversePrimary = UIColor(red: 0/255, green: 101/255, blue: 142/255, alpha: 1) // #00658E
    public static let darkSurfaceTint = UIColor(red: 138/255, green: 206/255, blue: 255/255, alpha: 1) // #8ACEFF
    public static let darkOutlineVariant = UIColor(red: 65/255, green: 72/255, blue: 77/255, alpha: 1) // #41484D
    public static let darkScrim = UIColor(red: 0/255, green: 0/255, blue: 0/255, alpha: 1) // #000000
    
    // MARK: - Dynamic Color Helpers
    
    /// Returns dynamic color that adapts to the current interface style
    /// - Parameters:
    ///   - light: Color for light mode
    ///   - dark: Color for dark mode
    /// - Returns: Dynamic UIColor
    public static func dynamicColor(light: UIColor, dark: UIColor) -> UIColor {
        return UIColor { traitCollection in
            return traitCollection.userInterfaceStyle == .dark ? dark : light
        }
    }
    
    // MARK: - Semantic Colors
    
    /// Primary brand color with dynamic light/dark support
    public static let primary = dynamicColor(light: lightPrimary, dark: darkPrimary)
    
    /// Background color with dynamic light/dark support
    public static let background = dynamicColor(light: lightBackground, dark: darkBackground)
    
    /// Surface color with dynamic light/dark support
    public static let surface = dynamicColor(light: lightSurface, dark: darkSurface)
    
    /// Error color with dynamic light/dark support
    public static let error = dynamicColor(light: lightError, dark: darkError)
    
    /// Text on background for content (WCAG AA compliant)
    public static let contentText = dynamicColor(light: lightOnBackground, dark: darkOnBackground)
    
    /// Primary action color (WCAG AA compliant)
    public static let actionPrimary = dynamicColor(light: lightPrimary, dark: darkPrimary)
    
    /// Secondary action color (WCAG AA compliant)
    public static let actionSecondary = dynamicColor(light: lightSecondary, dark: darkSecondary)
    
    /// Accent color for highlights (WCAG AA compliant)
    public static let accent = dynamicColor(light: lightTertiary, dark: darkTertiary)
    
    // MARK: - WCAG AA Compliance
    
    /// Verifies contrast meets WCAG AA standards (4.5:1 for normal text, 3:1 for large text)
    /// - Parameters:
    ///   - foreground: Foreground color
    ///   - background: Background color
    ///   - isLargeText: Whether the text is large (≥18pt or bold ≥14pt)
    /// - Returns: Whether the contrast ratio meets WCAG AA standards
    public static func isWCAGAACompliant(foreground: UIColor, background: UIColor, isLargeText: Bool = false) -> Bool {
        let ratio = contrastRatio(between: foreground, and: background)
        return isLargeText ? ratio >= 3.0 : ratio >= 4.5
    }
    
    /// Calculates the contrast ratio between two colors
    /// - Parameters:
    ///   - color1: First color
    ///   - color2: Second color
    /// - Returns: Contrast ratio (1-21)
    private static func contrastRatio(between color1: UIColor, and color2: UIColor) -> CGFloat {
        let luminance1 = relativeLuminance(of: color1)
        let luminance2 = relativeLuminance(of: color2)
        
        let lighter = max(luminance1, luminance2)
        let darker = min(luminance1, luminance2)
        
        return (lighter + 0.05) / (darker + 0.05)
    }
    
    /// Calculates the relative luminance of a color
    /// - Parameter color: Color to calculate luminance for
    /// - Returns: Relative luminance value (0-1)
    private static func relativeLuminance(of color: UIColor) -> CGFloat {
        var red: CGFloat = 0
        var green: CGFloat = 0
        var blue: CGFloat = 0
        var alpha: CGFloat = 0
        
        color.getRed(&red, green: &green, blue: &blue, alpha: &alpha)
        
        let r = red <= 0.03928 ? red/12.92 : pow((red + 0.055)/1.055, 2.4)
        let g = green <= 0.03928 ? green/12.92 : pow((green + 0.055)/1.055, 2.4)
        let b = blue <= 0.03928 ? blue/12.92 : pow((blue + 0.055)/1.055, 2.4)
        
        return 0.2126 * r + 0.7152 * g + 0.0722 * b
    }
    
    // Additional semantic colors can be added as needed
}

