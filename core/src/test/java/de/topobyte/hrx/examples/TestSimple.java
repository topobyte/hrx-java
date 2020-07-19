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
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.topobyte.hrx.HrxException;
import de.topobyte.hrx.HrxFile;
import de.topobyte.hrx.HrxFiles;
import de.topobyte.hrx.HrxReader;
import de.topobyte.hrx.TestUtil;

public class TestSimple
{

	private List<HrxFile> expected = new ArrayList<>();
	{
		expected.add(HrxFiles.file("input.scss", TestUtil.lines( //
				"ul {", //
				"  margin-left: 1em;", //
				"  li {", //
				"    list-style-type: none;", //
				"  }", //
				"}", //
				"" //
		)));
		expected.add(HrxFiles.file("output.css", TestUtil.lines( //
				"ul {", //
				"  margin-left: 1em;", //
				"}", //
				"ul li {", //
				"  list-style-type: none;", //
				"}", //
				"" //
		)));
	}

	@Test
	public void test() throws IOException, HrxException
	{
		try (Reader reader = TestUtil.asReader("examples/simple.hrx")) {
			HrxReader hrxReader = new HrxReader();
			List<HrxFile> files = hrxReader.read(reader);
			TestUtil.assertEquals(expected, files);
		}
	}

}
