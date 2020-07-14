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

package de.topobyte.hrx.executables;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import de.topobyte.hrx.HrxException;
import de.topobyte.hrx.task.HrxExtract;

public class RunHrxExtract
{

	public static void main(String[] args)
	{
		if (args.length != 2) {
			System.out.println(
					"usage: hrx-extract <input hrx archive> <output directory>");
			System.exit(1);
		}

		Path input = Paths.get(args[0]);
		Path output = Paths.get(args[1]);

		HrxExtract task = new HrxExtract(input, output);
		try {
			task.execute();
		} catch (IOException | HrxException e) {
			System.out.println(
					"Error while extracting archive: " + e.getMessage());
			System.exit(1);
		}
	}

}
