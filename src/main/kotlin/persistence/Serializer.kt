package persistence

/**
 * A generic interface for serializing and deserializing objects to and from a persistent storage medium.
 */
interface Serializer {
    /**
     * Writes the provided object to a persistent storage medium.
     *
     * @param obj The object to be written.
     * @throws Exception If an error occurs during the writing process.
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * Reads an object from a persistent storage medium.
     *
     * @return The deserialized object, or null if there is no data to read.
     * @throws Exception If an error occurs during the reading process.
     */
    @Throws(Exception::class)
    fun read(): Any?
}
