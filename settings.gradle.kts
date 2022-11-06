rootProject.name = "demo"

pluginManagement {
    val springBootVersion: String by settings
    val springDependencyManagement: String by settings
    val kotlinVersion: String by settings
    val jibVersion: String by settings

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagement
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        id("com.google.cloud.tools.jib") version jibVersion
    }
}
