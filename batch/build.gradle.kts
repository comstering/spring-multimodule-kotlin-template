import java.io.ByteArrayOutputStream

group = rootProject.group
version = rootProject.version

val springCloudTaskVersion: String by rootProject

val imageRegistry: String by rootProject
val serviceName: String by rootProject

dependencies {
    implementation(project(":domain"))
    implementation(project(":infrastructure"))

    // Spring Batch
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.cloud:spring-cloud-starter-task:$springCloudTaskVersion")
}

jib {
    to {
        image = "$imageRegistry/$serviceName-batch"
        tags = setOf(getGitCurrentBranch())
    }
}

fun getGitCurrentBranch(): String {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine = listOf("git", "rev-parse", "--abbrev-ref", "HEAD")
        standardOutput = stdout
    }
    return stdout.toString().trim()
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
