package pl.com.psl.spring.grpc.server.invoice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.psl.spring.grpc.commons.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.IntStream;

import static java.time.Instant.now;
import static java.time.LocalDate.ofInstant;
import static java.time.ZoneId.of;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public InvoiceResponse processInvoice(@RequestBody InvoiceRequest request) {
        return process(request.getId());
    }

    @PostMapping(params ="projection=list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InvoiceResponse> processInvoices(@RequestBody InvoicesRequest request) {
        return request.getIds().stream().map(this::process).collect(toList());
    }

    private InvoiceResponse process(int id) {
        List<ProductResponse> products = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> ProductResponse.builder()
                        .amount(i)
                        .name("Product " + i)
                        .price(i)
                        .build()
                ).collect(toList());

        return InvoiceResponse.builder()
                .id(id)
                .name("Invoice " + id)
                .status("Processed")
                .buyerName("Buyer " + id)
                .sellerName("Seller " + id)
                .shippingAddress(
                        AddressResponse.builder()
                                .city("City " + id)
                                .postalCode("00-" + id)
                                .street("Street " + id)
                                .build()
                )
                .products(products)
                .totalPrice(products.stream().mapToInt(p -> p.getAmount() * p.getPrice()).sum())
                .invoiceDate(ofInstant(now(), of("UTC")))
                .build();
    }
}
