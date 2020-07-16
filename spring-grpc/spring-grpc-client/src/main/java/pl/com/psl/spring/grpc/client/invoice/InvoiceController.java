package pl.com.psl.spring.grpc.client.invoice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.com.psl.spring.grpc.commons.InvoiceResponse;
import pl.com.psl.spring.grpc.commons.InvoicesRequest;

import java.util.List;

@RestController
@RequestMapping("/invoices")
@Slf4j
public class InvoiceController {

    private final InvoiceRestService invoiceRestService;
    private final InvoiceGrpcService invoiceGrpcService;

    @Autowired
    public InvoiceController(InvoiceRestService invoiceRestService, InvoiceGrpcService invoiceGrpcService) {
        this.invoiceRestService = invoiceRestService;
        this.invoiceGrpcService = invoiceGrpcService;
    }

    @GetMapping(value = "/{id}", params = "mode=grpc", produces = MediaType.APPLICATION_JSON_VALUE)
    public InvoiceResponse getInvoiceProcessedWithGrpc(@PathVariable Integer id) {
        return invoiceGrpcService.processInvoice(id);
    }

    @PostMapping(params = "mode=grpc", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InvoiceResponse> getInvoicesProcessedWithGrpc(@RequestBody InvoicesRequest request) {
        return invoiceGrpcService.processInvoices(request);
    }

    @GetMapping(value = "/{id}", params = "mode=rest", produces = MediaType.APPLICATION_JSON_VALUE)
    public InvoiceResponse getInvoiceProcessedWithRest(@PathVariable Integer id) {
        return invoiceRestService.processInvoice(id);
    }

    @PostMapping(params = "mode=rest", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InvoiceResponse> getInvoicesProcessedWithRest(@RequestBody InvoicesRequest request) {
        return invoiceRestService.processInvoices(request);
    }
}
