package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method('PUT')
        url('/resources/0')
        body([
                "stringValue": $(regex("[a-zA-z]+"))
        ])
        headers {
            contentType(applicationJsonUtf8())
        }
    }
    response {
        status OK()
    }
}