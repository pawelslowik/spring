package pl.com.psl.spring.cloudcontract.producer;

import lombok.Value;

@Value
class Resource {
    private long id;
    private String stringValue;
}
