package pl.com.psl.spring.grpc.client.invoice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.psl.spring.grpc.commons.InvoiceResponse;

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

    @GetMapping(value = "/{id}", params = "mode=grpc")
    public InvoiceResponse getInvoiceProcessedWithGrpc(@PathVariable Integer id) {
        return invoiceGrpcService.processInvoice(id);
    }

    @GetMapping(value = "/{id}", params = "mode=rest")
    public InvoiceResponse getInvoiceProcessedWithRest(@PathVariable Integer id) {
        return invoiceRestService.processInvoice(id);
    }
}
