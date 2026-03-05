import com.android.build.gradle.api.ApplicationVariant

plugins {
    alias(libs.plugins.android.application)
    id("checkstyle")
}

android {
    namespace = "de.blinkt.openvpn"
    compileSdk = 34

    defaultConfig {
        applicationId = "de.blinkt.openvpn"
        minSdk = 21
        targetSdk = 34
        versionCode = 219
        versionName = "0.7.64"

        // تقليص اللغات
        resConfigs("ar", "en")

        // تحديد معالج واحد فقط لتقليص الحجم (السر هنا)
        ndk {
            abiFilters.add("arm64-v8a")
        }
    }

    buildTypes {
        getByName("release") {
            // استخدام توقيع الديباج لضمان نجاح البناء على GitHub
            signingConfig = signingConfigs.getByName("debug")
            
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    // حذف قسم الـ splits تماماً لأنه يسبب التعارض مع abiFilters
    splits {
        abi {
            isEnable = false
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
    add("uiImplementation", libs.androidx.appcompat)
    add("uiImplementation", libs.androidx.constraintlayout)
    add("uiImplementation", libs.android.view.material)
}
