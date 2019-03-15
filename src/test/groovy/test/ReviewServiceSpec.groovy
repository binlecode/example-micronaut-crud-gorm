package test

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

    def 'should be able to create review for a book'() {
        when:
        Book book = new Book(name: 'test-book')
        Review review = new Review()
        review.content = 'test review for the book'
        book.addToReviews(review)
        book.save(flush: true)

        then:
        book.reviews.size() == 1
        Review rv = book.reviews[0]
        rv.id == 1
        rv.content == 'test review for the book'
        rv.bookId == book.id

        when:
        def count = reviewService.countByBook(book)
        then:
        count == 1
    }

    def 'should be able to update review for a book'() {
        given:
        Book book = new Book(name: 'test-book-1')
        Review review = new Review()
        review.content = 'test review for the book-1'
        book.addToReviews(review)
        book.save(flush: true)

        when:
        reviewService.save(bookId: book.id, id: review.id, content: 'updated test review')

        then:
        List<Review> rvs = reviewService.findAllByBookId(book.id)
        rvs.size() == 1
        rvs[0].content == 'updated test review'


    }


}
