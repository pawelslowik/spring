package pl.com.psl.spring.grpc.client.invoice;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.com.psl.spring.grpc.commons.InvoiceResponse;
import pl.com.psl.spring.grpc.server.invoice.InvoiceProcessRequest;
import pl.com.psl.spring.grpc.server.invoice.InvoiceProcessResponse;
import pl.com.psl.spring.grpc.server.invoice.InvoiceProcessorGrpc;

import java.time.Duration;
import java.time.Instant;

@Service
@Slf4j
public class InvoiceGrpcService implements InvoiceService {

    private InvoiceProcessorGrpc.InvoiceProcessorBlockingStub invoiceProcessor;

    public InvoiceGrpcService() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
        invoiceProcessor = InvoiceProcessorGrpc.newBlockingStub(managedChannel);
    }

    @Override
    public InvoiceResponse processInvoice(int id) {
        Instant processingStart = Instant.now();
        InvoiceProcessResponse invoiceProcessResponse = invoiceProcessor.processInvoice(InvoiceProcessRequest.newBuilder().setId(id).build());
        Instant processingEnd = Instant.now();
        log.info("Processing time:{} ms", Duration.between(processingStart, processingEnd).toMillis());
        return new InvoiceResponse(invoiceProcessResponse.getId(), invoiceProcessResponse.getName(), invoiceProcessResponse.getStatus());
    }
}
