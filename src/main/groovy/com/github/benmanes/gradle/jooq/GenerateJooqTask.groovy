/*
 * Copyright 2013 Ben Manes. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.benmanes.gradle.jooq

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.jooq.util.GenerationTool
import org.jooq.util.jaxb.Generator
import org.jooq.util.jaxb.Target

/**
 * A task that performs jOOQ code generation.
 *
 * @author Ben Manes (ben.manes@gmail.com)
 */
class GenerateJooqTask extends DefaultTask {

  /** The database schema migration scripts */
  @Input
  String migrationDir = '${project.buildDir}/../src/main/resources/db/migration'

  /** The output directory for the generated sources */
  @Input
  String targetDir = "${project.buildDir}/generated-sources/jooq"

  GenerateJooqTask() {
    inputs.dir migrationDir

    outputs.dir targetDir
    project.sourceSets.main.java.srcDirs += [ targetDir ]

    description = 'Generates jOOQ Java classes.'
    group = 'Build'
  }

  @TaskAction
  def generateJooq() {
    def xml = project.jooq.xml
    logger.info 'Using this configuration:\n{}', xml
    def configuration = GenerationTool.load(new ByteArrayInputStream(xml.getBytes('utf8')))

    updateDefaults(configuration)
    GenerationTool.main(configuration);
  }

  def updateDefaults(def configuration) {
    configuration.generator = configuration.generator ?: new Generator()
    configuration.generator.target = configuration.generator.target ?: new Target()
    configuration.generator.target.directory = targetDir
  }
}
