package airwallex.demo.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import java.util.*

/**
 * @author bin.guo
 * On 2018/10/31
 */
@Document(
    indexName = "airwallex_user_x", type = "users"
)
data class User(
        @Id var id : UUID?,
        var name : String,
        var email : String,
        var birthday: String,
        var height: Long,
        var weight: Long,
        var age : Int,
        var languages: List<String>
)