# Banking system (proof of concept)
This is an example of a basic banking system, the project provides an API which can perform:
- User management (creation, login).
- Wallets creation, deposit and withdrawal.
- Consult wallet balance and movements.
- Transfers between wallets.
- AML and domain validation rules.

The project provides an approach to hexagonal architecture with DDD implementation, based on a real world example. 

The current version implements an H2 database repository via outbound port interfaces implementations. Use cases entry points is provided by inbound rest controller implementations. 
### Stack 
- Kotlin 1.9.2
- SpringBoot 3.2.1
- H2 2.2.2
- OpenApi 2.3.0 
## Api Docs

http://localhost:8080/swagger-ui/index.html

In order to try swagger endpoints you must provide a valid token: 

First, create an user
```
curl --location 'localhost:8080/users/registry' \
--header 'Content-Type: application/json' \
--data-raw '{
"username" : "first@mail.com",
"password" : "password"
}'
```
Then authenticate
```
curl --location 'localhost:8080/login' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic Og==' \
--data-raw '{
    "username" : "first@mail.com", 
    "password" : "password"
}'
```
## Security
Note all api endpoints are secured using spring security but `/users/registry` and `/login`, in order to operate with the remaining endpoints you must include the bearer token provided in the login response

## Postman collection
A complete postman collection is available under `src/main/resources/` 

The bearer token is managed through a script after login request and stored in an environment variable for both initial users (A & B) to make this part transparent 

## Database
This service uses H2 for persistence, for database management visit http://localhost:8080/h2-console
```
user: admin
password: admin 
jdbc-url: jdbc:h2:mem:db
```
