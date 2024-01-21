plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation("androidx.compose.ui:ui:1.5.4")
                implementation("androidx.compose.ui:ui-tooling:1.5.4")
                implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
                implementation("androidx.compose.foundation:foundation:1.5.4")
                implementation("androidx.compose.material:material:1.5.4")
                implementation("androidx.activity:activity-compose:1.8.2")
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
                implementation("io.insert-koin:koin-androidx-compose:3.4.3")
                implementation("io.insert-koin:koin-core:3.4.3")
                implementation("androidx.navigation:navigation-compose:2.7.6")
                implementation("io.insert-koin:koin-android:3.4.3")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        applicationId = "com.myapplication.MyApplication"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
