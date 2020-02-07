package micronaut.service.template.api.v1.hello

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import micronaut.service.template.api.v1.hello.schema.Greeting

@Tag(name = "Hello Api")
interface HelloApi {

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get a greeting",
        description = "Returns a greeting with a message.")
    @ApiResponses(
        ApiResponse(responseCode = "500", description = "Internal Server Error"),
        ApiResponse(
            responseCode = "200", description = "The greeting.", content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(
                        type = "object",
                        implementation = Greeting::class
                    )
                )
            ]
        )
    )
    fun getGreeting(): HttpResponse<Greeting>
}
