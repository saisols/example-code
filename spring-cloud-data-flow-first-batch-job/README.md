# Spring Cloud Data Flow First Batch Job

## Blog post

There is a blog post that explains how to run this batch job in Spring Cloud Data Flow at 
https://blog.mikeski.net/development/java/spring-cloud-data-flow-custom-task/

## Running in standalone mode

To run this app standalone, we need to disable the database connection, so use this:

    mvn spring-boot:run -Dspring-boot.run.profiles=standalone
    
We need to enable the database configuration to work in the SCDF framework, so it's on by default.

Running the tests will also run the batch job:

    mvn test
    
