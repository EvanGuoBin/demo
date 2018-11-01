package airwallex.demo.io

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.util.*

/**
 * @author bin.guo
 * On 2018/10/31
 */
class UserRequest : Pageable {

    var id : UUID? = null
    var name : String? = null
    var email : String? = null
    var minAge : Int? = null
    var maxAge: Int? = null
    var height: Long? = null
    var weight: Long? = null
    var age : Int? = null
    var languages: List<String>? = null
    var pageNum : Int = 0
    var size : Int = 2
    var sorted : Sort= Sort(Sort.Direction.ASC, "email")

    override fun getPageNumber(): Int  = pageNum

    override fun next(): Pageable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPageSize(): Int  = size

    override fun getOffset(): Int = pageNum * size

    override fun hasPrevious(): Boolean = pageNum == 0

    override fun getSort(): Sort  = sorted

    override fun first(): Pageable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun previousOrFirst(): Pageable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}