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

import java.io.IOException;
import java.io.Reader;

class LineReader
{

	private Reader br;

	public LineReader(Reader br)
	{
		this.br = br;
	}

	public Line readLine() throws IOException
	{
		StringBuilder buffer = new StringBuilder();
		LineTerminator terminator = LineTerminator.EOF;
		while (true) {
			int r = br.read();
			if (r == -1) {
				terminator = LineTerminator.EOF;
				break;
			}
			char c = (char) r;
			if (c == '\n') {
				terminator = LineTerminator.UNIX;
				int len = buffer.length();
				if (len > 0) {
					if (buffer.charAt(len - 1) == '\r') {
						terminator = LineTerminator.WINDOWS;
					}
				}
				break;
			}
			buffer.append(c);
		}
		if (terminator == LineTerminator.EOF) {
			if (buffer.length() == 0) {
				return new Line(null, terminator);
			}
		}
		return new Line(buffer.toString(), terminator);
	}

}
