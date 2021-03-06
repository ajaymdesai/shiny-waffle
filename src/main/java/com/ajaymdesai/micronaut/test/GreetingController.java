package com.ajaymdesai.micronaut.test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import io.reactivex.Flowable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;


@Validated
@Secured(SecurityRule.IS_ANONYMOUS)
@Controller(value = "/greet", produces = MediaType.APPLICATION_JSON)
public class GreetingController {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);

    @Get(produces = MediaType.TEXT_PLAIN)
    public Single<String> index() {
        return Single.just(new Greeting().toString());
    }

    @Get(value = "/{name}")
    public Single<Greeting> greet(@NotNull final String name) {
        Optional<HttpRequest<Object>> request = ServerRequestContext.currentRequest();
        return Single.just(new Greeting().withName(name));
    }

    @Post(value = "/echo", consumes = MediaType.TEXT_PLAIN, produces = MediaType.TEXT_PLAIN)
    public Single<String> echo(@Size(max = 1024) @Body final String text) {
        return Single.just(text);
    }

    @Post(value = "/echo-stream",
        consumes = MediaType.APPLICATION_OCTET_STREAM,
        produces = MediaType.APPLICATION_OCTET_STREAM)
    public Single<MutableHttpResponse<String>> echoFlow(
        @Body final Flowable<String> text) {
        return text.collect(
            StringBuffer::new,
            StringBuffer::append).map(
            (buffer) -> HttpResponse.ok(buffer.toString())
        );
    }

    @Post(value = "/echo-json", consumes = MediaType.APPLICATION_JSON)
    public Single<Greeting> echoJson(@Body final Greeting greeting) {
        return Single.just(greeting);
    }
}
