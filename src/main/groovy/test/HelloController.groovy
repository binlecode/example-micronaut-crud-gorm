package test

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Status
import io.micronaut.views.View

import javax.annotation.Nullable
import javax.validation.constraints.NotNull

@Controller("/hello")
class HelloController {

    @Get(value = "/", produces = MediaType.TEXT_PLAIN)
    String index() {
        return 'hello mn'
//        return HttpStatus.OK
    }

    @Get("/map2json{?name}")
    @Produces(MediaType.APPLICATION_JSON)  // try to auto bind return response data type to JSON
    HttpResponse getJson(@Nullable String name) {
        Map data = [works: 'yay!', greeting: "it is ${name ?: 'You'}".toString()] // note the .toString() on GString obj
        return HttpResponse.status(HttpStatus.OK).body(data)
    }

    @Get("/map2text")
    @Produces(MediaType.TEXT_PLAIN)  // bind response data to string format
    HttpResponse getText() {
        return HttpResponse.ok([works: 'yay', name: 'text free!'])
    }

    /**
     * POST with body {"name": "teech", /optional: "color": "white"/}
     * @param name  json field "name"
     * @param color json field "color", optional
     */
    @Post("/showTeeth")
//    @Produces(MediaType.APPLICATION_JSON)  // auto bind json body key to action arguments
    @Status(HttpStatus.CREATED) // customize successful response http code
    HttpResponse showTeech(@NotNull String name, @Nullable String color, @Header("Content-Type") Optional<String> contentType) {
        if (contentType.isPresent()) {
            if (contentType.get() == MediaType.APPLICATION_JSON) {
                // return json by default
                return HttpResponse.ok([name: name, color: color, pretendCreated: true])
            } else if (contentType.get() == MediaType.APPLICATION_XML) {
                // return xml
            }
        }
            // return plain text

    }

    /**
     * Html view is also supported by integrating a view template engine, such as Thymeleaf.
     */
    @Get("/showView")
    @View("hello")
//    @Produces(MediaType.TEXT_HTML)  // this can be saved as view is specified in annotation
    HttpResponse<Map> showView() {
        return HttpResponse.ok([name: '<i>there</i>', greeting: '<em>hello</em>'])
    }


}
