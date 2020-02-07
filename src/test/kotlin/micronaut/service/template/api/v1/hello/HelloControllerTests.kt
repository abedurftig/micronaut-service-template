package micronaut.service.template.api.v1.hello

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import micronaut.service.template.api.v1.hello.schema.Greeting
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.net.URI
import javax.inject.Inject

@MicronautTest
class HelloControllerTests {

    @Inject
    private lateinit var embeddedServer: EmbeddedServer

    @Test
    fun getGreeting() {

        val client = HttpClient.create(embeddedServer.url)
        val request = HttpRequest.GET<Any>(URI.create("/api/v1/hello"))

        val response = client.toBlocking().exchange(
            request,
            Argument.of(Greeting::class.java)
        )

        Assertions.assertThat(response.status).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(response.body).isPresent
        Assertions.assertThat(response.body()!!.message).isEqualTo("Hello World!")
    }
}
