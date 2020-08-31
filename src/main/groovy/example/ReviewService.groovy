package example

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import io.micronaut.validation.Validated

import javax.inject.Inject
import javax.validation.constraints.NotNull

// use abstract class instead of interface as shortcut to implement custom persistence methods

// Each method that interacts with GORM should be annotated with GORM’s grails.gorm.transactions.Transactional
// to ensure a session is present. You can also add the @Transactional annotation to the class.

@Service(Review)
@Validated
abstract class ReviewService {
    @Inject  // abstract class can not use constructor DI
    BookService bookService

    /**
     * Count reviews
     */
    abstract int count()

    /**
     * Count reviews by book
     * @param book
     */
    abstract int countByBook(Book book)

    /**
     * Count reviews by book id
     */
    @Transactional(readOnly = true)
    int countByBookId(Long bookId) {
        return countByBook(Book.findById(bookId))
    }

    /**
     * List all reviews
     */
    abstract List<Review> findAll()

    /**
     * Find all reviews by book
     */
    abstract List<Review> findAllByBook(Book book)

    /**
     * Find all reviews by book id
     */
    @Transactional(readOnly = true)
    List<Review> findAllByBookId(Long bookId) {  //fixme: adding @NotNull to bookId in concreate method will raise MethodNotFund error
        Review.findAll() { book == Book.findById(bookId) }
    }

    /**
     * Find review by id
     */
    abstract Review find(@NotNull Long id)

    /**
     * Delete review by id
     */
    abstract void delete(@NotNull Long id)

    /**
     * Hibernate/JPA standard method, requires a fully-fledged review instance, including nested book object
     */
    abstract Review save(Review review)

    /**
     * Create or update review
     * @param params map of id (optional), bookId (optional) and content
     */
    @Transactional
    Review save(Map params) {
        Review review
        if (params.id) {
            review = find(params.id as Long)
            if (!review) {
                return
            }
        } else {
            review = new Review()
        }
        review.content = params.content
        if (params.bookId) {
            Book book = bookService.find(params.bookId as Long)
            if (book) {
                // direct book assignment on review doesn't work: review.book = book; review.save() will raise error
                // this is prob due to strict rule that FK can not be re-assigned from child side
                book.addToReviews(review)
            }
        }
        return review.save()
    }



}
