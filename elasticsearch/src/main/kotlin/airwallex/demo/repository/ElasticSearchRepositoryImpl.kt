package airwallex.demo.repository

import airwallex.demo.io.UserRequest
import airwallex.demo.model.User
import airwallex.demo.utils.Codecs
import io.vavr.control.Either
import org.elasticsearch.index.query.QueryBuilders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.DeleteQuery
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.data.elasticsearch.core.query.SearchQuery
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @author bin.guo
 * On 2018/10/31
 */
@Repository
open class ElasticSearchRepositoryImpl(
        @Autowired private val template: ElasticsearchTemplate
) : ElasticSearchRepository {

    override fun findAll(pageable: UserRequest): Either<String, Page<User>> {
        return try {
            val filter = QueryBuilders
                    .rangeQuery("age")
                    .gte(pageable.minAge)
                    .lte(pageable.maxAge)
            val query : SearchQuery = NativeSearchQueryBuilder()
                    .withFilter(filter)
                    .withPageable(pageable)
                    .build()
            val result = template.queryForPage(query, User::class.java)
            Either.right(result)
        } catch (e : Exception) {
            Either.left(e.message)
        }
    }

    override fun findByIdx(id: UUID): Either<String, User> {
        return try {
            val filter = QueryBuilders.matchQuery("id", id)
            val query : SearchQuery = NativeSearchQueryBuilder()
                    .withFilter(filter)
                    .build()
            val result = template.query(query) {
                it.hits.firstOrNull()?.sourceAsString?.let { str ->
                    Codecs.jsonToObj(str, User::class.java)
                }
            }
            requireNotNull(result) { "No such user for id:$id" }
            Either.right(result)
        } catch (e : Exception) {
            Either.left(e.message)
        }
    }

    override fun save(user: User): Either<String, User> {
        return try {
            val indexQuery = IndexQueryBuilder()
                    .withId(user.id.toString())
                    .withObject(user)
                    .build()
            val result = template.index(indexQuery)
            val saved = user.copy(id = UUID.fromString(result))
            return Either.right(saved)
        } catch (e : Exception) {
            Either.left(e.message)
        }
    }

    override fun delete(id: UUID) {
        try {
            val deleteQuery = DeleteQuery()
            deleteQuery.index = "airwallex_user_x"
            deleteQuery.type = "users"
            deleteQuery.query = QueryBuilders.matchQuery("id", id)
            template.delete(deleteQuery)
        } catch (e : Exception) {
            throw e
        }
    }

    override fun findAll(): MutableIterable<User> {
        val query : SearchQuery = NativeSearchQueryBuilder().build()
        return template.queryForList(query, User::class.java)
    }

    override fun findAll(ids: MutableIterable<UUID>?): MutableIterable<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <S : User?> save(entity: S): S {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <S : User?> save(entities: MutableIterable<S>?): MutableIterable<S> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(entity: User?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(entities: MutableIterable<User>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findOne(id: UUID?): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun count(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun exists(id: UUID?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}