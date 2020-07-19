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

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

class Lines
{

	@Getter
	private List<Line> lines = new ArrayList<>();

	public void append(Line line)
	{
		lines.add(line);
	}

	@Override
	public String toString()
	{
		if (lines.isEmpty()) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();

		int n = lines.size();
		for (int i = 0; i < n; i++) {
			Line line = lines.get(i);
			if (line.isEOF() && line.getContent() == null) {
				continue;
			}
			buffer.append(line.getContent());
			buffer.append(ending(line.getEnding()));
		}
		return buffer.toString();
	}

	private String ending(LineTerminator ending)
	{
		switch (ending) {
		case EOF:
			return "";
		default:
		case UNIX:
			return "\n";
		case WINDOWS:
			return "\r\n";
		}
	}

	public void setLastToEof()
	{
		if (lines.isEmpty()) {
			return;
		}
		Line line = lines.get(lines.size() - 1);
		line.setEnding(LineTerminator.EOF);
	}

}
