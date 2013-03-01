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

import javax.xml.bind.JAXB

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jooq.util.GenerationTool
import org.jooq.util.jaxb.Configuration

/**
 * A task that performs jOOQ code generation.
 *
 * @author Ben Manes (ben.manes@gmail.com)
 */
class DependencyUpdatesTask extends DefaultTask {

  DependencyUpdatesTask() {
    description = 'Generates jOOQ Java classes.'
    group = 'Build'
  }

  @TaskAction
  def generateJooq() {
    if (logger.isInfoEnabled()) {
      logConfiguration()
    }
    GenerationTool.main(project.jooq);
  }

  private def logConfiguration() {
    def configuration = new Configuration().with {
      generator = project.jooq.generator
      jdbc = project.jooq.jdbc
      it
    }
    StringWriter writer = new StringWriter();
    JAXB.marshal(configuration, writer);
    logger.info("Using this configuration:\n" + writer);
  }
}
