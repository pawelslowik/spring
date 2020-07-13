package pl.com.psl.spring.grpc.client.invoice;

import pl.com.psl.spring.grpc.commons.InvoiceResponse;

public interface InvoiceService {
    InvoiceResponse processInvoice(int id);
}
