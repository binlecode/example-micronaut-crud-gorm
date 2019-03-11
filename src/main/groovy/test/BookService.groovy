package test

import grails.gorm.services.Service

import javax.validation.constraints.NotNull

@Service(Book)
interface BookService {

    int count()

    List<Book> findAll()

    Book find(@NotNull Long id)

    Book save(@NotNull Book book)

    void delete(@NotNull Long id)

}
