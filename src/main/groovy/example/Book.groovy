package example

import com.fasterxml.jackson.annotation.JsonIgnore
import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

//@EqualsAndHashCode
//@CompileStatic
@Entity
class Book implements GormEntity<Book> {

    Long id
    String isbn
    String name

    @JsonIgnore
    List<Review> reviews

    static hasMany = [reviews: Review]

    static constraints = {
        isbn nullable: true, unique: true
        name blank: false
    }
}
