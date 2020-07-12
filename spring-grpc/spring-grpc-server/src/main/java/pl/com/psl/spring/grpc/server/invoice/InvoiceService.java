package pl.com.psl.spring.grpc.server.invoice;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import pl.com.psl.spring.grpc.server.invoice.InvoiceProcessorGrpc.InvoiceProcessorImplBase;

@GRpcService
public class InvoiceService extends InvoiceProcessorImplBase {
    @Override
    public void processInvoice(InvoiceProcessRequest request, StreamObserver<InvoiceProcessResponse> responseObserver) {
        responseObserver.onNext(InvoiceProcessResponse.newBuilder().setName("Processed " + request.getId()).build());
        responseObserver.onCompleted();
    }
}
