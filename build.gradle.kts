plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.50"
    id("org.jetbrains.kotlin.kapt") version "1.3.50"
    id("com.github.johnrengelman.shadow") version "5.0.0"
    id("com.github.ksoichiro.console.reporter") version "0.6.2"
    id("io.gitlab.arturbosch.detekt") version "1.0.1"
    id("com.google.cloud.tools.jib") version "1.7.0"
    application
    jacoco
}

version = "0.1"
group = "micronaut.service.template"

repositories {
    jcenter()
    mavenCentral()
}

// this is the ktlint configuration
// not to be mistaken with the ktlint task
val ktlint = configurations.create("ktlint")

dependencies {

    val kotlinVersion = "1.3.50"
    val micronautVersion = "1.2.5"
    val jacksonVersion = "2.9.9"
    val spekVersion = "2.0.8"

    kapt(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")
    kapt("io.micronaut.configuration:micronaut-openapi")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:$kotlinVersion"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-management:${micronautVersion}")

    implementation("io.micronaut.configuration:micronaut-micrometer-registry-prometheus:1.1.0")

    implementation("io.swagger.core.v3:swagger-annotations:1.5.23")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    runtimeOnly("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jacksonVersion")
    runtimeOnly("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    runtimeOnly("ch.qos.logback:logback-classic:1.2.3")

    kaptTest(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kaptTest("io.micronaut:micronaut-inject-java")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.2")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testImplementation("org.assertj:assertj-core:3.12.2")
    testImplementation("org.mockito:mockito-core:2.28.2")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")

    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    ktlint("com.pinterest:ktlint:0.32.0")
}

application {
    mainClassName = "micronaut.service.template.App"
}

tasks {

    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
            javaParameters = true
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
            javaParameters = true
        }
    }

    jacocoTestReport {
        reports {
            xml.isEnabled = false
            csv.isEnabled = false
            html.isEnabled = true
            html.destination = file("$buildDir/reports/coverage")
        }
    }

    register("ktlint", JavaExec::class.java) {
        group = "verification"
        description = "Check Kotlin code style."
        classpath = ktlint
        main = "com.pinterest.ktlint.Main"
        args("src/**/*.kt")
    }

    register("ktlintFormat", JavaExec::class.java) {
        group = "formatting"
        description = "Fix Kotlin code style deviations."
        classpath = ktlint
        main = "com.pinterest.ktlint.Main"
        args("-F", "src/**/*.kt")
    }

    check {
        dependsOn(named("ktlint"))
        dependsOn(jacocoTestReport)
    }

    jib {
        from {
            image = "gcr.io/distroless/java:11"
        }
    }

    test {
        useJUnitPlatform()
    }

    shadowJar {
        mergeServiceFiles()
    }
}
