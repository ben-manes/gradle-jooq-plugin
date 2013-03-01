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

import groovy.xml.MarkupBuilder

/**
 * The configuration as an XML DSL matching jOOQ's schema.
 *
 * @author Ben Manes (ben.manes@gmail.com)
 */
public class JooqExtension {
  private def builder
  private def printer
  private def writer
  private def done

  public JooqExtension() {
    writer = new StringWriter()
    printer = new IndentPrinter(writer)
    builder = new MarkupBuilder(printer)
    start()
  }

  private def start() {
    printer.incrementIndent()
    printer.println('<configuration>')
    printer.printIndent()
  }

  private def end() {
    if (!done) {
      done = true
      printer.println('\n</configuration>')
    }
  }

  def methodMissing(String name, args) {
    builder.invokeMethod(name, args)
  }

  def getXml() {
    end()
    writer.toString()
  }
}
