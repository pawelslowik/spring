package pl.com.psl.spring.cloudcontract.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureStubRunner(ids = {"pl.com.psl.spring:spring-cloud-contract-producer:+:stubs:8080"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class ProviderIntegrationTest {

    private static final String RESOURCES_ENDPOINT = "http://localhost:8080/resources";

    @Test
    public void shouldGetResource() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Resource> responseEntity = restTemplate.getForEntity(RESOURCES_ENDPOINT +"/0", Resource.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        Resource resource = responseEntity.getBody();
        assertThat(resource.getId()).isEqualTo(0);
        assertThat(resource.getStringValue()).isEqualTo("anything");
    }

    @Test
    public void shouldPostResource() {
        RestTemplate restTemplate = new RestTemplate();
        ResourceRequest request = new ResourceRequest("anything");
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(RESOURCES_ENDPOINT, request, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNull();
    }
}
