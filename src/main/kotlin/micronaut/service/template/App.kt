package micronaut.service.template

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
        title = "Micronaut Service Template",
        version = "0.1",
        description = "This is the is a template for a service built with Micronaut",
        contact = Contact(
            url = "https://gitlab.forfriday.de/honey-badger/micronaut-service-template",
            email = "team_honey_badger@friday.de"
        )
    )
)
object App {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
            .packages("micronaut.service.template")
            .mainClass(App.javaClass)
            .start()
    }
}
