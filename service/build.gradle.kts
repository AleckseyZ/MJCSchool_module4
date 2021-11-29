plugins {
    id("RESTBasics.common-conventions")
}

dependencies {
    implementation("org.springframework.data:spring-data-jpa:${Versions.springDataJpaVer}")
    testImplementation("org.mockito:mockito-core:${Versions.mockitoVer}")
    testImplementation("org.mockito:mockito-junit-jupiter:${Versions.mockitoVer}")
    implementation(project(":dataAccess"))
}