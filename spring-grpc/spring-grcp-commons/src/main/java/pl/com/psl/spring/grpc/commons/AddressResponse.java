package pl.com.psl.spring.grpc.commons;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddressResponse {
    private String street;
    private String city;
    private String postalCode;
}
