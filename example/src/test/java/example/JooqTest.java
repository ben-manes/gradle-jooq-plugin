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

import com.google.inject.Inject;
import example.generated.tables.records.UserRecord;
import org.jooq.impl.Executor;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static example.generated.tables.User.USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Ben Manes (ben.manes@gmail.com)
 */
@Guice(modules = {JooqModule.class, H2Module.class})
public final class JooqTest {
  @Inject Executor db;

  @AfterMethod
  public void afterMethod() {
    db.truncate(USER).execute();
  }

  @Test
  public void insert() {
    UserRecord user = insertUser("John", "Doe");
    assertThat(user.getId(), is(1));
  }

  @Test
  public void select() {
    UserRecord user = insertUser("John", "Doe");
    UserRecord john = db.selectFrom(USER)
        .where(USER.FIRST_NAME.equal("John"))
        .fetchOne();
    assertThat(john, is(equalTo(user)));
  }

  private UserRecord insertUser(String firstName, String lastName) {
    UserRecord user = new UserRecord();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    return db.insertInto(USER).set(user).returning().fetchOne();
  }
}
