import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream

val groupName: String by project
val kotestVersion: String by project

val springdocOpenapiVersion: String by project

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
	jacoco
	id("com.github.kt3k.coveralls")
	id("com.google.cloud.tools.jib")
}

allprojects {
	group = groupName
	version = getGitHash()

	apply {
		plugin("kotlin")
		plugin("kotlin-spring")
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
		plugin("jacoco")
		plugin("com.google.cloud.tools.jib")
	}

	jacoco {
		toolVersion = "0.8.8"
	}

	repositories {
		mavenCentral()
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

subprojects {
	java.sourceCompatibility = JavaVersion.VERSION_11

	dependencies {
		// Kotlin
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

		// Spring
		implementation("org.springframework.boot:spring-boot-starter-actuator")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("org.springframework.boot:spring-boot-starter-web")

		// Swagger
		implementation("org.springdoc:springdoc-openapi-ui:$springdocOpenapiVersion")
		implementation("org.springdoc:springdoc-openapi-kotlin:$springdocOpenapiVersion")

		compileOnly("org.projectlombok:lombok")
		developmentOnly("org.springframework.boot:spring-boot-devtools")
		annotationProcessor("org.projectlombok:lombok")
		testImplementation("org.springframework.boot:spring-boot-starter-test")

		// kotest
		testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
		testImplementation("io.kotest:kotest-property:$kotestVersion")
		testImplementation("io.kotest:kotest-extensions-spring:$kotestVersion")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
		finalizedBy(tasks.withType<JacocoReport>())
	}

	tasks.withType<JacocoReport> {
		dependsOn(tasks.withType<Test>())

		reports {
			html.required.set(true)
			xml.required.set(true)
			csv.required.set(false)
		}

		afterEvaluate {
			classDirectories.setFrom(files(classDirectories.files.map {
				fileTree(it).apply {
					exclude("**/DemoApplication**")
					for (pattern in 'A' .. 'Z') {
						exclude("**/${pattern}**")
					}
				}
			}))
		}

		finalizedBy(tasks.withType<JacocoCoverageVerification>())
	}

	tasks.withType<JacocoCoverageVerification> {
		dependsOn(tasks.withType<JacocoReport>())
		val excludeJacocoClassNamePatterns = mutableListOf<String>().apply {
			for (pattern in 'A'..'Z') {
				add("*.Q${pattern}*")
			}
		}

		violationRules {
			rule {
				element = "CLASS"

				limit {
					counter = "BRANCH"
					value = "COVEREDRATIO"
					minimum = 0.80.toBigDecimal()
				}

				limit {
					counter = "LINE"
					value = "COVEREDRATIO"
					minimum = 0.80.toBigDecimal()
				}

				excludes = listOf<String>("**.DemoApplication**") + excludeJacocoClassNamePatterns
			}
		}
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "11"
		}
	}
}

fun getGitHash(): String {
	val stdout = ByteArrayOutputStream()
	exec {
		commandLine = listOf("git", "rev-parse", "--short", "HEAD")
		standardOutput = stdout
	}
	return stdout.toString().trim()
}

tasks.register<JacocoReport>("jacocoAllReport") {
	val currentTask = this

	val allSourceSrcDirs = mutableListOf<Set<File>>()
	val outputDirectories = mutableListOf<SourceSetOutput>()

	subprojects {
		val subproject = this
		subproject.plugins.withType<JacocoPlugin>().configureEach {
			subproject.tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }.forEach {
				currentTask.dependsOn(it)
			}
		}

		allSourceSrcDirs.add(subproject.sourceSets.main.get().allSource.srcDirs)
		outputDirectories.add(subproject.sourceSets.main.get().output)

		afterEvaluate {
			classDirectories.setFrom(files(classDirectories.files.map {
				fileTree(it).apply {
					exclude("**/DemoApplication**")
					for (pattern in 'A' .. 'Z') {
						exclude("**/${pattern}**")
					}
				}
			}))
		}
	}

	additionalSourceDirs.setFrom(allSourceSrcDirs)
	sourceDirectories.setFrom(allSourceSrcDirs)
	classDirectories.setFrom(outputDirectories)
	executionData.setFrom(fileTree(projectDir).include("**/jacoco/*.exec"))

	reports {
		xml.required.set(true)
		html.required.set(true)
		csv.required.set(false)
	}
}

coveralls {
	jacocoReportPath = "${projectDir}/build/reports/jacoco/jacocoAllReport/jacocoAllReport.xml"
	sourceDirs = subprojects.map { it.sourceSets.main.get().allSource.srcDirs.toList() }
		.toList().flatten().map { relativePath(it) }
}

tasks.withType<org.kt3k.gradle.plugin.coveralls.CoverallsTask> {
	dependsOn(tasks["jacocoAllReport"])
}
