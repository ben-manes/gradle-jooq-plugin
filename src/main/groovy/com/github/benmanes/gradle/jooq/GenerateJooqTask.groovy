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
  def configuration

  GenerateJooqTask() {
    description = 'Generates jOOQ Java classes.'
    group = 'Build'

    project.gradle.projectsEvaluated {
      parseConfiguration()
      outputs.dir configuration.generator.target.directory
      project.sourceSets.main.java.srcDirs += [ configuration.generator.target.directory ]
    }
  }

  @TaskAction
  def generateJooq() {
    logger.info 'Using this configuration:\n{}', project.jooq.xml
    GenerationTool.main(configuration);
  }

  def parseConfiguration() {
    configuration = GenerationTool.load(new ByteArrayInputStream(project.jooq.xml.getBytes('utf8')))
    if (useDefaultTargetDirectory()) {
      gradleTargertDir()
    }
  }

  def useDefaultTargetDirectory() {
    def parsed = new XmlParser().parseText(project.jooq.xml)
    parsed.generator.target.directory.text().isEmpty()
  }

  def gradleTargertDir() {
    configuration.generator = configuration.generator ?: new Generator()
    configuration.generator.target = configuration.generator.target ?: new Target()
    configuration.generator.target.directory = "${project.buildDir}/generated-sources/jooq"
  }
}
