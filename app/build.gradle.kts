plugins {
    id("com.android.application")
}

android {
    namespace = "com.trg.pbbvoting"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.trg.pbbvoting"
        minSdk = 31
        targetSdk = 37
        versionCode = 2
        versionName = "2.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-ktx:1.9.3")
}
