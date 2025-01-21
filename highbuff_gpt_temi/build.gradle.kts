import com.android.build.api.dsl.ApplicationBaseFlavor
import com.android.build.api.dsl.ApplicationProductFlavor
import com.google.protobuf.gradle.id

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.google.protobuf)
}

private val deviceType_default = "default"
private val deviceType_cloi = "cloi"
private val deviceType_temi = "temi"

android {
    namespace = "kr.bluevisor.robot.highbuff_gpt_temi"
    compileSdk = 35

    defaultConfig {
        applicationId = "kr.bluevisor.robot.highbuff_gpt_temi"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
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

dependencies {
    implementation(project(":_libs")) {
        exclude(group = "com.lge.thirdpartylib")
        exclude(group = "com.lge.rtsp")
        exclude(group = "com.google.code.gson", module = "gson")
        exclude(group = "io.grpc", module = "grpc-okhttp")
        exclude(group = "com.google.cloud", module = "google-cloud-dialogflow")
    }
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
    implementation(libs.androidx.datastore)
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
    implementation(libs.google.protobuf.java.lite)
    implementation(libs.bumptech.glide)
    implementation(libs.parksanggwon.tedpermission.coroutine)
    implementation(libs.robotemi.sdk)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

protobuf {
    protoc {
        val protobufCatalog = versionCatalogs.named("libs")
        val protocDependency =
            protobufCatalog.findVersion("googleProtobuf").get().requiredVersion
        artifact = "com.google.protobuf:protoc:$protocDependency"
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}

private fun ApplicationBaseFlavor.setBuildVariables(deviceType: String) {
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

private fun NamedDomainObjectContainer<ApplicationProductFlavor>.createProductFlavorForDeviceType(
    flavorName: String
) {
    create(flavorName) {
        dimension = "deviceType"
        applicationIdSuffix = ".$flavorName"
        versionNameSuffix = "-$flavorName"
        setBuildVariables(flavorName)
    }
}