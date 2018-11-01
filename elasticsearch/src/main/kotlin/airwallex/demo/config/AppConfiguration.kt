package airwallex.demo.config

import airwallex.demo.utils.Codecs
import org.elasticsearch.client.Client
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.EntityMapper

/**
 * @author bin.guo
 * On 2018/11/1
 */
@Configuration
open class AppConfiguration {


    @Bean
    open fun elasticsearchTemplate(client: Client) = ElasticsearchTemplate(client, CustomMapper())

    class CustomMapper : EntityMapper {
        override fun <T : Any?> mapToObject(source: String?, clazz: Class<T>?) = Codecs.getMapper().readValue(source, clazz)!!

        override fun mapToString(obj: Any?) = Codecs.getMapper().writeValueAsString(obj)!!
    }
}