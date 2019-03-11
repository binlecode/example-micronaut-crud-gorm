package test

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/hello")
class HelloController {

    @Get(value = "/", produces = MediaType.TEXT_PLAIN)
    String index() {
        return 'hello mn'
//        return HttpStatus.OK
    }
}
