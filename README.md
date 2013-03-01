# Gradle jOOQ Plugin

[jOOQ](http://www.jooq.org) generates a simple Java representation of your database schema.
Every table, view, stored procedure, enum, UDT is a class. This plugin performs code generation
as part of the Gradle build.

## Usage

This plugin is hosted on the Maven Central Repository. You can add it to your build script using
the following configuration:

```groovy
apply plugin: 'jooq'

buildscript {
  repositories {
    mavenCentral()
  }
  
  dependencies {
    classpath 'com.github.ben-manes:gradle-jooq-plugin:0.1'
  }
}
```

## Tasks

### `generateJooq`

In development!
