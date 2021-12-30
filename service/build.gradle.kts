plugins {
    jacoco
    id("RESTSecurity.common-conventions")
    id("org.sonarqube") version "3.3"
}

dependencies {
    implementation("org.springframework.data:spring-data-jpa:${Versions.springDataJpaVer}")
    testImplementation("org.springframework:spring-test:${Versions.springVer}")
    testImplementation("org.mockito:mockito-core:${Versions.mockitoVer}")
    testImplementation("org.mockito:mockito-junit-jupiter:${Versions.mockitoVer}")
    implementation(project(":dataAccess"))
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
    }
}