package micronaut.service.template.api.v1.hello

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import micronaut.service.template.api.v1.hello.schema.Greeting

@Controller("/api/v1/hello")
class HelloController : HelloApi {
    override fun getGreeting(): HttpResponse<Greeting> {
        return HttpResponse.ok(Greeting("Hello World!"))
    }
}
