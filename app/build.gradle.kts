plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization") version "1.8.0"
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
//    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    alias(libs.plugins.compose.compiler)
    id("androidx.room")
}



android {
    namespace = "io.github.mushroom_cn.timemanager"
    compileSdk = 35


    defaultConfig {
        applicationId = "io.github.mushroom_cn.timemanager"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


//        kapt {
//            arguments {
//                arg("room.schemaLocation", "$projectDir/schemas")
//            }
//        }

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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/io.netty.versions.properties"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/NOTICE.md"
        }
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.tv.foundation)
    implementation(libs.androidx.tv.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
//    implementation(libs.androidx.work.runtime.ktx.v271)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.hilt.work)
    runtimeOnly(libs.androidx.foundation)

    // 二维码生成工具
    // https://mvnrepository.com/artifact/com.google.zxing/core
    implementation(libs.core)

    // 网络工具
    // https://commons.apache.org/proper/commons-net/index.html
    implementation(libs.commons.net)
    //https://commons.apache.org/proper/commons-codec/
    implementation(libs.commons.codec)

    // 序列化层
    implementation(libs.kotlinx.serialization.json)

    // websocket 层
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.config.yaml)

    // room持久层
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)
    implementation(libs.androidx.room.guava)
    implementation(libs.androidx.room.paging)

    // unit test
    androidTestImplementation(libs.test.core)
    // Kotlin extensions for androidx.test.core
    androidTestImplementation(libs.core.ktx)
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.truth)
    androidTestImplementation(libs.androidx.espresso.idling.resource)

    // hilt test
    // For Robolectric tests.
    // For instrumented tests.
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)
//    testAnnotationProcessor (libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)
//    androidTestAnnotationProcessor (libs.hilt.android.compiler)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

//    kapt ("com.google.dagger:hilt-android-compiler:2.41")
    // rxjava
    implementation(libs.rxandroid)
    implementation(libs.rxjava)
    implementation(libs.androidx.room.rxjava3)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

// (Java only)
    implementation(libs.androidx.work.runtime)

    // Kotlin + coroutines
    implementation(libs.androidx.work.runtime.ktx)

    // optional - RxJava2 support
    implementation(libs.androidx.work.rxjava3)

    // optional - GCMNetworkManager support
//    implementation(libs.androidx.work.gcm)

    // optional - Test helpers
    androidTestImplementation(libs.androidx.work.testing)

    // optional - Multiprocess support
    implementation(libs.androidx.work.multiprocess)


    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // ViewModel utilities for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)
    // Lifecycles only (without ViewModel or LiveData)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Lifecycle utilities for Compose
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Saved state module for ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)

    // Annotation processor
    kapt(libs.androidx.lifecycle.compiler)
    // alternately - if using Java8, use the following instead of lifecycle-compiler
    implementation(libs.androidx.lifecycle.common.java8)

    // optional - helpers for implementing LifecycleOwner in a Service
    implementation(libs.androidx.lifecycle.service)

    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    implementation(libs.androidx.lifecycle.process)

    // optional - ReactiveStreams support for LiveData
    implementation(libs.androidx.lifecycle.reactivestreams.ktx)

    // optional - Test helpers for LiveData
    testImplementation(libs.androidx.core.testing)

    // optional - Test helpers for Lifecycle runtime
    testImplementation(libs.androidx.lifecycle.runtime.testing)

    implementation(libs.androidx.biometric)

    implementation(libs.prettytime)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
}
composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
}

kapt {
    correctErrorTypes = true
}