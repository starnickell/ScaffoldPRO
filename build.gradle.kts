plugins {
    id("com.android.application")
    kotlin("android")
    // TODO: Add detekt plugin if it's a Gradle plugin
    // id("io.gitlab.arturbosch.detekt") version "1.23.0" // Example version
}

android {
    namespace = "com.scaffoldpro.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.scaffoldpro.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 2
        versionName = "5.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3" // Example, use appropriate version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Framework APIs android.speech.SpeechRecognizer & android.speech.tts.TextToSpeech will be used directly.
    // No specific Gradle dependencies required for these core functionalities.

    // TODO: Add detekt for static analysis (if not a plugin, then as a dependency for a task)
    // detektPlugins "io.gitlab.arturbosch.detekt:detekt-formatting:1.23.0" // Example

    // TODO: Add Google Accessibility Test Framework for Android
    // androidTestImplementation "com.google.android.apps.common.testing.accessibility.framework:accessibility-test-framework:4.0.0" // Example version

    // TODO: Add Flank (usually configured in CI, not as a direct app dependency)

    // TODO: Add Compose screenshot testing library
    // androidTestImplementation "com.github.your-compose-screenshot-lib:library:version" // Example
    
    // TODO: Add LocalizationScanner (likely a CI script integration rather than direct dependency)
}

// TODO: Configure detekt task if using the plugin
// detekt {
//    config = files("$projectDir/config/detekt/detekt.yml")
//    buildUponDefaultConfig = true
// }

