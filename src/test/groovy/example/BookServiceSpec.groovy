package example

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
        // separate create, delete and check in separate transactions to ensure session is flushed (by commit)
        // before check
        // in spock by default the whole feature method is wrapped in single transaction, and interface (JPA)
        // methods have no explicit session flush but rather rely on transaction boundary

        given:
        Book book
        Book.withNewTransaction {
            book = bookService.save(new Book(name: 'test-book'))
        }
        when:
        def count = bookService.count()
        then:
        count == 1

        when:

        Book.withNewTransaction {
            bookService.delete(book.id)
        }
        Book.withNewTransaction {
            count = bookService.count()
        }
        then:
        count == 0

    }


}
