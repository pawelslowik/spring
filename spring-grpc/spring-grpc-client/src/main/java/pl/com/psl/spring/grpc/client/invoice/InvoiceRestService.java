package pl.com.psl.spring.grpc.client.invoice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.com.psl.spring.grpc.commons.InvoiceRequest;
import pl.com.psl.spring.grpc.commons.InvoiceResponse;

import java.time.Duration;
import java.time.Instant;

@Service
@Slf4j
public class InvoiceRestService implements InvoiceService {

    private RestTemplate invoiceRestTemplate = new RestTemplate();

    @Override
    public InvoiceResponse processInvoice(int id) {
        Instant processingStart = Instant.now();
        InvoiceResponse invoice = invoiceRestTemplate.postForObject("http://localhost:8081/invoices", new InvoiceRequest(id), InvoiceResponse.class);
        Instant processingEnd = Instant.now();
        log.info("Processing time:{} ms", Duration.between(processingStart, processingEnd).toMillis());
        return invoice;
    }
}
