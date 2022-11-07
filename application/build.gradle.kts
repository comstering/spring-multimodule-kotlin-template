group = rootProject.group
version = rootProject.version

dependencies {
    implementation(project(":service"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
