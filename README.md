# anemic-api

This is a sample project to demonstrate the concept of an anemic API, the problems it causes and how to fix it.

## Intended architecture

The following diagram describes the intended architecture of the system being developed.

![Intended architecture](/Documentation/Intended%20architecture.drawio.png)

This architecture has 3 layers to separate concerns:
 - **Database** - For long term data storage
 - **Back end** - For the business logic of the application secured on the server
 - **User interface** - For the presentation of the application in the client's web browser

## The anemic API

Run the Spring Boot application in the **main** branch of the repository:

Run the application:
- `.\gradlew bootRun` Linux
- `.\gradlew.bat bootRun` Windows
- Navigate to http://localhost:9080/actuator/swagger-ui/index.html
- Specifically look at the endpoint:
    `PUT /backlog-items/{backlog-item-id}`

The stated purpose of this endpoint is to "Update an existing backlog item".

But it does not make it clear why an end user would like to update a backlog item.

There are in fact many reasons why an end user might want to update a backlog item.
For example:
- Update the name or description of the backlog item.
- Update User strory of the backlog item.
- Estimate the backlog item and assign story points to it.
- Commit the backlog item to a sprint.
- Change the state of the backlog item to IN_PROGRESS.
- Change the state of the backlog item to DONE.
- etc.

We will argue that these are the "real" requirements for the system.
The user interface will have to make it possible to do these things.
Thus, the business logic for the application will live in the User Interface layer.

The back end will simply be exposing CRUD (Create, Read, Update and Delete) functionality
for the tables in the database. The back end will also do some data validation.

The following diagram describes the actual architecture we end up building.

![Anemic API](/Documentation/Anemic%20API.drawio.png)

This architecture is not ideal due to a number of technical reasons:
- The business logic now lives in the client's browser and not on the server, which is less secure.
- Someone with technical skills could figure out how to modify or bypass the front end business logic and call the 
  back end API in ways that are not expected. This could lead to invalid data in the database. 
- If we ever want to add a second user interface like a native mobile application, then this new user interface 
  would have to implement the same business logic as the original user interface.
- As the business logic lives in the UI layer, concurrency issues are more likely to occur, and it is hard to resolve 
  these issues in the back end.
- It is hard for a developer of a generic API endpoint like an update to identify all the business scenarios that
  would call the endpoint and to determine the data validations that apply to each scenario.
- Since there are multiple business scenarios per API endpoint, each with successful and unsucessful test scenarios,
  we have many more test scenarios that are harder to identify and write.
- If we have multiple UI screens calling the same API endpoint, then modifying the API endpoint to support a change in
  one screen could break the other screens dependent on the endpoint.

There are also some financial and managerial issues caused by anemic APIs:
- As already stated that generic endpoints supporting multiple screens are more complex, it becomes difficult for
  developers to accurately estimate the effort to complete the development work.
- It also becomes more difficult to complete the development of such endpoints in a single Agile iteration or 
  SCRUM sprint.
- These endpoints often need to be revisited if a new screen is discovered that needs to call the endpoint.
- It makes it more difficult to make user stories that are true vertical slices through your architecture.
- In general, it makes it more difficult for teams to estimate the total effort required to complete a product/project
  and more difficult to measure a team's velocity. Without this important data we cannot calculate when will the
  project/product be done and how much will it cost.

## The non-anemic API

The solution to an anemic API is to bring the business logic back into the back end of our application and as close to
the data model as possible.

When looking at the code of an anemic API, you will most likely find an anemic domain model. A concept familiar to 
those aware of Domain Driven Design (DDD). We should in fact bring our business logic into the domain model at the core
of our application modules and make technical / infrastructure concerns such as API presentation and database persistence dependent 
on the domain model, and not the other way around.

![Domain Driven Design](/Documentation/DDD.drawio.png)

This differs from the more common layered architecture where the business logic layer (domain) is dependent on the 
data access layer (infrastructure).

![Layered architecture](/Documentation/Layered%20architechture.drawio.png)

We have refactored the code of the example application to use domain driven design concepts and
we now have a non-amenic domain model and API. It should be noted that we have used Spring Modulith to ensure our
architecture rules are followed in the back end. 

Switch to the branch **non-anemic**:
`git checkout non-anemic`

Run the application:
- `.\gradlew bootRun` Linux
- `.\gradlew.bat bootRun` Windows
- Navigate to http://localhost:9080/actuator/swagger-ui/index.html
- Specifically look at the new endpoints for the backlog.
