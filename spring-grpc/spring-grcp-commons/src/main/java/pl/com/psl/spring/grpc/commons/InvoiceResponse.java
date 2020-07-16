package pl.com.psl.spring.grpc.commons;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvoiceResponse {
    private int id;
    private String name;
    private List<ProductResponse> products;
    private String sellerName;
    private String buyerName;
    private AddressResponse shippingAddress;
    private LocalDate invoiceDate;
    private int totalPrice;
    private String status;
}
