# Banking system (proof of concept)
This is an example of a banking system. The services provides an API which can perform:
- User creation and authentication.
- Wallets creation, deposit and withdrawal.
- Consult wallet balances and movements.
- Transfers between wallets.
- AML and domain validation rules.

The project provides an approach to hexagonal architecture, based on a real world example.

### Stack 
- Kotlin 1.9.2
- SpringBoot 3.2.1
- H2 2.2.2
- OpenApi 2.3.0 
## Api Docs

http://localhost:8080/swagger-ui/index.html

In order to try swagger endpoints you must provide a valid token: 

First, create a new user
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
--data-raw '{
    "username" : "first@mail.com", 
    "password" : "password"
}'
```
## Security
All endpoints are secured with spring-security and JWT. `/users/registry` and `/login` are exceptions.

In order to consume the remaining endpoints a valid bearer token must be provided via authorization header value. 

## Postman collection
A complete postman collection is available under `src/main/resources/` 

The bearer token is managed through a script after login request and stored in an environment variable for both initial users (A & B) to make the authentication transparent and easy to use. 

## Database
This service uses H2 for persistence, for database management visit http://localhost:8080/h2-console
```
user: admin
password: admin 
jdbc-url: jdbc:h2:mem:db
```
