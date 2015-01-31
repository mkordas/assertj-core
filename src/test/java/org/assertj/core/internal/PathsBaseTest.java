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
package org.assertj.core.internal;

import static org.assertj.core.test.ExpectedException.none;
import static org.assertj.core.test.TestData.someInfo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;

/**
 * Base test class for {@link Path} instances
 *
 * @see Path
 * @see FileSystem
 * @see Files
 */
public abstract class PathsBaseTest {
  
  @Rule
  public ExpectedException thrown = none();
  protected Failures failures;
  protected Paths paths;
  protected NioFilesWrapper nioFilesWrapper;
  protected AssertionInfo info;

  // TODO! uncomment when needed
  // protected Diff diff;
  // protected BinaryDiff binaryDiff;

  @Before
  public void setUp() throws IOException {
	failures = spy(new Failures());
	nioFilesWrapper = mock(NioFilesWrapper.class);
	paths = new Paths(nioFilesWrapper);
	paths.failures = failures;
	info = someInfo();
	// diff = mock(Diff.class);
	// files.diff = diff;
	// binaryDiff = mock(BinaryDiff.class);
	// files.binaryDiff = binaryDiff;
  }
 
}