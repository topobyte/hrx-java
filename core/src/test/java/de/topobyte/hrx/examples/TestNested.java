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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.topobyte.hrx.HrxException;
import de.topobyte.hrx.HrxFile;
import de.topobyte.hrx.HrxFiles;
import de.topobyte.hrx.HrxReader;
import de.topobyte.hrx.TestUtil;

public class TestNested
{

	private List<HrxFile> expected = new ArrayList<>();
	{
		expected.add(HrxFiles.file("file1.hrx", TestUtil.lines(
				"<=====> nested-file1.hrx", //
				"This is a HRX file nested within a HRX file.", //
				"", //
				"<=====> nested-file2.hrx", //
				"You can tell it's not part of the outer file because the boundaries are longer.", //
				"" //
		)));
		expected.add(HrxFiles.file("file2.hrx", TestUtil.lines(
				"<=> nested-file1.hrx", //
				"Inner files can also contain shorter boundaries...", //
				"", //
				"<=> nested-file2.hrx", //
				"...as long as they don't contain the outer file's boundary.", //
				"" //
		)));
	}

	private List<HrxFile> expected1 = new ArrayList<>();
	{
		expected1.add(HrxFiles.file("nested-file1.hrx",
				TestUtil.lines("This is a HRX file nested within a HRX file.", //
						"" //
				)));
		expected1.add(HrxFiles.file("nested-file2.hrx", TestUtil.lines(
				"You can tell it's not part of the outer file because the boundaries are longer.", //
				"" //
		)));
	}

	private List<HrxFile> expected2 = new ArrayList<>();
	{
		expected2.add(HrxFiles.file("nested-file1.hrx",
				TestUtil.lines(
						"Inner files can also contain shorter boundaries...", //
						"" //
				)));
		expected2.add(HrxFiles.file("nested-file2.hrx", TestUtil.lines(
				"...as long as they don't contain the outer file's boundary.", //
				"" //
		)));
	}

	@Test
	public void test() throws IOException, HrxException
	{
		try (Reader reader = TestUtil.asReader("examples/nested.hrx")) {
			HrxReader hrxReader = new HrxReader();
			List<HrxFile> files = hrxReader.read(reader);
			TestUtil.assertEquals(expected, files);

			HrxFile file1 = files.get(0);
			List<HrxFile> nested1 = hrxReader
					.read(new StringReader(file1.getContent()));
			TestUtil.assertEquals(expected1, nested1);

			HrxFile file2 = files.get(1);
			List<HrxFile> nested2 = hrxReader
					.read(new StringReader(file2.getContent()));
			TestUtil.assertEquals(expected2, nested2);
		}
	}

}
