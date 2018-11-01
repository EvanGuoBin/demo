package airwallex.demo.controller

import airwallex.demo.io.UserRequest
import airwallex.demo.model.User
import airwallex.demo.repository.ElasticSearchRepository
import io.vavr.control.Either
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * @author bin.guo
 * On 2018/10/31
 */
@RestController
@RequestMapping("/elasticsearch/demo")
open class ElasticSearchOpController(
        @Autowired private val repository: ElasticSearchRepository
) {

    @GetMapping("/home/{name}")
    open fun home(@PathVariable name : String) : String = "Hello, $name"

    @PostMapping("/user")
    open fun save(@RequestBody user : UserDTO) : User {
        val param = User(
                id = user.id?: UUID.randomUUID(),
                name = user.name?: "",
                email = user.email?: "",
                birthday = user.birthday?: "2000-01-01",
                height = user.height?: 0,
                weight = user.weight?: 0,
                age = user.age?: 0,
                languages = user.languages?: listOf()
                )
        val result : Either<String, airwallex.demo.model.User> = repository.save(param)
        return if (result.isLeft) {
            throw RuntimeException(result.left)
        } else {
            result.get()
        }
    }

    @GetMapping("/user/{id}")
    open fun find(@PathVariable id: UUID) : User {
        val result = repository.findByIdx(id)
        return if (result.isLeft) {
            throw RuntimeException(result.left)
        } else {
            result.get()
        }
    }

    @DeleteMapping("/user/{id}")
    open fun delete(@PathVariable id: UUID) {
        repository.delete(id)
    }

    @GetMapping("/allUsers")
    open fun findAll() : List<User> = repository.findAll().toList()

    @GetMapping("/users")
    open fun findByPage(size : Int,
                        pageNum: Int,
                        minAge: Int,
                        maxAge: Int) : PageResult<User> {
        val request = UserRequest()
        request.size = size
        request.pageNum = pageNum
        request.minAge = minAge
        request.maxAge = maxAge

        val result = repository.findAll(request)
        return if (result.isLeft) {
            throw RuntimeException(result.left)
        } else {
            val page = result.get()
            val res = PageResult<User>()
            res.total = page.totalElements.toInt()
            res.values = page.content
            res.pageNum = page.number
            res.pageSize = page.size
            res
        }
    }

    class UserDTO {
        var id : UUID? = null
        var name : String? = null
        var email : String? = null
        var birthday: String? = null
        var height: Long? = null
        var weight: Long? = null
        var age : Int? = null
        var languages: List<String>? = null
    }

    class PageResult<T> {
        var total: Int = 0
        var values: List<T> = listOf()
        var pageNum: Int = 0
        var pageSize: Int = 0
    }
}