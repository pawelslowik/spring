package pl.com.psl.spring.grpc.client.invoice;

import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.com.psl.spring.grpc.commons.AddressResponse;
import pl.com.psl.spring.grpc.commons.InvoiceResponse;
import pl.com.psl.spring.grpc.commons.InvoicesRequest;
import pl.com.psl.spring.grpc.commons.ProductResponse;
import pl.com.psl.spring.grpc.server.invoice.InvoiceProcessRequest;
import pl.com.psl.spring.grpc.server.invoice.InvoiceProcessResponse;
import pl.com.psl.spring.grpc.server.invoice.InvoiceProcessorGrpc;
import pl.com.psl.spring.grpc.server.invoice.InvoicesProcessRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.time.Instant.ofEpochSecond;
import static java.time.LocalDate.ofInstant;
import static java.time.ZoneId.of;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.toList;

@Service
@Profile({"!grpc-secure"})
@Slf4j
public class InvoiceGrpcService implements InvoiceService {

    private InvoiceProcessorGrpc.InvoiceProcessorBlockingStub invoiceProcessor;
    private InvoiceProcessorGrpc.InvoiceProcessorStub invoiceAsyncProcessor;

    @Autowired
    public InvoiceGrpcService(
            @Value("${invoice.server.host}") String serverHost,
            @Value("${invoice.server.port.grpc}") int serverPort) {
        this(NettyChannelBuilder.forAddress(serverHost, serverPort).usePlaintext().build());
    }

    InvoiceGrpcService(ManagedChannel managedChannel) {
        invoiceProcessor = InvoiceProcessorGrpc.newBlockingStub(managedChannel);
        invoiceAsyncProcessor = InvoiceProcessorGrpc.newStub(managedChannel);
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
    @SneakyThrows
    public List<InvoiceResponse> processInvoices(InvoicesRequest request) {
        List<InvoiceResponse> responses = new ArrayList<>(request.getIds().size());
        final CountDownLatch finishLatch = new CountDownLatch(1);
        Instant processingStart = Instant.now();
        invoiceAsyncProcessor.processInvoices(InvoicesProcessRequest.newBuilder()
                .addAllIds(request.getIds())
                .build(), new StreamObserver<>() {
            @Override
            public void onNext(InvoiceProcessResponse response) {
                responses.add(toInvoiceResponse(response));
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Received error", throwable);
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                Instant processingEnd = Instant.now();
                log.info("Processing time:{} ms", Duration.between(processingStart, processingEnd).toMillis());
                finishLatch.countDown();
            }
        });
        finishLatch.await(10, SECONDS);
        return responses;
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
