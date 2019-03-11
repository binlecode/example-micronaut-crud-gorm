package test

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class BookControllerSpec extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())


    void "test index getting back list of books"() {
        given:
        List books = client.toBlocking().retrieve(HttpRequest.GET('/books'), Argument.of(List, Book))

        expect:
        books.size() == 3
    }
}
