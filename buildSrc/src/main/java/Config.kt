const val kotlinVersion = "1.3.50"
const val androidPluginVersion = "3.3.2"
const val dexCountVersion = "0.8.2"
const val googlePlayVersion = "4.2.0"
const val navigationPluginVersion = "2.1.0-alpha04"

// Compile dependencies

// Support
const val appCompatVersion = "1.1.0-alpha03"
const val constraintLayoutVersion = "1.1.2"
const val supportMaterialDesignVersion = "1.1.0-alpha04"
const val preferenceManagerVersion = "1.1.0"

// Architecture Components
const val archCompVersion = "2.0.0-rc01"
const val archExtensionVersion = archCompVersion

// Sqlite encrpytioni
const val sqlChipherVersion = "4.3.0"
const val sqliteVersion = "2.0.1"

// Navigation Components
const val navigationVersion = "2.1.0-alpha04"

// Core-KTX
const val coreKtxVersion = "1.0.2"

// Google Maps
const val playServicesMapsVersion = "16.0.0"
const val playServicesLocationVersion = "16.0.0"
const val playServicesPlacesVersion = "16.0.0"
const val playServicesAuthVersion = "17.0.0"

// Timber
const val timberVersion = "4.7.1"

// Retrofit
const val retrofitVersion = "2.5.0"
const val retrofitScalarVersion = "2.5.0"

// OkHttp
const val okhttpVersion = "3.12.1"
const val okhttpLoggingVersion = "3.11.0"

// RxJava
const val rxJava2Version = "2.2.17"

// RxKotlin
const val rxKotlinVersion = "2.3.0"

// RxAndroid
const val rxAndroidVersion = "2.1.1"

// RxBinding
const val rxBindingXVersion = "3.0.0-alpha2"

// RxMath
const val rxMathVersion = "0.20.0"

// Dagger
const val daggerVersion = "2.20"

// MultiDex
const val multiDexVersion = "1.0.1"

// Glide
const val glideVersion = "4.10.0"

// GlassFish
const val glassFishVersion = "10.0-b28"

// Epoxy
const val epoxyVersion = "2.19.0"

// CircleImageView
const val circleImageViewVersion = "2.2.0"

// SnapHelper
const val snapHelperVersion = "1.5"

// RxPermissions
const val runtimePermissionVersion = "0.10.2"

// ThreeTenABP
const val threeTenABPVersion = "1.1.1"

// Stetho
const val stethoVersion = "1.5.0"

// Crashlytics
const val crashlyticsVersion = "2.9.9"

// Test dependency versions
const val junitVersion = "4.12"
const val testRunnerVersion = "1.1.0"
const val espressoVersion = "3.1.0"
const val mockitoVersion = "2.25.1"
const val gsonVersion = "2.8.5"

// Facebook SDK Version
const val facebookSdkVersion = "[5,6)"

// https://proandroiddev.com/gradle-dependency-management-with-kotlin-94eed4df9a28

object BuildPlugins {
    val androidPlugin = "com.android.tools.build:gradle:$androidPluginVersion"
    val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    val dexCountPlugin = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:$dexCountVersion"
    val googlePlayPlugin = "com.google.gms:google-services:$googlePlayVersion"
    val navigationPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationPluginVersion"
}

object Android {
    // Manifest version information!
    private const val versionMajor = 0
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionBuild = 1 // bump for dogfood builds, public betas, etc.

    const val versionCode =
        versionMajor * 10000 + versionMinor * 1000 + versionPatch * 10 + versionBuild
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"

    const val compileSdkVersion = 29
    const val targetSdkVersion = 29
    const val minSdkVersion = 21
    const val buildToolsVersion = "29.0.2"
}

object Libs {

    // Kotlin
    val kotlinStdlb = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"

    // Support
    val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
    val cardView = "androidx.cardview:cardview:$appCompatVersion"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
    val supportDesign = "com.google.android.material:material:$supportMaterialDesignVersion"

    // Architecture Components
    val archExtensions = "androidx.lifecycle:lifecycle-extensions:$archExtensionVersion"
    val archExtensionsCompiler = "androidx.lifecycle:lifecycle-compiler:$archExtensionVersion"
    val room = "androidx.room:room-runtime:$archCompVersion"
    val roomRx = "androidx.room:room-rxjava2:$archCompVersion"
    val roomCompiler = "androidx.room:room-compiler:$archCompVersion"

