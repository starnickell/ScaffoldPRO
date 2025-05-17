include(":app", ":arcoremeasure")
project(":arcoremeasure").projectDir = file("third_party/arcoremeasure")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google() 
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    versionCatalogs {
        create("libs") {
            // ARCore dependencies for measurement functionality
            alias("arcore").to("com.google.ar:core:1.36.0")
            alias("arcore-measure").to("io.github.h3x3l3:arcoremeasure:1.1.0")
            
            // SmartSnap dependencies for room scanning and object detection
            alias("smartsnapkit").to("io.github.anggrayudi:smartsnapkit:2.4.1")
            
            // Material You design libraries
            alias("material3").to("androidx.compose.material3:material3:1.1.1")
            alias("material-icons").to("androidx.compose.material:material-icons-extended:1.5.0")
        }
    }
}