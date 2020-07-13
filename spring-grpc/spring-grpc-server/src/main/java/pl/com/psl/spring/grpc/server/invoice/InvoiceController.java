package pl.com.psl.spring.grpc.server.invoice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.psl.spring.grpc.commons.InvoiceRequest;
import pl.com.psl.spring.grpc.commons.InvoiceResponse;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public InvoiceResponse processInvoice(@RequestBody InvoiceRequest request) {
        return new InvoiceResponse(request.getId(), "Invoice " + request.getId(), "Processed");
    }

}
