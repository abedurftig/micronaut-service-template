package micronaut.service.template

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.ApplicationContext
import org.assertj.core.api.Assertions
import org.spekframework.spek2.Spek

object AppTest : Spek({

    val appContext = ApplicationContext.run("test")

    group("Object mapper") {

        val objectMapper = appContext.getBean(ObjectMapper::class.java)

        test("Kotlin module should be enabled") {
            Assertions.assertThat(objectMapper.registeredModuleIds)
                .contains("com.fasterxml.jackson.module.kotlin.KotlinModule")
        }

        test("JDK 8 module should be enabled") {
            Assertions.assertThat(objectMapper.registeredModuleIds)
                .contains("com.fasterxml.jackson.datatype.jdk8.Jdk8Module")
        }

        test("Java 8 time module should be enabled") {
            Assertions.assertThat(objectMapper.registeredModuleIds)
                .contains("com.fasterxml.jackson.datatype.jsr310.JSR310Module")
        }

        test("Should not serialize dates with time stamps") {
            val serializationFeatureEnabled =
                objectMapper.deserializationConfig.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            Assertions.assertThat(serializationFeatureEnabled).isFalse()
        }
    }

    afterGroup {
        appContext.close()
    }
})
