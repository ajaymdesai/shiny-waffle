package com.ajaymdesai.micronaut.test;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import io.micronaut.views.View;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;


@Validated
@Secured(SecurityRule.IS_ANONYMOUS)
@Controller(value = "/", produces = MediaType.TEXT_HTML)
public class HomeController {
    private static final Logger LOG = LoggerFactory.getLogger(GreetingController.class);

    @View("home")
    @Get(produces = MediaType.TEXT_HTML)
    public Single<Greeting> index() {
        return Single.just(new Greeting());
    }
}
