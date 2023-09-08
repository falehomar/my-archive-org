package my.archive.org;


import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.retry.annotation.Retryable;
import io.reactivex.rxjava3.core.Flowable;
import lombok.*;

import java.nio.ByteBuffer;

@Client("https://archive.org")
@Retryable
public interface ArchiveOrg {
    /**@Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder(toBuilder = true)
    class Metadata {

    }*/
    // https://archive.org/services/search/beta/page_production/
    // ?service_backend=metadata
    // &user_query=
    // &page_type=collection_details
    // &page_target=iacl
    // &hits_per_page=100
    // &page=1
    // &filter_map=%7B%22lending___status%22%3A%7B%22is_readable%22%3A%22inc%22%7D%7D
    // &aggregations=false
    // &uid=R%3A02c1efc0d40756019f66-S%3A394d56110b6afe2944b0-P%3A1-K%3Ah-T%3A1693515852383
    // &client_url=https%3A%2F%2Farchive.org%2Fdetails%2Fiacl%3Fpage%3D2%26and%255B%255D%3Dlending%253A%2522is_readable%2522
    @Get(uri = "/services/search/beta/page_production/",produces = MediaType.APPLICATION_JSON)
    @Headers({
            @Header(name = "Accept",value = "*/*"),
            @Header(name = "Accept-Encoding",value = "Accept-Encoding"),
            @Header(name = "User-Agent",value = "PostmanRuntime/7.32.3"),
            @Header(name = "Host",value = "archive.org")
    })
    Flowable<HttpResponse<?>> page_production(
            @QueryValue String service_backend,
            @QueryValue String user_query,
            @QueryValue String page_type,
            @QueryValue String page_target,
            @QueryValue Integer hits_per_page,
            @QueryValue Integer page,
            @QueryValue String filter_map,
            @QueryValue Boolean aggregations,
            @QueryValue String uid,
            @QueryValue String client_url
    );

    @Get(value = "/download/{identifier}/{identifier}.pdf",produces = MediaType.APPLICATION_PDF)
    @Headers({
            @Header(name = "Accept",value = "*/*"),
            @Header(name = "Accept-Encoding",value = "Accept-Encoding"),
            @Header(name = "User-Agent",value = "PostmanRuntime/7.32.3"),
            @Header(name = "Host",value = "archive.org"),
            @Header(name = "Connection",value = "keep-alive"),
    })
    Flowable<HttpResponse<byte[]>> download(@PathVariable String identifier);
}
