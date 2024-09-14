plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.android.consultaruc"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.consultaruc"
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
}

dependencies {


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.cardview)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.inappmessaging)
    implementation("com.android.volley:volley:1.2.1")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)

}