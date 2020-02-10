## AZ-Multithreading-Example
In this project I'm using Spring Boot with JPA and H2 in-memory DB to explain Asynchronous/Multithreading in Spring Boot application. I'll take multiple CSV files as input to save user data into H2 DB then fetch user data from H2 DB Asynchronous way.


#### Step by step guide:
* In Java8 `CompletableFuture` introduced for none-blocking/Asychronous calls.
* `@EnableAsync` annotation is used for enable Asynchronous/Multithreading in Spring boot (To run methods in background).
* Now we need to setup ThreadPoolTaskExecutor related stuff.
		
	@Bean
	public Executor taskExecutor() {		
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();		
		threadPoolTaskExecutor.setCorePoolSize(2);
		threadPoolTaskExecutor.setMaxPoolSize(2);
		threadPoolTaskExecutor.setQueueCapacity(10);
		threadPoolTaskExecutor.setThreadNamePrefix("Az-ThreadPoolTaskExecutor-");
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;
	}

* If we don't configure above `ThreadPoolTaskExecutor` Spring boot create `SimpleThreadPoolTaskExecutor`. Better to create your custom Executor.
* In Service class some methods I have added `@Async` that makes those methods to run Asynchronously/background.
* Install postman to your machine and for post requests (chose field and type as file and select CSV file at least two.
* How to pass CSV files as input to RESTful service please check [youtube video tutorial](https://youtu.be/3rJBLFA95Io?t=1203) 
* For generating file please check reference material.

#### EndPoints:
* POST request for input multiple files from post man [http://localhost:8081/users](http://localhost:8081/users)
* GET request for normal output [http://localhost:8081/users](http://localhost:8081/users)
* GET request for Asynchronous call output [http://localhost:8081/usersAsync](http://localhost:8081/usersAsync)

#### Extra Things:
* I have used Builder Pattern by [Project Lombok](https://projectlombok.org/features/Builder)
	
		User user = 	User.builder()
    				.name(data[0])
    				.email(data[1])
    				.gender(data[2])
    				.build();   

#### Reference Material:
* [Spring Official](https://spring.io/guides/gs/async-method/)
* [Youtube video tutorial](https://www.youtube.com/watch?v=3rJBLFA95Io)
* [CSV File Generator](https://mockaroo.com/)
