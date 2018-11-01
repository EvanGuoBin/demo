package airwallex.demo.utils

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

/**
 * @author bin.guo
 * On 2018/11/1
 */
object Codecs {

    private val NO_NULL_FIELD_ORDERED_MAPPER = buildMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)

    private fun buildMapper(): ObjectMapper {
        return ObjectMapper()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
                .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
                //.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES)
                .registerModule(javaTimeModule())
                .registerModule(Jdk8Module())
                .registerModule(KotlinModule())
                .registerModule(ParameterNamesModule())
    }

    private fun javaTimeModule(): Module {
        return JavaTimeModule()
                .addSerializer(LocalDate::class.java, LocalDateSerializer(ISO_LOCAL_DATE))
                .addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(ISO_LOCAL_DATE_TIME))
    }

    fun <T> jsonToObj(json: String, clz: Class<T>): T {
        try {
            return NO_NULL_FIELD_ORDERED_MAPPER.readValue(json, clz)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    fun getMapper() = NO_NULL_FIELD_ORDERED_MAPPER
}