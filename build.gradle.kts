import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.4.21"
	kotlin("jvm") version kotlinVersion
	kotlin("kapt") version kotlinVersion
	kotlin("plugin.serialization") version kotlinVersion
	application
}

application {
	mainClassName = "App"
}

repositories {
	jcenter()
	mavenCentral()
	maven("https://kotlin.bintray.com/kotlinx")
	maven("https://oss.sonatype.org/content/groups/public")
}

dependencies {
	implementation(kotlin("stdlib"))

	implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
	implementation("com.pi4j:pi4j-core:1.4")
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "1.8"
}