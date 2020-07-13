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
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.topobyte.hrx.HrxException;
import de.topobyte.hrx.HrxFile;
import de.topobyte.hrx.HrxReader;
import de.topobyte.util.Resources;

public class TestComments
{

	@Test
	public void test() throws IOException, HrxException
	{
		try (Reader reader = Resources.asReader("examples/comments.hrx")) {
			HrxReader hrxReader = new HrxReader();
			List<HrxFile> files = hrxReader.read(reader);
			for (HrxFile file : files) {
				System.out.println(file.getType());
				System.out.println(file.getPath());
				System.out.println(file.getContent());
			}
			Assert.assertEquals(4, files.size());
		}
	}

}
