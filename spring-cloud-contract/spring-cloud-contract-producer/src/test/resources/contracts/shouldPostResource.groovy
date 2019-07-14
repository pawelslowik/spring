package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method('POST')
        url('/resources')
        body([
                "stringValue": $(regex("[a-zA-z]+"))
        ])
        headers {
            contentType(applicationJsonUtf8())
        }
    }
    response {
        status CREATED()
    }
}