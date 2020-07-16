package pl.com.psl.spring.grpc.client.invoice;

import pl.com.psl.spring.grpc.commons.InvoiceResponse;
import pl.com.psl.spring.grpc.commons.InvoicesRequest;

import java.util.List;

public interface InvoiceService {
    InvoiceResponse processInvoice(int id);
    List<InvoiceResponse> processInvoices(InvoicesRequest request);
}
