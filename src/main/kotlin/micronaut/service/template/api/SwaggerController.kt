package micronaut.service.template.api

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.swagger.v3.oas.annotations.Hidden

@Hidden
@Controller("/")
class SwaggerController {

    @Get("/swagger/ui")
    @Produces(MediaType.TEXT_HTML)
    fun swaggerUi() = swagger()

    @Get("/")
    @Produces(MediaType.TEXT_HTML)
    fun swaggerUiAtRoot() = swagger()

    private fun swagger() = SwaggerController::class.java.getResource("/swaggerui.html").readText()
}
