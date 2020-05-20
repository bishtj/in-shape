# In-Shape
This is a sample project to demonstrate a Restful API service for a simple shape repository. 

## Scope
The current scope is limited to square Shape. A Square shape that is axis-aligned, not rotated.

## Acceptance Criteria 
The result of this activity should:
- expose an endpoint to store a new shape
- expose an endpoint to retrieve all stored shapes

The repository should enforce that:
- all shapes have unique names
- no stored shape overlaps any other stored shapes

## Constraints
- store the data into any type of database you wish, but it should not require an actual database server instance running in the target machine.

## Design approach

A restful web service using spring-boot.

Dasebase needs to be integral part of application, so opted to use in-memory database H2 database used and configured to run inside main application.

## Design Options

Every create shape api requires validation against area overlap against all persisted shapes, hence following approach could be taken

### Option1. Fetch All
Fetch all shapes from database on every create shape api.

#### Downside
It is not efficient when the datasize grows and will have poor scalability

#### Upside
It is quick to implement and work well with smaller dataset when scaling is not a requirement

### Option2. Local in-memory cache
Add an in memory cache e.g java based Threadsafe collections (e.g ConcurrentHashMap) to store the Geometry descriptor to avoid fetch all

#### Downside
Forces restriction to have a single application running, as each application will have its own cache 

#### Upside
Efficient in terms of database operation calls


### Option3. Distributed cache (No-Sql database)
Add a distributed No-Sql database Mongo, cassandra, dynamo db etc  

#### Downside
Another application to manage, cost needs to be evaluated 

#### Upside
Fully scalable application

## Engineering Tradeoff

In the interest of time, as this is a demo app and api rate volume is fairly low typically up to 10 api/sec, the current implementation will

be most basic using database to fetch all shapes to validate overlapping. Further improvement can be made as listed in  

Technical debt/ backlog section.  

## Steps to Build

Please follow steps to build.

#### Requirement

* Java JDK8 installed with JAVA_HOME, JDK_HOME environment variable set up 
* MAVEN 3.x installed with MAVEN_HOME, ${MAVEN_HOME}/bin PATH environment variable set up 

#### Build
Navigate to root directory and run below target using make command

```$ make build```
 
## Steps to Run

Please follow steps to run.

#### Requirement

* JRE8 installed with java environment variable set up in PATH 

#### 
Navigate to root directory and run below target using make command

```$ make run```

## Api calls

### Apis

The following apis are supported

* POST /api/v1/shape/square/create
* Payload
     ```
        {                                                                                                        
           "type" : "square",                                                                                 
           "name" : "uniqueName",                                                                              
           "bottomLeftPointX" : 0,                                                           
           "bottomLeftPointY" : 0,                                                           
           "width" : 5                                                                               
        }
  ```

* GET /api/v1/shape/square
* DELETE /api/v1/shape/square


Please follow steps to make api calls.

#### Add square shapes

Run below command with appropriate parameter values (NAME,BOTTOM_LEFT_X,BOTTOM_LEFT_Y,WIDTH) to add shapes. 

```$ make add-square-shape NAME=name1 BOTTOM_LEFT_X=10 BOTTOM_LEFT_Y=10 WIDTH=5```

```$ make add-square-shape NAME=name2 BOTTOM_LEFT_X=300 BOTTOM_LEFT_Y=300 WIDTH=10```

#### GET square shapes

```$ make get-square-shapes```

#### CLEAR all square shapes

```$ make clear-square-shapes```




