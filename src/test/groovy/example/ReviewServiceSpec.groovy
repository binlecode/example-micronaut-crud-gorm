package example

import grails.gorm.transactions.Rollback
import io.micronaut.context.ApplicationContext
import org.grails.orm.hibernate.HibernateDatastore
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@Rollback
class ReviewServiceSpec extends Specification {

    @Shared @AutoCleanup ApplicationContext context =
            ApplicationContext.build().mainClass(ReviewService).start()
    @Shared HibernateDatastore hibernateDatastore = context.getBean(HibernateDatastore)
    @Shared ReviewService reviewService = hibernateDatastore.getService(ReviewService)
    @Shared BookService bookService = hibernateDatastore.getService(BookService)


    def 'should be able to create a review for a book'() {
        given:
        Book book = bookService.save(new Book(name: 'test book'))
        when:
        Review review = reviewService.save(bookId: book.id, content: 'test review for test book')

        then:
        review.id
        reviewService.countByBookId(book.id) == 1

        when:
        List<Review> rvs = reviewService.findAllByBookId(book.id)
        then:
        rvs.size() == 1
        rvs[0].content == 'test review for test book'
    }

    def 'should be able to update a review for a book'() {
        given:
        Book book = bookService.save(new Book(name: 'test book'))
        when:
        Review review = reviewService.save(bookId: book.id, content: 'test review for test book')
        then:
        reviewService.countByBook(book) == 1

        when:
        reviewService.save(id: review.id, content: 'updated test review for test book')

        then:
        reviewService.countByBook(book) == 1
        reviewService.findAllByBookId(book.id)[0].content == 'updated test review for test book'
    }

    def 'should be able to delete a review for a book'() {
        given:
        Book book
        Review review
        Book.withNewTransaction {
            book = bookService.save(new Book(name: 'test book'))
            review = reviewService.save(bookId: book.id, content: 'test review for test book')
        }
        when:
        def count = reviewService.countByBookId(book.id)
        then:
        count == 1

        when:
        Book.withNewTransaction {
            reviewService.delete(review.id)
        }
        Book.withNewTransaction {
            count = reviewService.countByBookId(book.id)
        }
        then:
        count == 0
    }






}
