import org.jetbrains.kotlin.ir.backend.js.compile

group = rootProject.group
version = rootProject.version

val springCloudOpenFeignVersion: String by rootProject
val springCloudHystrixVersion: String by rootProject
val springCloudRibbonVersion: String by rootProject

dependencies {
    implementation(project(":domain"))
    implementation(project(":service"))
    implementation(project(":application"))

    // Spring Cloud
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:$springCloudOpenFeignVersion")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-hystrix:$springCloudHystrixVersion")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-ribbon:$springCloudRibbonVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
