package util.zip

import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.util.zip.{ZipEntry, ZipOutputStream}

class ZipFileCreator {

  def createZipBytes(fileEntries: Map[String, String]): Array[Byte] = {
    val baos = new ByteArrayOutputStream()
    val zos = new ZipOutputStream(baos)

    try {
      fileEntries.foreach { case (fileName, content) =>
        val entry = new ZipEntry(fileName)
        zos.putNextEntry(entry)

        val bytes = content.getBytes(StandardCharsets.UTF_8)
        zos.write(bytes, 0, bytes.length)

        zos.closeEntry()
      }
    } finally {
      zos.close()
    }

    baos.toByteArray
  }

}
