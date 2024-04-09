// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("com.android.library") version "8.1.0" apply false
    id("com.android.dynamic-feature") version "8.1.0" apply false
}

project.ext.set("apiKey", findProperty("API_KEY") as String?)
project.ext.set("baseUrl", findProperty("BASE_URL") as String?)
project.ext.set("baseUrlImage", findProperty("BASE_URL_IMAGE") as String?)
project.ext.set("hostname", findProperty("HOSTNAME") as String?)
project.ext.set("pin1", findProperty("PIN_1") as String?)
project.ext.set("pin2", findProperty("PIN_2") as String?)