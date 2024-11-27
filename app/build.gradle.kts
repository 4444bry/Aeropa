plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.aeropa"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.aeropa"
        minSdk = 29
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures{
        viewBinding=true
    }
}

//dependencies {
//
//    implementation(libs.appcompat)
//    implementation(libs.material)
//    implementation(libs.activity)
//    implementation(libs.constraintlayout)
//    implementation("com.github.bumptech.glide:glide:4.13.0")
//    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")
//    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
//    implementation ("com.google.firebase:firebase-auth:22.1.1")
//    implementation(libs.firebase.database)
//    implementation(libs.annotation)
//    implementation(libs.lifecycle.livedata.ktx)
//    implementation(libs.lifecycle.viewmodel.ktx)
//    implementation(libs.firebase.auth)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.ext.junit)
//    androidTestImplementation(libs.espresso.core)
//
//}

dependencies {
    // Library UI
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Library tambahan
    implementation("com.github.bumptech.glide:glide:4.13.0")
    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

    // Firebase BOM
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth")

    // Firebase Realtime Database
    implementation("com.google.firebase:firebase-database")

    // Lifecycle dan ViewModel
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    // Annotation (Opsional, pastikan ini digunakan di proyek Anda)
    implementation(libs.annotation)

    // Unit Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
