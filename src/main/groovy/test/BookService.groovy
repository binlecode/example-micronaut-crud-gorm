package test

import grails.gorm.services.Service
import io.micronaut.validation.Validated

import javax.validation.constraints.NotNull


/**
 * Not a big fan of interface based service alone, this is only to illustrate GORM-JPA auto implementation
 * based on Micronaut's @Decoration aop pattern.
 * A better way is to create an abstract class to for custom persistence methods while still keep 'template'
 * methods as abstract method
 * @see {@link ReviewService}
 */
@Service(Book)
@Validated
interface BookService {

    int count()

    List<Book> findAll()

    Book find(@NotNull Long id)

    Book save(@NotNull Book book)

    void delete(@NotNull Long id)
}
