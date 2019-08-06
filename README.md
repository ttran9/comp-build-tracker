# Overview

- I am making this web application to sharpen my skills using Java 8, Spring Boot, Spring Security, ReactJS, and JWT Authentication.
- Another reason I am making this web app is because I want a place where I can look for reference when I am planning on building
a new desktop. Basically, whenever someone asks me for help I always like to know what a certain part was priced at or possibly
how much an entire build was at a certain time.
    - I just need a place where I can refer to because I don't see myself remembering exact details for builds for others that
    I have helped. I can safely say that the last two builds I had that I can recall a good number of small details but
    it will get more difficult for me to do so with others.
- For building the react app into a compressed JavaScript file I realize that there is an alternative approach which is to use
a plugin using maven but for now I will be using a shell file which is just a set of commands that uses webpack to build
a production ready JavaScript file and moves it to the back-end resources directory along with the index.html which references
the React content and serves this web application.


# Tests
- I have added tests using JUnit. While doing this I decided only to test the controllers and services because I do not believe
testing individual domain projects is necessary as these are used by the controller and/or the services.
- When running the tests, I have designed them to pass with an in-memory h2 database that creates the schema upon start-up
and then drops the tables after the application is no longer running.
    - One limitation that I have noticed is that I must be running the application in order to pass the tests. For example,
    since I am not running Spring MVC I cannot run something similar to a Spring MVC test environment and have the tests
    inside of the controller pass as these require the back-end API to be running and must be able to hit those endpoints.

# Notes for the specific branch (computer-build-details-frontend)

- Both the frontend and backend are done as of this branch. The basic functionality is done, such as being able to
perform CRUD operations on a computer build and its details. 
- There are JUnit tests for the services and the controllers required for the user to use the api endpoints.
- At this point there are still some issues I would like to address.
    - I will work on the appearance of the UI.
    - I would like to refactor code in the back-end and front-end.
    - I will also refactor/adjust some of the tests as I feel they do not test more "complicated" cases but instead they
    were testing very basic functionality. A bug my former uniqueIdentifier generation logic had was that I did not test 
    what would happen if the user were to delete a computer detail (computer part, build note, overclocking note, direction, or purpose, but not the last one) and 
    then were to add a new computer detail then this would cause a DataIntegrityViolationException because the uniqueIdentifier
    generated would match the last computer detail. I have resolved this issue by adding in a column to track the number
    of insertions of a specific computer detail (the number of directions or computer parts) for each individual computer
    build to ensure that the uniqueIdentifiers generated should be unique.
    
# TODO:
- Refactor tests and/or possibly add tests. (on-going)   
- Refactor (repetitive) code in the front-end/react app.  

# Progress (master branch)
    - For the progress once I have crossed off the item that means it has been merged into the master branch.
    - <strike>Merged in user login, registration, password change, and account activation features.</strike>
- <strike>Add support to be able to to create, delete/remove, and edit a ComputerBuild and write automated tests for this functionality.</strike>
- <strike>Add backend functionality to be able to add, delete, and update ComputerParts for a ComputerBuild.</strike>
- <strike>Add backend functionality to be able to add, delete, and update Directions for a ComputerBuild.</strike> 
- <strike>Add backend functionality to be able to add, delete, and update Overclocking Notes for a ComputerBuild.</strike> 
- <strike>Add backend functionality to be able to add, delete, and update Other Notes for a ComputerBuild.</strike> 
- <strike>Add frontend functionality to be able to add, delete, and update ComputerParts for a ComputerBuild.</strike>
- <strike>Add frontend functionality to be able to add, delete, and update Directions for a ComputerBuild.</strike> 
- <strike>Add frontend functionality to be able to add, delete, and update Overclocking Notes for a ComputerBuild.</strike> 
- <strike>Add frontend functionality to be able to add, delete, and update Other Notes for a ComputerBuild.</strike> 
- <strike>Refactor the CustomResponseEntityExceptionHandler to not use so many @ExceptionHandler repetitive methods.</strike>
- <strike>Refactor the MapValidationErrorServiceImpl and the controllers that utilize it to throw an exception that
will be handled by CustomResponseEntityExceptionHandler.</strike>
- <strike>Modify the appearance of the computer build details to be more "presentable."</strike>
- <strike>Refactor (repetitive) code in the back-end/spring boot backend app.</strike>

