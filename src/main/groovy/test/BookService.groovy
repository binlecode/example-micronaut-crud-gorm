package test

import grails.gorm.services.Service
import io.micronaut.validation.Validated

import javax.validation.constraints.NotNull

@Service(Book)
@Validated
interface BookService {

    int count()

    List<Book> findAll()

    Book find(@NotNull Long id)

    Book save(@NotNull Book book)

    void delete(@NotNull Long id)

}
