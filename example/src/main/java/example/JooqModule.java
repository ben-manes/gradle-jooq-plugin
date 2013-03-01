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
package example;

import javax.sql.DataSource;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.jooq.SQLDialect;
import org.jooq.impl.Executor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A module that configures JOOQ.
 *
 * @author Ben Manes (ben.manes@gmail.com)
 */
public final class JooqModule extends AbstractModule {
  private final SQLDialect dialect;

  public JooqModule() {
    this.dialect = checkNotNull(SQLDialect.H2);
  }

  public JooqModule(SQLDialect dialect) {
    this.dialect = checkNotNull(dialect);
  }

  @Override
  protected void configure() {}

  @Provides @Singleton
  Executor providesExecutor(DataSource dataSource) {
    return new Executor(dataSource, dialect);
  }
}
