package util.zip

import org.scalatest.freespec.AnyFreeSpec

class ZipFileCreatorTest extends AnyFreeSpec {

  "Test createZipBytes" in {
    val zipFileCreator = new ZipFileCreator()

    val fileEntries = Map(
      "file1.txt" -> "Hello, World!",
      "file2.txt" -> "Hello, Scala!"
    )

    val zipBytes = zipFileCreator.createZipBytes(fileEntries)

    assert(zipBytes.length > 0)
  }

}
