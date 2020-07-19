// Copyright 2020 Sebastian Kuerten
//
// This file is part of hrx-java.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package de.topobyte.hrx.examples;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import de.topobyte.hrx.HrxEntryType;
import de.topobyte.hrx.HrxException;
import de.topobyte.hrx.HrxFile;
import de.topobyte.hrx.HrxReader;
import de.topobyte.hrx.TestUtil;
import de.topobyte.system.utils.SystemPaths;

@RunWith(Parameterized.class)
public class TestExtract
{

	@Parameterized.Parameters
	public static Collection<?> primeNumbers()
	{
		return Arrays.asList(new Object[][] { //
				{ "comment-only.hrx" }, //
				{ "comments.hrx" }, //
				{ "complex-filenames.hrx" }, //
				{ "directory.hrx" }, //
				{ "empty-file.hrx" }, //
				{ "files-in-directories.hrx" }, //
				{ "inline-boundary.hrx" }, //
				{ "nested.hrx" }, //
				{ "no-trailing-newlines.hrx" }, //
				{ "simple.hrx" }, //
				{ "trailing-comment.hrx" }, //
		});
	}

	private String filename;

	public TestExtract(String filename)
	{
		this.filename = filename;
	}

	@Test
	public void test() throws IOException, HrxException
	{
		System.out.println("ARCHIVE: " + filename);
		Assert.assertTrue(filename.endsWith(".hrx"));

		String dirname = filename.substring(0, filename.length() - 4);
		Path dir = SystemPaths.CWD.getParent().resolve("extracted-examples")
				.resolve(dirname);
		Assert.assertTrue(Files.exists(dir));

		try (Reader reader = TestUtil.asReader("examples/" + filename)) {
			HrxReader hrxReader = new HrxReader();
			List<HrxFile> files = hrxReader.read(reader);
			checkExtractedContent(files, dir);
			// TODO: check the other way around that all files in the expected
			// directory have been extracted
			// TODO: check for discovered directories both ways
		}
	}

	private void checkExtractedContent(List<HrxFile> files, Path dir)
			throws IOException
	{
		for (HrxFile file : files) {
			if (file.getType() == HrxEntryType.FILE) {
				System.out.println("FILE: " + file.getPath());
				Path expectedPath = dir.resolve(file.getPath());
				System.out.println("EXPECTED FILE: " + expectedPath);
				Assert.assertTrue(Files.exists(expectedPath));

				byte[] bytes = Files.readAllBytes(expectedPath);
				String expected = new String(bytes);

				Assert.assertEquals(expected, file.getContent());
			}
		}
	}

}
