package airwallex.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @author bin.guo
 * On 2018/10/31
 */
@SpringBootApplication
open class Application {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(Application::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
            LOGGER.info("Elastic search demo is up.")
        }
    }

}