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

This architecture is not ideal because:
- The business logic now lives in the client's browser and not on the server, which is less secure.
- Someone with technical skills could figure out how to modify or bypass the front end business logic and call the back end API in ways that are not expected. This could lead to invalid data in the database. 
- If we ever want to add a second user interface like a native mobile application, then this new user interface would have to implement the same business logic as the original user interface.

## The non-anemic API

Switch to the branch **non-anemic**:
`git checkout non-anemic`

Run the application:
- `.\gradlew bootRun` Linux
- `.\gradlew.bat bootRun` Windows
- Navigate to http://localhost:9080/actuator/swagger-ui/index.html
- Specifically look at the new endpoints for the backlog.

