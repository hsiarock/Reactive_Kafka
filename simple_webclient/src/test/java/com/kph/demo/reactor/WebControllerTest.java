package com.kph.demo.reactor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
//import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(WebController.class)
class WebControllerTest {

//    @Autowired
//    private MockMvc mvc;
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void getTweetsBlockingSize() {

//        WebTestClient client =
//                MockMvcWebTestClient.bindToController(new TestController()).build();

//        RequestBuilder request = MockMvcRequestBuilders.get("/tweets-blocking");
//        WebController webController = new WebController();
//        Flux<Tweet> reslt = webController.getTweetsBlocking();
//        assertEquals (reslt.count(), 3);

//        WebTestClient webTestClient =
//                WebTestClient.bindToController(new WebController()).build();

//        Employee employee = Employee.builder().id(1).city("delhi").age(23).name("ABC").build();
//        Mono<Employee> employeeMono = Mono.just(employee);
//
//        when(employeeService.getEmployeeById(1)).thenReturn(employeeMono);

        webTestClient.get().uri("/slow-service-tweets")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Tweet.class).hasSize(3);


    }
}
