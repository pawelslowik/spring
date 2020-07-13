1. cd spring-grpc-commons
2. mvn clean install
3. cd spring-grpc-server
4. mvn clean install and run main class
5. cd spring-grpc-client
6. mvn clean install and run main class
7. HTTP GET /invoices/{id}?mode=grcp to observe GRPC interaction between client and server
8. HTTP GET /invoices/{id}?mode=rest to observe REST interaction between client and server
__