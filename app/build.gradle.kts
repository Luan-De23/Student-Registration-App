plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    // KSP plugin
    //https://kotlinlang.org/docs/ksp-quickstart.html
    id("com.google.devtools.ksp") version "2.3.10"
}

android {
    namespace = "com.example.studentregistration"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.studentregistration"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    val navVersion = "2.8.5"

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation("androidx.compose.material:material-icons-extended")


    //room-db
    //https://developer.android.com/jetpack/androidx/releases/room
    val room_version = "2.8.5"

    implementation("androidx.room:room-runtime:$room_version")

    //
    ksp("androidx.room:room-compiler:$room_version")

    //
    implementation("androidx.room:room-ktx:$room_version")


    //
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // --------END-----

    //runtime-datalive
    //https://developer.android.com/jetpack/androidx/releases/compose-runtime#kts
    implementation("androidx.compose.runtime:runtime:1.12.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.12.1")

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}