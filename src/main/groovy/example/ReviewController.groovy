package example

import groovy.transform.CompileStatic
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated

import javax.validation.constraints.NotNull

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
        return reviewService.findAll()
    }

    @Get("/{id}")
    Review get(@NotNull Long id) {
        return reviewService.find(id)
    }

    @Post("/")
    @Status(HttpStatus.CREATED)
    Review create(@NotNull @Body Map params) {
        return reviewService.save(params)
    }

    @Put("/{id}")
    Review update(@NotNull Long id, @NotNull @Body Map params) {
        params.id = id
        return reviewService.save(params)
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    void delete(@NotNull Long id) {
        reviewService.delete(id)
    }

}
