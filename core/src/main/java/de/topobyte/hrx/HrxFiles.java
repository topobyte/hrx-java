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

package de.topobyte.hrx;

import java.nio.file.Paths;

public class HrxFiles
{

	public static HrxFile comment(Lines lines)
	{
		HrxFile file = new HrxFile(HrxEntryType.COMMENT);
		file.setContent(lines.toString());
		return file;
	}

	public static HrxFile file(String filename, Lines lines)
	{
		HrxFile file = new HrxFile(HrxEntryType.FILE);
		file.setPath(Paths.get(filename));
		file.setContent(lines.toString());
		return file;
	}

	public static HrxFile directory(String dirname, Lines lines)
	{
		HrxFile file = new HrxFile(HrxEntryType.DIRECTORY);
		file.setPath(Paths.get(dirname));
		file.setContent(lines.toString());
		return file;
	}

}
