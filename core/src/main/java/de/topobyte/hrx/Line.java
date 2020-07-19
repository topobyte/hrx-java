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

import lombok.Getter;
import lombok.Setter;

class Line
{

	@Getter
	@Setter
	private String content;
	@Getter
	@Setter
	private LineTerminator ending;

	public Line(String content, LineTerminator ending)
	{
		this.content = content;
		this.ending = ending;
	}

	public boolean isEOF()
	{
		return ending == LineTerminator.EOF;
	}

	@Override
	public String toString()
	{
		return content;
	}

}
