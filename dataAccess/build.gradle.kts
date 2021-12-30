plugins {
    id("RESTSecurity.common-conventions")
     jacoco
}

dependencies {
    implementation("org.springframework:spring-jdbc:${Versions.springVer}")
    implementation("org.postgresql:postgresql:${Versions.postgreVer}")
    implementation("org.springframework:spring-orm:${Versions.springVer}")
    implementation("org.springframework:spring-aspects:${Versions.springVer}")
    implementation("org.springframework.data:spring-data-jpa:${Versions.springDataJpaVer}")
    testImplementation("org.springframework:spring-test:${Versions.springVer}")
    testImplementation("io.zonky.test:embedded-postgres:${Versions.zonkyPostgres}")
    compileOnly("org.projectlombok:lombok:${Versions.lombokVer}")
	annotationProcessor("org.projectlombok:lombok:${Versions.lombokVer}")
	testCompileOnly("org.projectlombok:lombok:${Versions.lombokVer}")
	testAnnotationProcessor("org.projectlombok:lombok:${Versions.lombokVer}")
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