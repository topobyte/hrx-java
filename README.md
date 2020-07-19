# About

A Java implementation of the Human Readable Arhive (hrx) format as specified
in [google/hrx](https://github.com/google/hrx).

This project consists of a library and a command line interface (CLI).

## Library

We provide access to the artifacts via our own Maven repository:

<https://mvn.topobyte.de>

The package is available at these coordinates:

<pre>
<a href="https://mvn.topobyte.de/de/topobyte/hrx/0.1.0/">de.topobyte:hrx:0.1.0</a>
</pre>

## Command Line Interface (CLI)

### Installation

To install the CLI tools, run this:

    ./install.sh

This command will build the tools and install them into your `~/bin`
directory.

### Executables

There is currently only one executable, for extracting .hrx files into a
directory:

    hrx-extract <input hrx archive> <output directory>

# Notes

* We currently do not check for incorrectness of archive content, i.e.
    * mulitple consequtive comments
    * duplicate filenames
    * invalid filenames
* We don't have library classes or tools for packaging hrx files from a
  directory tree

# License

This library is released under the terms of the Apache License.

See [APACHE.md](APACHE.md) for details.
