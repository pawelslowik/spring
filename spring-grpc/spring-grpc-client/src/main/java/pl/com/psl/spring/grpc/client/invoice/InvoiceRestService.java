package pl.com.psl.spring.grpc.client.invoice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.com.psl.spring.grpc.commons.InvoiceRequest;
import pl.com.psl.spring.grpc.commons.InvoiceResponse;
import pl.com.psl.spring.grpc.commons.InvoicesRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
public class InvoiceRestService implements InvoiceService {

    private RestTemplate invoiceRestTemplate = new RestTemplate();
    @Value("${invoice.server.host}")
    private String serverHost;
    @Value("${invoice.server.port.rest}")
    private int serverPort;

    @Override
    public InvoiceResponse processInvoice(int id) {
        Instant processingStart = Instant.now();
        InvoiceResponse invoice = invoiceRestTemplate.postForObject("http://" + serverHost + ":" + serverPort + "/invoices", new InvoiceRequest(id), InvoiceResponse.class, serverHost, serverPort);
        Instant processingEnd = Instant.now();
        log.info("Processing time:{} ms", Duration.between(processingStart, processingEnd).toMillis());
        return invoice;
    }

    @Override
    public List<InvoiceResponse> processInvoices(InvoicesRequest request) {
        Instant processingStart = Instant.now();
        InvoiceResponse[] invoices = invoiceRestTemplate.postForObject("http://" + serverHost + ":" + serverPort + "/invoices?projection=list", request, InvoiceResponse[].class);
        Instant processingEnd = Instant.now();
        log.info("Processing time:{} ms", Duration.between(processingStart, processingEnd).toMillis());
        return ofNullable(invoices).map(Arrays::asList).orElseGet(Collections::emptyList);
    }
}
