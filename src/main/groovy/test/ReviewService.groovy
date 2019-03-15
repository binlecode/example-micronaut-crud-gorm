package test

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

// use abstract class instead of interface as shortcut to implement custom persistence methods
@Service(Review)
abstract class ReviewService {

    /**
     * Count reviews by book
     * @param book
     */
    abstract int countByBook(Book book)

    /**
     */
    abstract Review save(Review review)

    /**
     * Delete review by id
     */
    abstract void delete(Long id)

    /**
     * Find review by id
     */
    abstract Review find(Long id)

    /**
     * Find all reviews by book id
     */
    @Transactional(readOnly = true)
    List<Review> findAllByBookId(Long bookId) {  //todo: add @NotNull to bookId param will raise error coz it is not a concrete class
        Review.findAll() { book == Book.findById(bookId) }
//        Book book = Book.findById(bookId)
//        return book?.reviews ?: []
    }


    @Transactional
    Review save(Map params) {
        Book book = Book.findById(params.bookId as Long)
        if (book) {
            Review review = new Review(content: params.content)

            //todo: direct book assignment on review doesn't work: review.book = book; review.save() will raise error

            book.addToReviews(review)
//            book.save()    // either book.save() or review.save() works
            review.save()
            return review
        } else {
            return null
        }
    }

}
