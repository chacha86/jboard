plugins {
    id 'java'
    id 'war'
}

group 'org.example'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.0'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.0'
    // https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api
    compileOnly 'javax.servlet:javax.servlet-api:4.0.1'
    // https://mvnrepository.com/artifact/mysql/mysql-connector-java
    implementation 'mysql:mysql-connector-java:8.0.26'
    // https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
    implementation 'com.googlecode.json-simple:json-simple:1.1.1'
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation 'org.slf4j:slf4j-api:1.7.32'
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    implementation 'ch.qos.logback:logback-classic:1.2.3'
// https://mvnrepository.com/artifact/ch.qos.logback/logback-core
    implementation 'ch.qos.logback:logback-core:1.2.3'
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly 'org.projectlombok:lombok:1.18.22'
}

test {
    useJUnitPlatform()
}