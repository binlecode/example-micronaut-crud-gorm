package test

import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject


// In some tests you may need a reference to the ApplicationContext and/or the EmbeddedServer (for example,
// to create an instance of an HttpClient). Rather than defining these as properties of the test (such as a
// @Shared property in Spock), when using @MicronautTest you can reference the server/context that was started
// up for you, and inject them directly in your test.
@MicronautTest
class HelloControllerSpec extends Specification {

    @Inject
    EmbeddedServer embeddedServer

    @Inject
    ApplicationContext applicationContext

    @Inject
    @Client("/hello")
    RxHttpClient client


    void "test index"() {
        given:
//        HttpResponse response = client.toBlocking().exchange("/hello")
        String response = client.toBlocking().retrieve("/")

        expect:
        response == 'hello mn'
    }

    void "test getJson"() {
        when:
        def resp = client.toBlocking().retrieve('/map2json', Map.class)
        then:
        resp.works == 'yay!'
        resp.greeting == 'it is You'

        when:
        resp = client.toBlocking().retrieve("/map2json?name=Bob", Map.class)
        then:
        resp.works == 'yay!'
        resp.greeting == 'it is Bob'
    }
}
