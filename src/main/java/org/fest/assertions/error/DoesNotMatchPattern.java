/*
 * Created on Dec 22, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010 the original author or authors.
 */
package org.fest.assertions.error;

/**
 * Creates an error message indicating that an assertion that verifies that a {@code String} matches a pattern failed.
 *
 * @author Alex Ruiz
 */
public class DoesNotMatchPattern extends BasicErrorMessage {

  /**
   * Creates a new <code>{@link DoesNotStartWith}</code>.
   * @param actual the actual value in the failed assertion.
   * @param pattern the pattern to match.
   * @return the created {@code ErrorMessage}.
   */
  public static ErrorMessage doesNotMatch(String actual, String pattern) {
    return new DoesNotMatchPattern(actual, pattern);
  }

  private DoesNotMatchPattern(String actual, String pattern) {
    super("%s%s does not match the pattern %s", actual, pattern);
  }
}