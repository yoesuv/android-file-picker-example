plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.yoesuv.filepicker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yoesuv.filepicker"
        minSdk = 24
        targetSdk = 34
        versionCode = 7
        versionName = "2.4.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments += mapOf(
            "clearPackageData" to "true",
        )
        setProperty("archivesBaseName", "$applicationId-v$versionCode($versionName)")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
    flavorDimensions += listOf(
        "environment",
    )
    productFlavors {
        create("forTest") {
            resValue("string", "app_name", "Picker & Permission TEST")
            applicationIdSuffix = ".test"
        }
        create("production") {
            resValue("string", "app_name", "Picker & Permission")
        }
    }
    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.idling.resource)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.uiautomator)
    androidTestUtil(libs.androidx.test.orchestrator)

    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("com.google.android.gms:play-services-location:21.1.0")

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp3.logging)
    implementation(libs.compressor)

    implementation(libs.glide)
    implementation(libs.glide.ksp)
}