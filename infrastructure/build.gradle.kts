group = rootProject.group
version = rootProject.version

val springCloudOpenFeignVersion: String by rootProject

dependencies {
    implementation(project(":domain"))
    implementation(project(":service"))
    implementation(project(":application"))

    // Spring Cloud
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:$springCloudOpenFeignVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
