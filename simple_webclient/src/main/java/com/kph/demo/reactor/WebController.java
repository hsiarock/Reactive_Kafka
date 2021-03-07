package com.kph.demo.reactor;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class WebController {
    private static final Logger LOG = LoggerFactory.getLogger(WebController.class);

    @Value("${demo.reactive.server.rest.url}")
    private String demoReactiveServerRestUrl;

    @Value("${demo.postgres.jpa.rest.url}")
    private String demoPostgresJpaRestUrl;

    @GetMapping("/slow-service-tweets")
    public List<Tweet> getAllTweets() throws Exception {
        Thread.sleep(2000L); // delay
        return Arrays.asList(
                new Tweet("RestTemplate rules", "@user1"),
                new Tweet("WebClient is better", "@user2"),
                new Tweet("OK, both are useful", "@user1"));
    }

    // to eliminate - could be refactored into a named _static_ inner class [com.kph.demo.reactor.WebController]
    // At WebController.java:[line 47] SIC_INNER_SHOULD_BE_STATIC_ANO
    static class MyParameterizedTypeReference<T> extends ParameterizedTypeReference<T> {
    }

    @GetMapping("/tweets-blocking")
    public Flux<Tweet> getTweetsBlocking() {
        LOG.info("Starting BLOCKING Controller!");
        final String uri = getSlowServiceUri();

        RestTemplate restTemplate = new RestTemplate();
        MyParameterizedTypeReference<List<Tweet>> typeTweet = new MyParameterizedTypeReference<>(){};
        ResponseEntity<List<Tweet>> response = restTemplate.exchange(
                uri, HttpMethod.GET, null, typeTweet);

        List<Tweet> result = response.getBody();
        if (result != null) {
            Flux<Tweet> resFlux = Mono.just(result).log().flatMapMany(Flux::fromIterable);
            //result.forEach(tweet -> LOG.info(tweet.toString()));
            LOG.info("Exiting BLOCKING Controller!");
            return resFlux;
        }
        return null;
    }

    @GetMapping(value = "/tweets-non-blocking", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> getTweetsNonBlocking() {
        LOG.info("Starting NON-BLOCKING Controller!");
        Flux<Tweet> tweetFlux = WebClient.create()
                .get()
                .uri(getSlowServiceUri())
                .retrieve()
                .bodyToFlux(Tweet.class);

        tweetFlux.subscribe(tweet -> LOG.info(tweet.toString()));
        LOG.info("Exiting NON-BLOCKING Controller!");
        return tweetFlux;
    }

    //@GetMapping(value = "/get-employee", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @GetMapping(value = "/get-employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    // defafult timeout is 30 secs
    public Mono<String> getEmployeeFromDb(@PathVariable(value = "id") int employeeId) throws WebClientException {
        LOG.info("Starting NON-BLOCKING getEmployeefromDb using " + demoPostgresJpaRestUrl +
                getDemoPostgressJpaUri(employeeId));
        Mono<String> employeeMono = WebClient.create(demoPostgresJpaRestUrl)
                .get()
                .uri(getDemoPostgressJpaUri(employeeId))
//                .header("Authorization", "Basic " + Base64Utils
//                        .encodeToString((username + ":" + token).getBytes(UTF_8)))
                //.exchangeToMono()
                .retrieve()
                .bodyToMono(String.class);

        employeeMono.subscribe(employee -> LOG.info(employee));

//        Mono<String> response = headersSpec.exchangeToMono(response -> {
//            if (response.statusCode()
//                    .equals(HttpStatus.OK)) {
//                return response.bodyToMono(String.class);
//            } else if (response.statusCode()
//                    .is4xxClientError()) {
//                return Mono.just("Error response");
//            } else {
//                return response.createException()
//                        .flatMap(Mono::error);
//            }
//        });

        LOG.info("Exiting NON-BLOCKING getEmployeefromDb Controller!");
        return employeeMono;
    }

    private String getSlowServiceUri() {
        return demoReactiveServerRestUrl + "/slow-service-tweets";
    }

    private String getDemoPostgressJpaUri(int id) {
        return "/myemployees/get/" + id;
    }
}
/** another get response
//    Mono<String> response = headersSpec.exchangeToMono(response -> {
//        if (response.statusCode()
//                .equals(HttpStatus.OK)) {
//            return response.bodyToMono(String.class);
//        } else if (response.statusCode()
//                .is4xxClientError()) {
//            return Mono.just("Error response");
//        } else {
//            return response.createException()
//                    .flatMap(Mono::error);
//        }
//    });
**/

/** set timeout
//    HttpClient httpClient = HttpClient.create()
//            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
//            .responseTimeout(Duration.ofMillis(5000))
//            .doOnConnected(conn ->
//                    conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
//                        .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));
//
//    WebClient client = WebClient.builder()
//            .clientConnector(new ReactorClientHttpConnector(httpClient))
//            .build();
**/

/** configuratin for all Ractor code

    @Configuration
    public class WebClientConfig {

        private static final int TIMEOUT_IN_SECONDS = 2;

        @Bean
        public WebClient jsonPlaceholderWebClient(WebClient.Builder webClientBuilder) {
            TcpClient tcpClient = TcpClient.create()
                    .option(CONNECT_TIMEOUT_MILLIS, TIMEOUT_IN_SECONDS * 1000)
                    .doOnConnected(connection ->
                            connection
                                    .addHandlerLast(new ReadTimeoutHandler(TIMEOUT_IN_SECONDS))
                                    .addHandlerLast(new WriteTimeoutHandler(TIMEOUT_IN_SECONDS)));

            return webClientBuilder
                    .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .build();
        }
    }
}
**/

/** add user id password in basicAuthHeader

 String basicAuthHeader = "basic " + Base64Utils.encodeToString((username + ":" + password).getBytes())

 client.get().uri("/route/user/all")
        .accept(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, basicAuthHeader)
        .exchange()
        .flatMapMany(response -> response.bodyToFlux(User.class))
        .subscribe(u -> System.out.println("All Users : " + u.getUsername() + ":" + u.getEmail() +
                    ":" + u.getFullname()));

 ------------------------------------
 Since spring 5.1 you should set basic authentication with HttpHeaders#setBasicAuth, like this:

 webClient
        .get()
        .uri("https://example.com")
        .headers(headers -> headers.setBasicAuth("username", "password"))
        .exchange()
        ....

 the previous approach, of using .filter(basicAuthentication("user", "password"), is now deprecated.

 ----------------------------------------------------------------------
 return webClient.get()
           .uri("/user/repos")
           .header("Authorization", "Basic " + Base64Utils
           .encodeToString((username + ":" + token).getBytes(UTF_8)))
          .retrieve()
          .bodyToFlux(GithubRepo.class);

 **/
