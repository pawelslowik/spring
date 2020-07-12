package pl.com.psl.spring.grpc.server.invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvoiceServer {
    public static void main(String[] args) {
        SpringApplication.run(InvoiceServer.class, args);
    }
}
