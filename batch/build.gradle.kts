group = rootProject.group
version = rootProject.version

dependencies {
    implementation(project(":domain"))
    implementation(project(":infrastructure"))

    // Spring Batch
    implementation("org.springframework.boot:spring-boot-starter-batch")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
