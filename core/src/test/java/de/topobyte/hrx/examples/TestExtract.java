package de.topobyte.hrx.examples;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import de.topobyte.system.utils.SystemPaths;

@RunWith(Parameterized.class)
public class TestExtract
{

	@Parameterized.Parameters
	public static Collection<?> primeNumbers()
	{
		return Arrays.asList(new Object[][] { //
				{ "comment-only.hrx" }, //
				{ "comments.hrx" }, //
				{ "complex-filenames.hrx" }, //
				{ "directory.hrx" }, //
				{ "empty-file.hrx" }, //
				{ "files-in-directories.hrx" }, //
				{ "inline-boundary.hrx" }, //
				{ "nested.hrx" }, //
				{ "no-trailing-newlines.hrx" }, //
				{ "simple.hrx" }, //
				{ "trailing-comment.hrx" }, //
		});
	}

	private String name;

	public TestExtract(String name)
	{
		this.name = name;
	}

	@Test
	public void test()
	{
		Assert.assertTrue(name.endsWith(".hrx"));

		String dirname = name.substring(0, name.length() - 4);
		Path dir = SystemPaths.CWD.getParent().resolve("extracted-examples")
				.resolve(dirname);
		Assert.assertTrue(Files.exists(dir));

	}

}
