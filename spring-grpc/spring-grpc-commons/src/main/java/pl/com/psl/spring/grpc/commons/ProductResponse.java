package pl.com.psl.spring.grpc.commons;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {
    private String name;
    private int amount;
    private int price;
}
