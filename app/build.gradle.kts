import kotlin.script.experimental.jvm.util.classpathFromClass

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}
android {
    namespace = "za.thirdyear.schedu"
    compileSdk = 34

    defaultConfig {
        applicationId = "za.thirdyear.schedu"
        minSdk = 25
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation ("androidx.core:core-ktx:1.13.0")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.11.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.compose.ui:ui-graphics-android:1.6.6")
    implementation ("androidx.compose.ui:ui-android:1.6.6")
    implementation ("com.google.firebase:firebase-bom:33.1.0") // Use Firebase BoM to manage versions
    implementation ("com.google.firebase:firebase-auth-ktx:23.0.0") // Version managed by BoM
    implementation ("com.google.firebase:firebase-database-ktx:21.0.0") // Version managed by BoM
    implementation ("com.google.firebase:firebase-firestore-ktx:25.0.0") // Version managed by BoM
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.core:core-splashscreen:1.1.0-rc01")
    implementation ("androidx.compose.ui:ui:1.6.6")
    implementation ("androidx.compose.material:material:1.6.6")
    implementation ("androidx.activity:activity-compose:1.9.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.6.6")
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.6")
}
