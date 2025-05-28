plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.onlineshopapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.onlineshopapplication"
        minSdk = 24
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

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")

    //hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    //Room data base
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    //Datastore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    //coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    //lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //coil image loading
    implementation("io.coil-kt:coil:2.1.0")

    //calligraphy
    implementation("io.github.inflationx:calligraphy3:3.1.1")
    implementation("io.github.inflationx:viewpump:2.0.3")

    //client
    implementation("com.squareup.okhttp3:okhttp:4.9.1")

    //gson
    implementation("com.google.code.gson:gson:2.8.9")

    //others
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.faltenreich:skeletonlayout:5.0.0")
    implementation("com.todkars:shimmer-recyclerview:0.4.1")
    implementation("com.airbnb.android:lottie:5.2.0")
    implementation("kr.co.prnd:readmore-textview:1.0.0")
    implementation("com.github.MrNouri:DynamicSizes:1.0")
    implementation("com.github.GoodieBag:Pinview:v1.5")
    implementation("me.relex:circleindicator:2.1.6")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.github.aliab:Persian-Date-Picker-Dialog:1.8.0")
    implementation("com.github.SimformSolutionsPvtLtd:SSImagePicker:2.3")
    implementation("com.github.sparrow007:carouselrecyclerview:1.2.6")
}