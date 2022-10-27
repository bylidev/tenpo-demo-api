# Demo API  
this is an authenticated dummy api, made for [tenpo](tenpo.cl).

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Security](https://spring.io/projects/spring-security)
* [Spring Retry](https://docs.spring.io/spring-batch/docs/current/reference/html/retry.html)
* [Spring Cache](https://spring.io/guides/gs/caching/)
* [Swagger](http://localhost:8080/swagger-ui/index.html#/demo-api)
* [Spring AOP](https://docs.spring.io/spring-framework/docs/2.5.5/reference/aop.html)
* [AspectJ](https://www.baeldung.com/aspectj)
* [Tenpo Auth Service](https://github.com/igloar96/tenpo-auth-service)

# Requirements
For building and running the application you need:
 - JDK 17
 - Maven 
 - Docker (optional)

# Running the application locally
There are several ways to run this application on your local machine. One way is to execute the main method in the cl.tenpo.evaluation.demo.api.DemoApplication class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:

``` 
   mvn spring-boot:run
```

# Deploying the application with docker
1) Should build the image with following cmd
`` docker build . -t tenpo-demo-api:1.0.0-beta``

2) List images
   `` docker image ls``
3) Run the image ``docker run -p 8080:8080 tenpo-demo-api:1.0.0-beta``

# How to use?
1) Get an authorization token from [tenpo auth service](https://github.com/igloar96/tenpo-auth-service).
2) Add the bearer token to request header.
3) Operate with the following POST request:
```
curl --location --request POST 'localhost:8080/demo-api/api/v1/demo/operation' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NjY4MzYzNDQsImV4cCI6MTY2NjgzOTk0NH0.1OMtMHERM8XYoprwkTjSWM7qC-Gjxn-aLXgYjDBSgwPTtYD-HVzUTB8QFjV97_Nvo6g7YgCWB935UEN_nmAlbA' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=DCBD13F723D11756E7A3CC4806D8D125' \
--data-raw '{
    "one":5546,
    "two":"2"
}'
```
4) Fetch auditory 
```
curl --location --request GET 'localhost:8080/demo-api/api/v1/auditory?page=0&size=300' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NjY4MzYzNDQsImV4cCI6MTY2NjgzOTk0NH0.1OMtMHERM8XYoprwkTjSWM7qC-Gjxn-aLXgYjDBSgwPTtYD-HVzUTB8QFjV97_Nvo6g7YgCWB935UEN_nmAlbA' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=DCBD13F723D11756E7A3CC4806D8D125' \
--data-raw '{
    "one":"5",
    "two":"2"
}'
```
# Contributing
Bug reports and pull requests are welcome :)
