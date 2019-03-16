package test

import grails.gorm.services.Service
import io.micronaut.validation.Validated

import javax.validation.constraints.NotNull

@Service(Book)
@Validated
abstract class BookService {

    abstract int count()

    abstract List<Book> findAll()

    abstract Book find(@NotNull Long id)

    abstract Book save(@NotNull Book book)

//    @Transactional  // Gorm @Service annotation defaults @Transactional to each public method
    void delete(Long id) {
        Book book = Book.get(id)
        book.delete(flush: true)  // flush is needed to ensure db state is synced in the overall Hibrernate transaction
    }

}
