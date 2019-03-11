package test

import grails.gorm.transactions.Rollback
import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@Rollback
class BookServiceSpec extends Specification {

//    @Shared @AutoCleanup ApplicationContext context =
//            ApplicationContext.build().mainClass(BookServiceSpec).start()
//    @Shared HibernateDatastore hibernateDatastore = context.getBean(HibernateDatastore)

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared BookService bookService = embeddedServer.getApplicationContext().getBean(BookService)

    def 'should list books'() {
//        given:
//        BookService bookService = hibernateDatastore.getService(BookService)

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
}
