# Gradle jOOQ Plugin

[jOOQ](http://www.jooq.org) generates a simple Java representation of your database schema.
Every table, view, stored procedure, enum, UDT is a class. This plugin performs code generation
as part of the Gradle build.

## Usage

This plugin is hosted on the Maven Central Repository. The configuration is defined as an XML DSL 
based on jOOQ's [codegen schema](http://www.jooq.org/xsd/jooq-codegen-3.0.0.xsd). All actions are
logged at the 'info' level.

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

jooq {
  jdbc {
    url 'jdbc:mysql://localhost:3306'
    driver 'com.mysql.jdbc.Driver'
    user 'root'
  }
  generator {
    database {
      name 'org.jooq.util.mysql.MySQLDatabase'
      inputSchema 'example'
      includes '.*'
    }
  }
}
```

## Tasks

### `generateJooq`

Executes the jOOQ [code generator](http://www.jooq.org/doc/3.0/manual/code-generation/).

```groovy
generateJooq {
  // An input dependency on the migration scripts directory can be specified to invalidate the
  // up-to-date check. By default this option is not set.
  migrationDir = "${projectDir}/src/main/resources/db/migration"

  // The output directory for the generated sources; default below
  targetDir = "${buildDir}/generated-sources/jooq"
}
```
