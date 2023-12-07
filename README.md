

[![Build Status](https://travis-ci.org/imrenagi/microservice-skeleton.svg?branch=master)](https://travis-ci.org/imrenagi/microservice-skeleton)  [![codecov](https://codecov.io/gh/imrenagi/microservice-skeleton/branch/master/graph/badge.svg)](https://codecov.io/gh/imrenagi/microservice-skeleton)


# synchronous communication in microservices architecture

This project is a simple application within a microservices architecture that utilizes synchronous communication. The architecture comprises two specific microservices,
namely "client" and "car," each equipped with its own dedicated MySQL database. Eureka is employed to streamline service registration,
and the system incorporates an API gateway to efficiently manage incoming requests, 
directing them to the relevant microservices for seamless operation.

## Services

In this project, we will embrace a microservices-based architecture characterized by breaking down an application into small, independent services. At the core of this structure are client microservices, autonomous entities that interact to deliver complete functionality. The API Gateway serves as a centralized entry point, streamlining request management by directing traffic to the relevant microservices. The Eureka discovery server plays a crucial role by enabling each microservice to dynamically register, thus forming a decentralized directory of available services.


![image](https://github.com/RababFhsy/SynchroneCommunicationMS/assets/101474591/7d845883-bd9a-48a5-81e6-d124bfd4d36c)
### Client service
The client service operates on port 8088, and its application name is configured with the following settings
: server.port=8088 and spring.application.name=SERVICE-CLIENT. Additionally, the associated database is named "databaseclients."
it's provide several APIS related to creating and retrieving all client information:

| Method | Path              | Description                                   |
|--------|-------------------|-----------------------------------------------|
| POST   | /clients          | add a client                                  | 
| GET    | /clients          | get list of clients                           |
| GET    | /clients/{id}     | get client by id                              |
| PUT    |/clients/{id}      | modify a client by id                         |
|DELETE  |/clients/{id}      |  delete a client by id                        |


![image](https://github.com/RababFhsy/SynchroneCommunicationMS/assets/101474591/a9f0becd-5eb5-4273-ae65-9290e1297124)

![image](https://github.com/RababFhsy/SynchroneCommunicationMS/assets/101474591/d8d34d30-694c-4c8f-97f0-3e98649ed60e)

### Car service
The client service operates on port 8089, and its application name is configured with the following settings
: server.port=8089 and spring.application.name=SERVICE-VOITURE. Additionally, the associated database is named "databasevoitures."
it's provide several APIS related to creating and retrieving all cars information:

| Method | Path              | Description                                   |
|--------|-------------------|-----------------------------------------------|
| POST   | /voitures        | Create new car                                 |
| GET    | /voitures        | Get All cars informations                      | 
| GET    | /voitures/{Id}   | Get car with id                                |
| GET    |/voitures/client/{Id}| Get cars list of a client


![image](https://github.com/RababFhsy/SynchroneCommunicationMS/assets/101474591/a2b2d34a-e39c-4cbe-982b-31602cee52ec)

![image](https://github.com/RababFhsy/SynchroneCommunicationMS/assets/101474591/580b846b-5eff-4e17-9182-4979a4c0a697)

![image](https://github.com/RababFhsy/SynchroneCommunicationMS/assets/101474591/dd67870d-18cc-4a09-b1d0-b271acff4ee4)

#### Notes
* Each microservice has it's own database and there is no way to access the database directly from other services.
* The services in this project are using MySQL for the persistent storage. In other case, it is also possible for one service 
to use any type of database (SQL or NoSQL).
* Service-to-service communiation is done by using REST API. It is pretty convenient to use HTTP call in Spring
since it provides a simplify HTTP layer service called Feign (discussed later). 

## Infrastructure
Spring Cloud is a really good web framework that we can use for building a microservice infrastructure since it provides 
broad supporting tools such as Load Balancer, Service registry, Monitoring, and Configuration.
![Infrastructure plan](https://miro.medium.com/v2/resize:fit:1400/format:webp/1*43NgBoAW6h-vZTgyknM8xw.png)
### Databases

I use MySQL for persistent data storage for several services in this application. 

To use MYSQL daatabse in  my Spring application, simply define the necessary configurations in application.properties file in both of services client and car service.
```
Car Service:
spring.datasource.url=jdbc:mysql://localhost:3306/databasevoitures
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
Client Service:
spring.datasource.url=jdbc:mysql://localhost:3306/databaseclients
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```



### API Gateway*

As you can see, there are two core services, which expose external API to client. In a real-world systems, this number can grow very quickly as well as whole system complexity. Actualy, hundreds of services might be involved in rendering one complex webpage. 

In theory, a client could make requests to each of the microservices directly. But obviously, there are challenges and limitations with this option, like necessity to know all endpoints addresses, perform http request for each peace of information separately, merge the result on a client side. Another problem is non web-friendly protocols, which might be used on the backend.

Usually a much better approach is to use API Gateway. It is a single entry point into the system, used to handle requests by routing them to the appropriate backend service or by invoking multiple backend services.


```java

@Bean
	DiscoveryClientRouteDefinitionLocator routesDynamique(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){
		return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
	}

```

That means all requests starting with `/SERVICE-CLIENT`  following by `/clients`  in client service or  starting by  `/SERVICE-VOITURE` by following `/voitures`  will be forwarded to car service. There is no hardcoded address, as you can see.

![image](https://github.com/RababFhsy/SynchroneCommunicationMS/assets/101474591/f1fa202b-764c-45de-a567-d9f7bd811d21)

![image](https://github.com/RababFhsy/SynchroneCommunicationMS/assets/101474591/25b49a8d-00f4-4734-80f2-19a1e2429b88)

### Service Discovery *
Another commonly known architecture pattern is Service discovery. It allows automatic detection of network locations for service instances, which could have dynamically assigned addresses because of auto-scaling, failures and upgrades.

The key part of Service discovery is Registry. I use Netflix Eureka in this project. Eureka is a good example of the client-side discovery pattern, when client is responsible for determining locations of available service instances (using Registry server) and load balancing requests across them.

With Spring Boot, you can easily build Eureka Registry with `spring-cloud-starter-eureka-server` dependency, `@EnableEurekaServer` annotation and simple configuration properties.

Now, on application startup, it will register with Eureka Server and provide meta-data, such as host and port, health indicator URL, home page etc. Eureka receives heartbeat messages from each instance belonging to a service. If the heartbeat fails over a configurable timetable, the instance will be removed from the registry.

Also, Eureka provides a simple interface, where you can track running services and number of available instances: `http://localhost:8761`

![image](https://github.com/RababFhsy/SynchroneCommunicationMS/assets/101474591/f10a928f-637b-4501-a050-455a5e7a1d0f)


#### Feign
To exemplify, let's take the car service as a case in point, demonstrating how Feign can be efficiently employed to engage with client microservices and external APIs. This integration streamlines the communication process, allowing us to effortlessly retrieve information about clients from other service.

![image](https://github.com/RababFhsy/SynchroneCommunicationMS/assets/101474591/3ac18abb-d51e-45bf-9254-cb4161668635)



``` java
@FeignClient(name="SERVICE-CLIENT")
    interface ClientService{
        @GetMapping(path="/clients/{id}")
        public Client clientById(@PathVariable(name="id") Long id);
    }
```

- Everything you need is just an interface
- You can share `@RequestMapping` part between Spring MVC controller and Feign methods

  
## How to run all things

Execute the Eureka service first, followed by launching the API Gateway.
Subsequently, initiate XAMPP's Apache and MySQL modules. Once completed, run the client service and the car service separately, each in its own window.

### Before you start
* Install intellij IDE
* Install Xampp.

### Important Endpoint *
* [http://localhost:8888](http://localhost:8888) - Gateway
* [http://localhost:8761](http://localhost:8761) - Eureka Dashboard
* [http://localhost:8088](http://localhost:8088) - Client Service
* [http://localhost:8089](http://localhost:8089) - Car Service







