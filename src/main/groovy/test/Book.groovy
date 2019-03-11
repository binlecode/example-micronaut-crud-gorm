package test

import grails.gorm.annotation.Entity
import groovy.transform.CompileStatic

//@EqualsAndHashCode
//@CompileStatic
@Entity
class Book {

    Long id
    String isbn
    String name

    static constraints = {
        isbn nullable: true, unique: true
        name blank: false
    }
}
