import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

val apikeyPropertiesFile: File = rootProject.file("apikey.properties")
val apikeyProperties = Properties()
apikeyProperties.load(FileInputStream(apikeyPropertiesFile))

android {
    namespace = "com.spyrakis.movieflix"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.spyrakis.movieflix"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", apikeyProperties["API_KEY"].toString())

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    testOptions.unitTests {
        isIncludeAndroidResources = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    val androidLegacyVersion = "1.0.0"
    val recyclerVersion = "1.3.2"
    val viewModelVersion = "2.7.0"
    implementation("androidx.legacy:legacy-support-v4:$androidLegacyVersion")
    implementation("androidx.recyclerview:recyclerview:$recyclerVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelVersion")
    val androidxCoreVersion = "1.12.0"
    val androidxAppcompatVersion = "1.6.1"
    val androidMaterialVersion = "1.11.0"
    val androidxConstraintlayoutVersion = "2.1.4"
    implementation("androidx.core:core-ktx:$androidxCoreVersion")
    implementation("androidx.appcompat:appcompat:$androidxAppcompatVersion")
    implementation("com.google.android.material:material:$androidMaterialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$androidxConstraintlayoutVersion")

    //navigation
    val navVersion = "2.7.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")


    //Hilt
    val hiltVersion = "2.50"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-compiler:$hiltVersion")

    //Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    //loggingInterceptor
    val okhttp3Version = "4.11.0"
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp3Version")

    //Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    //Timber
    val timberVersion = "5.0.1"
    implementation("com.jakewharton.timber:timber:$timberVersion")

    //Data store
    val dataStoreVersion = "1.0.0"
    implementation("androidx.datastore:datastore-preferences:$dataStoreVersion")

    //Glide
    val glideVersion = "4.15.1"
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    //swipe refresh layout
    val swipeRefreshLayoutVersion = "1.2.0-alpha01"
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshLayoutVersion")

    val junitVersion = "4.13.2"
    val extJunitVersion = "1.1.5"
    val espressoVersion = "3.5.1"
    val coroutinesTestVersion = "1.7.1"
    val navigation = "2.7.6"
    testImplementation("junit:junit:$junitVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesTestVersion")
    androidTestImplementation("androidx.test.ext:junit:$extJunitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:$espressoVersion")
    androidTestImplementation("androidx.navigation:navigation-fragment-ktx:$navigation")
    androidTestImplementation("androidx.navigation:navigation-ui-ktx:$navigation")

}