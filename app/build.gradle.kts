plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.practical06"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.practical06"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    // AndroidX Core and UI libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Unit Testing
    testImplementation(libs.junit)

    // Android InstrumentedTesting
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Third-party libraries
    implementation(libs.gson)
}