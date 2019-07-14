package pl.com.psl.spring.cloudcontract.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class ResourceRequest {
    private String stringValue;
}