    // SQLCipher
    val sqlcipher = "net.zetetic:android-database-sqlcipher:$sqlChipherVersion"
    val sqlite = "androidx.sqlite:sqlite:$sqliteVersion"

    // Navigation Components
    val navigationFragments = "androidx.navigation:navigation-fragment:$navigationVersion"
    val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
    val navigationFragmentUI = "androidx.navigation:navigation-ui-ktx:$navigationVersion"

    // Core-KTX
    val coreKTX = "androidx.core:core-ktx:$coreKtxVersion"

    // Google Maps
    val playServicesMaps = "com.google.android.gms:play-services-maps:$playServicesMapsVersion"
    val playServicesPlaces = "com.google.android.gms:play-services-places:$playServicesPlacesVersion"
    val playServicesLocation = "com.google.android.gms:play-services-location:$playServicesLocationVersion"
    val playServicesAuth = "com.google.android.gms:play-services-auth:$playServicesAuthVersion"

    // Timber
    val timber = "com.jakewharton.timber:timber:$timberVersion"

    // Retrofit
    val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    val retrofitRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
    val retrofitGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    val retrofitScalar = "com.squareup.retrofit2:converter-scalars:$retrofitScalarVersion"

    // OkHttp
    val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
    val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:$okhttpLoggingVersion"

    // RxJava
    val rxjava2 = "io.reactivex.rxjava2:rxjava:$rxJava2Version"

    // RxKotlin
    val rxkotlin = "io.reactivex.rxjava2:rxkotlin:$rxKotlinVersion"

    // RxAndroid
    val rxandroid = "io.reactivex.rxjava2:rxandroid:$rxAndroidVersion"

    // RxBinding
    val rxbindingAndroidX = "com.jakewharton.rxbinding3:rxbinding:$rxBindingXVersion"
    val rxbindingAndroidXCore = "com.jakewharton.rxbinding3:rxbinding-core:$rxBindingXVersion"
    val rxbindingAndroidXAppCompat = "com.jakewharton.rxbinding3:rxbinding-appcompat:$rxBindingXVersion"

    // Dagger
    val dagger = "com.google.dagger:dagger:$daggerVersion"
    val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
    val daggerAndroid = "com.google.dagger:dagger-android:$daggerVersion"
    val daggerSupport = "com.google.dagger:dagger-android-support:$daggerVersion"
    val daggerProcessor = "com.google.dagger:dagger-android-processor:$daggerVersion"

    // MultiDex
    val multiDex = "androidx.multidex:multidex:$multiDexVersion"

    // Glide
    val glide = "com.github.bumptech.glide:glide:$glideVersion"
    val glideCompiler = "com.github.bumptech.glide:compiler:$glideVersion"

    // GlassFish
    val glassFishAnnotation = "org.glassfish:javax.annotation:$glassFishVersion"

    // Epoxy
    val epoxy = "com.airbnb.android:epoxy:$epoxyVersion"
    val epoxyProcessor = "com.airbnb.android:epoxy-processor:$epoxyVersion"

    // CircleImageView
    val circleImageView = "de.hdodenhof:circleimageview:$circleImageViewVersion"

    // SnapHelper
    val snapHelperPlugin = "com.github.rubensousa:gravitysnaphelper:$snapHelperVersion"

    // RxPermissions
    val runtimePermission = "com.github.tbruyelle:rxpermissions:$runtimePermissionVersion"

    // ThreeTenABP
    val threeTenABP = "com.jakewharton.threetenabp:threetenabp:$threeTenABPVersion"

    // Stetho
    val stetho = "com.facebook.stetho:stetho:$stethoVersion"

    // Crashlytics
    val crashlytics = "com.crashlytics.sdk.android:crashlytics:$crashlyticsVersion"

    // Gson
    val gson = "com.google.code.gson:gson:$gsonVersion"

    // PreferenceManager
    val preference = "androidx.preference:preference-ktx:$preferenceManagerVersion"

    // Facebook SDK
    val facebookSdk = "com.facebook.android:facebook-login:$facebookSdkVersion"
}


object TestLibs {
    val junit = "junit:junit:$junitVersion"
    val testRunner = "androidx.test:runner:$testRunnerVersion"
    val espresso = "androidx.test.espresso:espresso-core:$espressoVersion"
    val mockito = "org.mockito:mockito-inline:$mockitoVersion"
    val archCoreTesting = "androidx.arch.core:core-testing:$archCompVersion"
}
