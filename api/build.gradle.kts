plugins {
    java
    war
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":service"))
    implementation(project(":dataAccess"))
    implementation("org.hibernate:hibernate-core:${Versions.hibernateVer}")
    implementation ("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-tomcat")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt:${Versions.jwtVer}")
    implementation("javax.validation:validation-api:${Versions.javaxValidatorVer}")
    implementation("org.hibernate:hibernate-validator:${Versions.hibernateValidatorVer}")
    compileOnly("org.projectlombok:lombok:${Versions.lombokVer}")
	annotationProcessor("org.projectlombok:lombok:${Versions.lombokVer}")
}