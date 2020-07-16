package pl.com.psl.spring.grpc.client.invoice;

import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.com.psl.spring.grpc.commons.AddressResponse;
import pl.com.psl.spring.grpc.commons.InvoiceResponse;
import pl.com.psl.spring.grpc.commons.InvoicesRequest;
import pl.com.psl.spring.grpc.commons.ProductResponse;
import pl.com.psl.spring.grpc.server.invoice.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static java.time.Instant.ofEpochSecond;
import static java.time.LocalDate.ofInstant;
import static java.time.ZoneId.of;
import static java.util.stream.Collectors.toList;

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
        return toInvoiceResponse(invoiceProcessResponse);
    }

    @Override
    public List<InvoiceResponse> processInvoices(InvoicesRequest request) {
        Instant processingStart = Instant.now();
        InvoicesProcessResponse invoicesProcessResponse = invoiceProcessor.processInvoices(
                InvoicesProcessRequest.newBuilder()
                        .addAllIds(request.getIds())
                        .build());
        Instant processingEnd = Instant.now();
        log.info("Processing time:{} ms", Duration.between(processingStart, processingEnd).toMillis());
        return invoicesProcessResponse
                .getResponsesList()
                .stream()
                .map(this::toInvoiceResponse)
                .collect(toList());
    }

    private InvoiceResponse toInvoiceResponse(InvoiceProcessResponse invoiceProcessResponse) {
        List<ProductResponse> products = invoiceProcessResponse.getProductList().stream().map(p -> ProductResponse.builder()
                .price(p.getPrice())
                .amount(p.getAmount())
                .name(p.getName())
                .build()
        ).collect(toList());

        Timestamp timestamp = invoiceProcessResponse.getInvoiceDate();
        return InvoiceResponse.builder()
                .id(invoiceProcessResponse.getId())
                .name(invoiceProcessResponse.getName())
                .status(invoiceProcessResponse.getStatus())
                .buyerName(invoiceProcessResponse.getBuyerName())
                .sellerName(invoiceProcessResponse.getSellerName())
                .shippingAddress(
                        AddressResponse.builder()
                                .city(invoiceProcessResponse.getShippingAddress().getCity())
                                .postalCode(invoiceProcessResponse.getShippingAddress().getPostalCode())
                                .street(invoiceProcessResponse.getShippingAddress().getStreet())
                                .build()
                )
                .products(products)
                .invoiceDate(ofInstant(ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()), of("UTC")))
                .totalPrice(invoiceProcessResponse.getTotalPrice())
                .build();
    }
}
