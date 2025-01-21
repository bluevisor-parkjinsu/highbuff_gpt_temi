import com.android.build.api.dsl.LibraryBaseFlavor
import com.android.build.api.dsl.LibraryProductFlavor

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.dagger.hilt.android)

    // for TensorFlowLite audio classification.
    alias(libs.plugins.de.undercouch.download)
}

private val deviceType_default = "default"
private val deviceType_cloi = "cloi"
private val deviceType_temi = "temi"

android {
    namespace = "kr.bluevisor.robot.libs"
    compileSdk = 35

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "DEVICE_TYPE__DEFAULT", "\"$deviceType_default\"")
        buildConfigField("String", "DEVICE_TYPE__CLOI", "\"$deviceType_cloi\"")
        buildConfigField("String", "DEVICE_TYPE__TEMI", "\"$deviceType_temi\"")
        setBuildVariables(deviceType_default)
    }

    buildFeatures.buildConfig = true
    flavorDimensions += "deviceType"

    productFlavors {
        create(deviceType_default) {
            dimension = "deviceType"
            isDefault = true
        }
        createProductFlavorForDeviceType(deviceType_cloi)
        createProductFlavorForDeviceType(deviceType_temi)
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "/META-INF/INDEX.LIST"
            merges += "/META-INF/DEPENDENCIES"
        }
    }
}

// for TensorFlowLite audio classification.
project.ext.apply {
    set("ASSET_DIR", "$projectDir/src/main/assets")
    set("TEST_ASSET_DIR", "$projectDir/src/androidTest/assets")
}

// for TensorFlowLite audio classification.
apply(from = "additional_tasks__tensor_flow_lite__audio_classification__download_model.gradle")

dependencies {
    coreLibraryDesugaring(libs.android.tools.desugar.jdk.libs)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.camerax.camera2)
    implementation(libs.androidx.camerax.lifecycle)
    implementation(libs.androidx.camerax.view)
    implementation(libs.androidx.window)
    implementation(libs.androidx.concurrent.futures.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.google.dagger.hilt.android)
    ksp(libs.google.dagger.hilt.android.compiler)
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.retrofit2.converter.moshi)
    implementation(libs.squareup.okhttp3.logging.interceptor)
    implementation(libs.squareup.moshi)
    implementation(libs.squareup.moshi.kotlin)
    implementation(libs.squareup.okio)
    implementation(libs.tensorflow.lite.task.audio)
    implementation(libs.arthenica.ffmpeg.kit.full)
    implementation(libs.parksanggwon.tedpermission.coroutine)
    implementation(libs.robotemi.sdk)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // for LG-CLOi thirdPartyLibs.
//    api(group = "com.lge.thirdpartylib", name = "ThirdPartyLib_1.0.0.18", ext = "aar")
//    api(group = "com.lge.rtsp", name = "RtspLib_1.0.0.2_debug", ext = "aar")
    api("com.google.code.gson:gson:2.9.1")
    api("io.grpc:grpc-okhttp:1.34.1")
    api("com.google.cloud:google-cloud-dialogflow:3.0.1")
}

private fun LibraryBaseFlavor.setBuildVariables(deviceType: String) {
    val forDefault = deviceType == deviceType_default
    val forCloi = deviceType == deviceType_cloi
    val forTemi = deviceType == deviceType_temi

    manifestPlaceholders.apply {
        put("forDefault", forDefault)
        put("forCloi", forCloi)
        put("forTemi", forTemi)
    }

    buildConfigField("String", "DEVICE_TYPE", "\"$deviceType\"")
    buildConfigField("Boolean", "FOR_DEFAULT", forDefault.toString())
    buildConfigField("Boolean", "FOR_CLOI", forCloi.toString())
    buildConfigField("Boolean", "FOR_TEMI", forTemi.toString())
}

private fun NamedDomainObjectContainer<LibraryProductFlavor>.createProductFlavorForDeviceType(
    flavorName: String
) {
    create(flavorName) {
        dimension = "deviceType"
        setBuildVariables(flavorName)
    }
}