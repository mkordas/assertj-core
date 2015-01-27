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

import static org.assertj.core.error.ShouldBeSymbolicLink.shouldBeSymbolicLink;
import static org.assertj.core.error.ShouldExistNoFollow.shouldExistNoFollow;
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

public class Paths_assertIsSymbolicLink_Test extends PathsBaseTest {

  @ClassRule
  public static FileSystemResource resource = new FileSystemResource();

  private static Path existingFile;
  private static Path symlinkToExistingFile;
  private static Path nonExistingPath;
  private static Path symlinkToNonExistingPath;
  private static Path existingDirectory;
  private static Path symlinkToExistingDirectory;

  @BeforeClass
  public static void initPaths() throws IOException {

	final FileSystem fs = resource.getFileSystem();

	existingFile = fs.getPath("/existingFile");
	symlinkToExistingFile = fs.getPath("/symlinkToExistingFile");
	Files.createFile(existingFile);
	Files.createSymbolicLink(symlinkToExistingFile, existingFile);

	nonExistingPath = fs.getPath("/nonExistingPath");
	symlinkToNonExistingPath = fs.getPath("/symlinkToNonExistingPath");
	Files.createSymbolicLink(symlinkToNonExistingPath, nonExistingPath);

	existingDirectory = fs.getPath("/existingDirectory");
	symlinkToExistingDirectory = fs.getPath("/symlinkToExistingDirectory");
	Files.createDirectory(existingDirectory);
	Files.createSymbolicLink(symlinkToExistingDirectory, existingDirectory);
  }

  @Test
  public void should_fail_if_actual_is_null() {
	thrown.expectAssertionError(actualIsNull());
	paths.assertIsSymbolicLink(info, null);
  }

  @Test
  public void should_fail_with_should_exist_error_if_actual_does_not_exist() {
	try {
	  paths.assertIsSymbolicLink(info, nonExistingPath);
	  wasExpectingAssertionError();
	} catch (AssertionError e) {
	  verify(failures).failure(info, shouldExistNoFollow(nonExistingPath));
	}
  }

  @Test
  public void should_fail_if_target_is_an_existing_directory() {
	try {
	  paths.assertIsSymbolicLink(info, existingDirectory);
	  wasExpectingAssertionError();
	} catch (AssertionError e) {
	  verify(failures).failure(info, shouldBeSymbolicLink(existingDirectory));
	}
  }

  @Test
  public void should_fail_if_actual_is_an_existing_regular_file() {
	try {
	  paths.assertIsSymbolicLink(info, existingFile);
	  wasExpectingAssertionError();
	} catch (AssertionError e) {
	  verify(failures).failure(info, shouldBeSymbolicLink(existingFile));
	}
  }

  @Test
  public void should_succeed_if_target_is_symlink_to_an_existing_directory() {
	paths.assertIsSymbolicLink(info, symlinkToExistingDirectory);
  }

  @Test
  public void should_succeed_if_actual_is_symlink_to_an_existing_regular_file() {
	paths.assertIsSymbolicLink(info, symlinkToExistingFile);
  }

  @Test
  public void should_succeed_if_actual_is_a_symlink_to_a_non_existing_path() {
	paths.assertIsSymbolicLink(info, symlinkToNonExistingPath);
  }
}
