package test

import grails.gorm.transactions.Rollback
import io.micronaut.context.ApplicationContext
import org.grails.orm.hibernate.HibernateDatastore
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@Rollback
class BookServiceSpec extends Specification {

    // for service spec, there's no need to invoke embedded server

    @Shared @AutoCleanup ApplicationContext context =
            ApplicationContext.build().mainClass(BookServiceSpec).start()
    @Shared HibernateDatastore hibernateDatastore = context.getBean(HibernateDatastore)
    @Shared BookService bookService = hibernateDatastore.getService(BookService)

//    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
//    @Shared BookService bookService = embeddedServer.getApplicationContext().getBean(BookService)

    def 'should list books'() {
        when:
        def count = bookService.count()

        then:
        count == 0

        when:
        bookService.save(new Book(name: 'book1', isbn: 'test-isbn1'))
        then:
        bookService.count() == 1
        bookService.findAll().size() == 1
        bookService.findAll()[0].name == 'book1'
    }

    def 'should be able to delete a book'() {
        given:
        Book book = bookService.save(new Book(name: 'test-book'))
        when:
        def count = bookService.count()
        then:
        count == 1

        when:
        bookService.delete(book.id)
        count = bookService.count()
        then:
        count == 0

    }


}
