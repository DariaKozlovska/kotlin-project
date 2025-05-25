plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services) // Upewnij się, że ten plugin jest dodany i synchronizowany
    id("kotlin-kapt")
}

android {
    namespace = "com.example.dsw51762_kotlin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dsw51762_kotlin"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        // Upewnij się, że to jest kompatybilne z Twoją wersją Kotlina (kotlin = "1.9.0" w libs.versions.toml)
        kotlinCompilerExtensionVersion = "1.5.1" // Może być konieczna aktualizacja do nowszej wersji np. "1.5.11" dla Kotlin 1.9.20 lub nowszej dla 1.9.0
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Platformy BOM
    implementation(platform(libs.firebase.bom))
    implementation(platform(libs.androidx.compose.bom))

    // Moduły Firebase (bez podawania wersji - BOM zarządza wersjami)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)

    // AndroidX Core KTX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose UI, Graphics, Tooling (wersje zarządzane przez composeBom)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3) // Material3 jest w composeBom

    // Usuń ręczne wersje Compose, jeśli używasz composeBom
    // implementation("androidx.compose.ui:ui:1.4.0") // Usuń lub sprawdź wersję z BOM
    // implementation("androidx.compose.material3:material3:1.0.0") // Usuń lub sprawdź wersję z BOM
    // implementation("androidx.compose.material:material-icons-extended:1.4.0") // Jeśli to nie jest w BOM, dodaj z aktualną wersją lub dodaj do libs.versions.toml

    // Navigation Compose
    val nav_version = "2.8.8" // Sprawdź najnowszą stabilną wersję
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Room
    val room_version = "2.6.1" // Wersja już zdefiniowana w libs.versions.toml, możesz użyć version.ref
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation(libs.androidx.room.ktx) // to już masz zdefiniowane w libs.versions.toml
    // Usuń te, jeśli są już w libs.versions.toml i używasz ich aliasów
    // implementation(libs.androidx.room.common)
    // implementation(libs.androidx.room.ktx)
     implementation(libs.androidx.runtime.livedata) // Sprawdź, czy jest potrzebne i czy jest w BOM

    // Biometric
    implementation("androidx.biometric:biometric:1.1.0") // Sprawdź najnowszą stabilną wersję

    // Testy
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4) // Wersja zarządzana przez composeBom
    androidTestImplementation(libs.androidx.ui.test.android) // Wersja zarządzana przez composeBom

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.material.icons.extended)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
}