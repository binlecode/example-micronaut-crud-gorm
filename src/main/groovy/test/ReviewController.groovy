package test

import groovy.transform.CompileStatic
import io.micronaut.context.annotation.Parameter
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status
import io.micronaut.validation.Validated

@CompileStatic
@Controller("/review")
@Validated
class ReviewController {

    private final ReviewService reviewService

    ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService
    }

    @Get("/")
    List<Review> index() {
        return []
    }

    @Get("/{id}")
    Review get(@Parameter Long id) {
        return reviewService.find(id)
    }

    @Post("/")
    @Status(HttpStatus.CREATED)
    Review create(@Body Map params) {
        return reviewService.save(params)
    }

}
