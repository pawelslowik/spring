package pl.com.psl.spring.grpc.client.invoice;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.psl.spring.grpc.server.invoice.InvoiceProcessRequest;
import pl.com.psl.spring.grpc.server.invoice.InvoiceProcessResponse;
import pl.com.psl.spring.grpc.server.invoice.InvoiceProcessorGrpc;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    @GetMapping(value = "/{id}")
    public Invoice getInvoice(@PathVariable Integer id) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
        InvoiceProcessResponse invoiceProcessResponse = InvoiceProcessorGrpc.newBlockingStub(managedChannel).processInvoice(InvoiceProcessRequest.newBuilder().setId(id).build());
        return new Invoice(invoiceProcessResponse.getName());
    }
}
