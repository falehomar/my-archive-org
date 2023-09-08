package my.archive.org

import com.jayway.jsonpath.JsonPath
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpResponse
import io.micronaut.json.tree.JsonNode
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subscribers.TestSubscriber
import jakarta.inject.Inject
import spock.lang.Specification

import java.nio.ByteBuffer

@MicronautTest
@Slf4j
class ArchiveOrgTest extends Specification {
    @Inject
    ArchiveOrg archiveOrg

    def "Page_production"() {
        given:
        def subscriber = new TestSubscriber()

        def pageProduction = archiveOrg.page_production(
                "metadata",
                "",
                "collection_details",
                "iacl",
                500,
                1,
                "{\"lending___status\":{\"is_readable\":\"inc\"}}",
                false,
                "R%3A02c1efc0d40756019f66-S%3A394d56110b6afe2944b0-P%3A1-K%3Ah-T%3A1693515852383",
                "https%3A%2F%2Farchive.org%2Fdetails%2Fiacl%3Fpage%3D2%26and%255B%255D%3Dlending%253A%2522is_readable%2522")

        when:

        pageProduction
                .map {
                    JsonPath.parse(it.body())
                            .read('$..fields.identifier')
                }
                .map { List<String> it ->
                    it
                }
                .flatMapStream {it.stream()}
                .subscribe(subscriber)

        then:
        noExceptionThrown()
        subscriber.await().assertComplete()
    }

    def "download" () {
        given:
        def download = archiveOrg.download("misterfox00newyiala")


        def subscriber = new TestSubscriber<HttpResponse<ByteBuffer>>()
        when:
        download.subscribe(subscriber)

        then:
        noExceptionThrown()
        subscriber.await().assertComplete()
        subscriber.assertValue {
            it.contentLength==412794
        }

    }
}
