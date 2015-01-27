/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.core.internal.paths;

import static org.assertj.core.error.ShouldNotExist.shouldNotExist;
import static org.assertj.core.test.TestFailures.wasExpectingAssertionError;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import org.assertj.core.internal.PathsBaseTest;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

public class Paths_assertNotExists_Test extends PathsBaseTest {

  @ClassRule
  public static FileSystemResource resource = new FileSystemResource();

  private static Path existingFile;
  private static Path symlinkToExistingFile;
  private static Path nonExistingPath;
  private static Path existingDirectory;

  @BeforeClass
  public static void initPaths() throws IOException {

	final FileSystem fs = resource.getFileSystem();

	existingFile = fs.getPath("/existing");
	Files.createFile(existingFile);
	nonExistingPath = fs.getPath("/nonExisting");

	symlinkToExistingFile = fs.getPath("/symlinkToExisting");
	Files.createSymbolicLink(symlinkToExistingFile, nonExistingPath);

	existingDirectory = fs.getPath("/existingDirectory");
	Files.createDirectory(existingDirectory);
  }

  @Test
  public void should_fail_if_actual_is_null() {
	thrown.expectAssertionError(actualIsNull());
	paths.assertDoesNotExist(info, null);
  }

  @Test
  public void should_fail_if_actual_is_an_existing_file() {
	try {
	  paths.assertDoesNotExist(info, existingFile);
	  wasExpectingAssertionError();
	} catch (AssertionError e) {
	  verify(failures).failure(info, shouldNotExist(existingFile));
	}
  }

  @Test
  public void should_fail_if_actual_is_an_existing_directory() {
	try {
	  paths.assertDoesNotExist(info, existingDirectory);
	  wasExpectingAssertionError();
	} catch (AssertionError e) {
	  verify(failures).failure(info, shouldNotExist(existingDirectory));
	}
  }
  
  @Test
  public void should_fail_even_if_actual_is_a_symlink_to_a_non_existing_path() {
	try {
	  paths.assertDoesNotExist(info, symlinkToExistingFile);
	  wasExpectingAssertionError();
	} catch (AssertionError e) {
	  verify(failures).failure(info, shouldNotExist(symlinkToExistingFile));
	}
  }

  @Test
  public void should_pass_if_actual_does_not_exist() {
	paths.assertDoesNotExist(info, nonExistingPath);
  }
}
