package example

import io.micronaut.runtime.Micronaut
import groovy.transform.CompileStatic
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License


@OpenAPIDefinition(
        info = @Info(
                title = "Hello World",
                version = "0.1",
                description = "Example Micronaut REST app with GORM",
                license = @License(name = "Apache 2.0", url = "https://github.com/binlecode/example-micronaut-crud-gorm"),
                contact = @Contact(
                        url = "https://github.com/binlecode/example-micronaut-crud-gorm",
                        name = "Bin Le",
                        email = "bin.le.code@gmail.com"
                )
        )
)
@CompileStatic
class Application {
    static void main(String[] args) {
        Micronaut.run(Application)
    }
}
