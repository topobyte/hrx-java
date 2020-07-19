package de.topobyte.hrx.examples;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import de.topobyte.hrx.HrxEntryType;
import de.topobyte.hrx.HrxException;
import de.topobyte.hrx.HrxFile;
import de.topobyte.hrx.HrxReader;
import de.topobyte.system.utils.SystemPaths;
import de.topobyte.util.TestUtil;

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

	private String filename;

	public TestExtract(String filename)
	{
		this.filename = filename;
	}

	@Test
	public void test() throws IOException, HrxException
	{
		System.out.println("ARCHIVE: " + filename);
		Assert.assertTrue(filename.endsWith(".hrx"));

		String dirname = filename.substring(0, filename.length() - 4);
		Path dir = SystemPaths.CWD.getParent().resolve("extracted-examples")
				.resolve(dirname);
		Assert.assertTrue(Files.exists(dir));

		try (Reader reader = TestUtil.asReader("examples/" + filename)) {
			HrxReader hrxReader = new HrxReader();
			List<HrxFile> files = hrxReader.read(reader);
			checkExtractedContent(files, dir);
		}
	}

	private void checkExtractedContent(List<HrxFile> files, Path dir)
			throws IOException
	{
		for (HrxFile file : files) {
			if (file.getType() == HrxEntryType.FILE) {
				System.out.println("FILE: " + file.getPath());
				Path expectedPath = dir.resolve(file.getPath());
				System.out.println("EXPECTED FILE: " + expectedPath);
				Assert.assertTrue(Files.exists(expectedPath));

				byte[] bytes = Files.readAllBytes(expectedPath);
				String expected = new String(bytes);

				Assert.assertEquals(expected, file.getContent());
			}
		}
	}

}
