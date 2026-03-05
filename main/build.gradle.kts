plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "de.blinkt.openvpn"
    compileSdk = 34

    // 📍 تفعيل الخاصية التي طلبها الخطأ في صورتك
    buildFeatures {
        buildConfig = true
        aidl = true
    }

    defaultConfig {
        applicationId = "de.blinkt.openvpn"
        minSdk = 21
        targetSdk = 34
        versionCode = 219
        versionName = "0.7.64"

        ndk {
            abiFilters.add("arm64-v8a")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    flavorDimensions += listOf("implementation", "ovpnimpl")
    productFlavors {
        create("ui") { dimension = "implementation" }
        create("ovpn2") {
            dimension = "ovpnimpl"
            buildConfigField("boolean", "openvpn3", "false")
        }
    }
}

dependencies {
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.android.view.material)
}
