package test

import io.micronaut.context.ApplicationContext
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class BookControllerSpec extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)
    @Shared @AutoCleanup RxHttpClient client = embeddedServer.applicationContext.createBean(RxHttpClient, embeddedServer.getURL())


    void "test index getting back list of books"() {
        given:
        List books = client.toBlocking().retrieve(HttpRequest.GET('/book'), Argument.of(List, Book))

        expect:
        books.size() == 0
    }

    void "GET /book/{id} with an invalid Long number responds 400"() {
        when:
        client.toBlocking().exchange("/book/badId")

        then:
        HttpClientResponseException e = thrown(HttpClientResponseException)
        e.status.code == 400
    }

    void "GET /book/{id} with invalid id responds 404"() {
        when:
        client.toBlocking().exchange("/book/999")

        then:
        HttpClientResponseException e = thrown(HttpClientResponseException)
        e.status.code == 404
    }
}
