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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class HrxReader
{

	private int boundaryLen;
	private String boundary;

	public List<HrxFile> read(Reader reader) throws IOException, HrxException
	{
		BufferedReader br = new BufferedReader(reader);
		return read(new LineReader(br));
	}

	public List<HrxFile> read(BufferedReader reader)
			throws IOException, HrxException
	{
		return read(new LineReader(reader));
	}

	List<HrxFile> read(LineReader br) throws IOException, HrxException
	{
		List<HrxFile> results = new ArrayList<>();
		Line line = br.readLine();
		boundaryLen = readFirstBoundary(line);
		boundary = boundary();

		LineSupplier supplier = new LineSupplier(br);
		supplier.push(line);

		while (true) {
			HrxFile file = readEntry(supplier);
			if (file == null) {
				break;
			}
			results.add(file);
		}

		return results;
	}

	private String boundary()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append('<');
		for (int i = 0; i < boundaryLen; i++) {
			buffer.append("=");
		}
		buffer.append('>');
		return buffer.toString();
	}

	private HrxFile readEntry(LineSupplier supplier)
			throws IOException, HrxException
	{
		Line line = supplier.next();
		if (line.isEOF()) {
			return null;
		}
		HrxEntryType type = readType(line);
		if (type == HrxEntryType.COMMENT) {
			Lines lines = readLines(supplier);
			return HrxFiles.comment(lines);
		}
		if (type == HrxEntryType.FILE) {
			String filename = readFilename(line);
			Lines lines = readLines(supplier);
			return HrxFiles.file(filename, lines);
		}
		if (type == HrxEntryType.DIRECTORY) {
			String dirname = readDirname(line);
			Lines lines = readLines(supplier);
			return HrxFiles.directory(dirname, lines);
		}
		throw new HrxException("invalid entry type");
	}

	private Lines readLines(LineSupplier supplier) throws IOException
	{
		boolean foundNext = false;
		Lines lines = new Lines();
		while (true) {
			Line line = supplier.next();
			if (line.isEOF()) {
				lines.append(line);
				break;
			}
			if (line.getContent().startsWith(boundary)) {
				supplier.push(line);
				foundNext = true;
				break;
			}
			lines.append(line);
		}
		if (foundNext) {
			// if there is another entry in the archive, consume the last
			// newline character
			lines.setLastToEof();
		}
		return lines;
	}

	private int readFirstBoundary(Line l) throws HrxException
	{
		String line = l.getContent();
		int len = line.length();
		if (len < 3) {
			throw new HrxException("boundary too short");
		}

		char c;
		int p = 0;
		int n = 0;

		// expect a '<' first
		c = line.charAt(p++);
		if (c != '<') {
			throw new HrxException("expected '<'");
		}

		// expect at least one '='
		c = line.charAt(p++);
		if (c != '=') {
			throw new HrxException("expected '<'");
		}
		n += 1;

		// skip through '=' until we hit '>'
		while (p < len) {
			c = line.charAt(p++);
			if (c == '=') {
				n += 1;
			} else if (c == '>') {
				return n;
			}
		}

		return 0;
	}

	private HrxEntryType readType(Line l) throws HrxException
	{
		String line = l.getContent();
		int len = line.length();
		if (len == boundaryLen + 2) {
			return HrxEntryType.COMMENT;
		}

		char c;
		int p = boundaryLen + 2;

		c = line.charAt(p++);
		if (c != ' ') {
			throw new HrxException("expected ' '");
		}

		int remaining = len - p;

		char last = line.charAt(line.length() - 1);
		if (last == '/') {
			if (remaining > 1) {
				return HrxEntryType.DIRECTORY;
			}
			throw new HrxException("empty directory name is invalid");
		} else {
			if (remaining > 0) {
				return HrxEntryType.FILE;
			}
			throw new HrxException("empty directory name is invalid");
		}
	}

	private String readFilename(Line line)
	{
		return line.getContent().substring(boundaryLen + 3);
	}

	private String readDirname(Line line)
	{
		return line.getContent().substring(boundaryLen + 3);
	}

}
