plugins {
    id("java")
}

group = "ru.otus"

dependencies {


    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core")

}



tasks.test {
    useJUnitPlatform()
}