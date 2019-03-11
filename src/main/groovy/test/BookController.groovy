package test

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.annotation.Parameter
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated

@Slf4j
@CompileStatic
@Controller("/book")
@Validated
class BookController {

    private final BookService bookService

    // prefer constructor injection over setter injection
    BookController(BookService bookService) {
        this.bookService = bookService
    }
//    @Inject
//    BookService bookService

    @Get("/")
    List<Book> index() {
        log.info "listing books"
        return bookService.findAll()
    }

    @Get("/{id}")
    Book get(@Parameter Long id) {
        return bookService.find(id)
    }

    @Post("/")
    @Status(HttpStatus.CREATED)
    Book create(@Body Book book) {
        return bookService.save(book)
    }

    @Put("/{id}")
    Book update(@Parameter Long id, @Body Book book) {
        Book.withTransaction {
            Book bk = bookService.find(id)
            if (bk) {
                bk.isbn = book.isbn
                bk.name = book.name
                return bookService.save(bk)
            } else {
                return null
            }
        }
    }

}
