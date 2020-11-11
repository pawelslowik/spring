package pl.com.psl.spring.grpc.server.invoice;

import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import pl.com.psl.spring.grpc.server.invoice.InvoiceProcessorGrpc.InvoiceProcessorImplBase;

import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@GRpcService
public class InvoiceGrpcService extends InvoiceProcessorImplBase {
    @Override
    public void processInvoice(InvoiceProcessRequest request, StreamObserver<InvoiceProcessResponse> responseObserver) {
        int id = request.getId();
        responseObserver.onNext(process(id));
        responseObserver.onCompleted();
    }

    @Override
    public void processInvoices(InvoicesProcessRequest request, StreamObserver<InvoiceProcessResponse> responseObserver) {
        request.getIdsList().forEach(id -> responseObserver.onNext(process(id)));
        responseObserver.onCompleted();
    }

    @Override
    public void bulkProcessInvoices(InvoicesProcessRequest request, StreamObserver<InvoicesProcessResponse> responseObserver) {
        responseObserver.onNext(InvoicesProcessResponse.newBuilder()
                .addAllResponses(
                        request.getIdsList().stream().map(this::process).collect(toList())
                )
                .build());
        responseObserver.onCompleted();
    }

    private InvoiceProcessResponse process(int id) {
        List<Product> products = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> Product.newBuilder()
                        .setAmount(i)
                        .setName("Product " + i)
                        .setPrice(i)
                        .build()
                ).collect(toList());

        Instant timestamp = Instant.now();
        return InvoiceProcessResponse.newBuilder()
                .setId(id)
                .setName("Invoice " + id)
                .setStatus("Processed")
                .setBuyerName("Buyer " + id)
                .setSellerName("Seller " + id)
                .setShippingAddress(
                        Address.newBuilder()
                                .setCity("City " + id)
                                .setPostalCode("00-" + id)
                                .setStreet("Street " + id)
                                .build()
                )
                .addAllProduct(products)
                .setTotalPrice(products.stream().mapToInt(p -> p.getAmount() * p.getPrice()).sum())
                .setInvoiceDate(Timestamp.newBuilder()
                        .setSeconds(timestamp.getEpochSecond())
                        .setNanos(timestamp.getNano())
                        .build())
                .build();
    }
}
