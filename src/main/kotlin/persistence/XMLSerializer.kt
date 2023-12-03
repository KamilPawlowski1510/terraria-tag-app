package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import models.Boss
import models.Weapon
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import kotlin.Throws

/**
 * An implementation of the Serializer interface that uses XML for object serialization and deserialization.
 *
 * @param file The File object representing the file used for reading and writing data.
 */
class XMLSerializer(private val file: File) : Serializer {

    /**
     * Reads an object from the XML file using XStream.
     *
     * @return The deserialized object read from the XML file.
     * @throws Exception If an error occurs during the reading process.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Boss::class.java))
        xStream.allowTypes(arrayOf(Weapon::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Writes an object to the XML file using XStream.
     *
     * @param obj The object to be written to the XML file.
     * @throws Exception If an error occurs during the writing process.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
