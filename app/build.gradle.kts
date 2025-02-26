plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.shoppinglisttesting"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.shoppinglisttesting"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.shoppinglisttesting.HiltTestRunner"
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    testOptions{
        unitTests.isReturnDefaultValues = false
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.10.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")


    implementation("androidx.navigation:navigation-compose:2.8.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    //ImageLoading
    implementation("io.coil-kt:coil-compose:2.7.0")


    // Architectural Components
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    // Lifecycle
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    // Room
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    kapt( "androidx.room:room-compiler:2.6.1")

    // Kotlin Extensions and Coroutines support for Room
    implementation ("androidx.room:room-ktx:2.6.1")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")




    // Coroutine Lifecycle Scopes
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    



    // Activity KTX for viewModels()
    implementation ("androidx.activity:activity-ktx:1.10.0")

    //Dagger - Hilt
    val dagger = "2.48.1"
    implementation("com.google.dagger:hilt-android:$dagger")
    kapt("com.google.dagger:hilt-android-compiler:$dagger")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")




    // Timber
    implementation ("com.jakewharton.timber:timber:4.7.1")

    // Local Unit Tests
    implementation ("androidx.test:core:1.6.1")
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.hamcrest:hamcrest-all:1.3")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation( "org.robolectric:robolectric:4.3.1")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
    testImplementation ("com.google.truth:truth:1.4.4")
    testImplementation ("org.mockito:mockito-core:2.25.0")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("app.cash.turbine:turbine:1.2.0")



// Instrumented Unit Tests
    androidTestImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation ("com.google.truth:truth:1.4.4")
    androidTestImplementation ("androidx.test.ext:junit:1.2.1")
    androidTestImplementation ("org.mockito:mockito-core:2.25.0")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.8")
    androidTestImplementation ("androidx.navigation:navigation-testing:2.8.7")


    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.7.8")




    // For Robolectric tests.
    testImplementation("com.google.dagger:hilt-android-testing:$dagger")
    // ...with Kotlin.
    kaptTest("com.google.dagger:hilt-android-compiler:$dagger")

    // For instrumented tests.
    androidTestImplementation("com.google.dagger:hilt-android-testing:$dagger")
    // ...with Kotlin.
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$dagger")





}

kapt {
    correctErrorTypes = true
}

