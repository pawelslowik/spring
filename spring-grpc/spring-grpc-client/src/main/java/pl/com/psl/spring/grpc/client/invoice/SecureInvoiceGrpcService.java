package pl.com.psl.spring.grpc.client.invoice;

import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Profile({"grpc-secure"})
public class SecureInvoiceGrpcService extends InvoiceGrpcService {

    @Autowired
    public SecureInvoiceGrpcService(
            @Value("${invoice.server.host}") String serverHost,
            @Value("${invoice.server.port.grpc}") int serverPort,
            @Value("file:${invoice.server.cert-chain}") Resource certChain) throws IOException {
        super(NettyChannelBuilder.forAddress(serverHost, serverPort)
                .useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient()
                        .trustManager(certChain.getInputStream())
                        .build())
                .build());
    }
}
