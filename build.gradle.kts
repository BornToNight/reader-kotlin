val kotlinVer: String by project // 2.1.0
val jacksonKotlinVer: String by project // 2.18.2
val springBootStarterVer: String by project // 3.4.2
val springAdminVer: String by project // 3.2.3
val postgreSQLVer: String by project // 42.7.3
val micrometerPrometheusVer: String by project // 1.12.4
val junitVer: String by project // 1.11.0-M2
val protoCommonVer: String by project // 0.0.1
val grpcVer: String by project // 3.1.0.RELEASE

plugins {
	kotlin("jvm") version "2.1.0"
	kotlin("plugin.spring") version "2.1.0"
    kotlin("plugin.jpa") version "2.1.0"
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.pachan"
version = "1.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
	mavenLocal()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVer")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonKotlinVer")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootStarterVer")
	implementation("org.springframework.boot:spring-boot-starter-web:$springBootStarterVer")
	implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootStarterVer")
	implementation("de.codecentric:spring-boot-admin-starter-client:$springAdminVer")
	implementation("io.micrometer:micrometer-registry-prometheus:$micrometerPrometheusVer")
	implementation("ru.pachan:proto-common:$protoCommonVer")
	implementation("net.devh:grpc-server-spring-boot-starter:$grpcVer")
	runtimeOnly("org.postgresql:postgresql:$postgreSQLVer")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVer")
	testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootStarterVer")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitVer")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
