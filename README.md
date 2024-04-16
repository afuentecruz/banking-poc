# Banking system (proof of concept)
This is an example of a basic banking system, wich includes 
- user managements
- wallets creation, deposit, withdraw 
- wallet balance and movements
- transfers between wallets

The project provides an approach to hexagonal architecture with DDD implementation, based on a real world example. 
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
Note all api endpoints are secured using spring security except `/users/registry` and `/login`, in order to operate with the remaining endpoints you must include the bearer token provided in the login response

## Postman collection
A complete postman collection is available under `src/test/resources/` 

The bearer token is managed through a script after login request and stored in an environment variable for both initial users (A & B) to make this part transparent 

## Database
This service uses H2 for persistence, for data visualization visit http://localhost:8080/h2-console
```
user: admin
password: admin 
jdbc-url: jdbc:h2:mem:db
```
