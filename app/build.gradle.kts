plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.moondroid.pharmacyproject01"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moondroid.pharmacyproject01"
        minSdk = 23
        targetSdk = 33
        versionCode = 4
        versionName = "1.0.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.activity:activity-ktx:1.8.2")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")

    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.vanniktech:android-image-cropper:4.5.0")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.android.gms:play-services-location:21.1.0")
    implementation("com.naver.maps:map-sdk:3.17.0")

    implementation ("com.tickaroo.tikxml:annotation:0.8.13")
    implementation ("com.tickaroo.tikxml:core:0.8.13")
    implementation ("com.tickaroo.tikxml:retrofit-converter:0.8.13")
    kapt ("com.tickaroo.tikxml:processor:0.8.13")

    // lottie for animation
    implementation ("com.airbnb.android:lottie:6.3.0")

    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}