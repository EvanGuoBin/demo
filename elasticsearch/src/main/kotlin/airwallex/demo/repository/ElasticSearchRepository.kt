package airwallex.demo.repository

import airwallex.demo.io.UserRequest
import airwallex.demo.model.User
import io.vavr.control.Either
import org.springframework.data.domain.Page
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.RepositoryDefinition
import java.util.*

/**
 * @author bin.guo
 * On 2018/10/31
 */
// 注释的行不通
//@RepositoryDefinition(
//        domainClass = User::class,
//        idClass = UUID::class
//)
@NoRepositoryBean
interface ElasticSearchRepository : CrudRepository<User, UUID> {

    /**
     * Returns a [Page] of entities meeting the paging restriction provided in the `Pageable` object.
     *
     * @param pageable
     * @return a page of entities
     */
    fun findAll(pageable: UserRequest): Either<String, Page<User>>

    fun findByIdx(id : UUID) : Either<String, User>

    fun save(user : User) : Either<String, User>

    override fun delete(id : UUID)
}