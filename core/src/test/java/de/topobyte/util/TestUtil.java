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

package de.topobyte.util;

import java.util.List;

import org.junit.Assert;

import de.topobyte.hrx.HrxFile;
import de.topobyte.hrx.Lines;

public class TestUtil
{

	public static Lines lines(String... lines)
	{
		Lines result = new Lines();
		for (String line : lines) {
			result.append(line);
		}
		return result;
	}

	public static void assertEquals(List<HrxFile> expected, List<HrxFile> files)
	{
		Assert.assertEquals(expected.size(), files.size());
		for (int i = 0; i < expected.size(); i++) {
			HrxFile expectedFile = expected.get(i);
			HrxFile actualFile = files.get(i);
			Assert.assertEquals(expectedFile.getType(), actualFile.getType());
			Assert.assertEquals(expectedFile.getPath(), actualFile.getPath());

			String expectedContent = expectedFile.getContent();
			String actualContent = actualFile.getContent();
			Assert.assertEquals(expectedContent, actualContent);
		}
	}

}
