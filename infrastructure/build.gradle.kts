group = rootProject.group
version = rootProject.version

dependencies {
    implementation(project(":domain"))
    implementation(project(":service"))
    implementation(project(":application"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
