
# Spring Boot Unit, Integration and Acceptance Testing examples

This project demonstrates the unit, integration and acceptance testing of Spring Boot Rest API. 

- Uses mockMvc to test the rest controller for query parameter, default value of query parameter, validation violation for primitive int query parameter, validation of custom exception thrown by the rest controller.
- Utilises MockMvcResultMatchers, validates json array using jsonPath


## Lessons Learned

- Unit, Integration and Acceptance testing of rest api method (WelcomeControllerUnitTest.java, DiscountCalculatorControllerIntegrationTest.java)
- In order to user @Min, @Max for rest controller method argument validation, added the dependency "spring-boot-starter-validation"
- When hitting the endpoint via mockMvc, provide the full endpoint to mockMvc.
- jsonPath usage to validate the json array, json object.
- Uses ResponseEntity to send back the response in positive as well as in Exception scenario.

## Start Spring Boot application
(Worked on Spring Boot 3.3.3 in Sep 2024)  
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=test --server.port=9092 --myapp.name=Prod"  

(Worked on Spring Boot 3.3.3 in Sep 2024)  
Using java:  
java -jar -Dspring.profiles.active=test ./target/spring-boot-testing-demo-0.0.1-SNAPSHOT.jar  
java -jar ./target/spring-boot-testing-demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=test  
java -jar ./target/spring-boot-testing-demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=test --server.port=8089  

