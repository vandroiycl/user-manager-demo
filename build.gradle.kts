plugins {
	java
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.myapp"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.h2database:h2")
	compileOnly("org.projectlombok:lombok:1.18.32")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// If using JUnit Jupiter
	/*testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// If using JUnit Vintage
	testCompileOnly("junit:junit:4.13.2")
	testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// If using JUnit 4
	testImplementation("junit:junit:4.13.2")

	// If using JUnit 3
	testCompileOnly("junit:junit:3.8.2")
	testRuntimeOnly("junit:junit:4.13.2")*/
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
