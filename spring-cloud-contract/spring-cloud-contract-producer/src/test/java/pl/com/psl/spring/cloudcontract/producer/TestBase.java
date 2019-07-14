package pl.com.psl.spring.cloudcontract.producer;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;

public class TestBase {

    @Before
    public void setup() {
        ProducerController controller = new ProducerController();
        controller.post(new ResourceRequest("anything"));
        RestAssuredMockMvc.standaloneSetup(controller);
    }
}
