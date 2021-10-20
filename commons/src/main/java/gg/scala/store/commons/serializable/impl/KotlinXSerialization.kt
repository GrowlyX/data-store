package gg.scala.store.commons.serializable.impl

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * @author GrowlyX
 * @since 10/20/2021
 */
object KotlinXSerialization
{
    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> serialize(t: T): String
    {
        return Json.encodeToString(t)
    }

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T> deserialize(json: String): T
    {
        return Json.decodeFromString(json)
    }
}
