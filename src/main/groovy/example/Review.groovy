package example

import com.fasterxml.jackson.annotation.JsonIgnore
import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity


// Each class to be a GORM entity should be annotated with the grails.gorm.annotation.Entity annotation.

@Entity
class Review implements GormEntity<Review> {

    Long id
    String content

    @JsonIgnore
    Book book
//    static belongsTo = [book: Book]

    static constraints = {
        content blank: false, maxSize: 1024
    }

    static mapping = {
//        book column: 'BOOK_ID'
    }


}
