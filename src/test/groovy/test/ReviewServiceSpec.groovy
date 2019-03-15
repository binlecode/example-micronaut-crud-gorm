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
//        review.book = book
        review.content = 'test review for the book'
        book.addToReviews(review)
        book.save(flush: true)

//        then:
//        review.book.id == 1
//        review.bookId == 1

        then:
        book.reviews.size() == 1
        book.reviews[0].id == 1
        reviewService.countByBook(book) == 1

    }


}
