plugins {
    java
    kotlin("jvm") version "2.0.0"
}

group = "com.acme.claims"
version = "0.1.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

sourceSets {
    main {
        java.srcDirs("src/main/java")
        kotlin.srcDirs("src/main/kotlin")
    }
    // Example agents are demos, compiled separately from the operational product.
    create("examples") {
        java.srcDirs("examples")
        compileClasspath += sourceSets["main"].output
    }
}
