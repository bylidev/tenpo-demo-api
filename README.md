# Demo API  
this is an authenticated dummy api, made for [tenpo](tenpo.cl).

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Security](https://spring.io/projects/spring-security)
* [Spring Retry](https://docs.spring.io/spring-batch/docs/current/reference/html/retry.html)
* [Swagger](http://localhost:8080/swagger-ui/index.html#/demo-api)
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

```
curl --location --request POST 'http://{{uri}}/api/v1/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
  "email": "tenpo@tenpo.cl",
  "username": "user",
  "password": "user1234",
  "roleList": [
    "ROLE_USER"
  ]
}
```

# Contributing
Bug reports and pull requests are welcome :)
