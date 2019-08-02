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

# Branches

- For each "major" feature I release I will be making a branch for it and then I will merge the changes to the master branch
once the functionality is working.
    - I will also be writing unit tests for the back-end portion of the code I implement as I want to get more practice
    using JUnit because although I have exposure to it, I still need to greatly improve my skill set.
   

# Notes for the specific branch (computer-build-details-backend)

- Currently, the backend is done. It also has tests for the services and the controllers required for the user
to use the api endpoints.
    - However, after I complete the front-end portion I will go back and start to refactor more code as I noticed there
    is some repetitive code that I believe I can compress.
    - There is also an error checker used in all the controllers and currently marked "TODO" that I will implement once
    the front-end is complete because I want to account for all the fields that could potentially require error checking.
- The front end (react application) must now be updated to create some components (to display forms) to be able to
add computer details(computer parts, directions, overclocking and other notes, and purposes) to be able to be added.
   - There will also be buttons added that allow a user to delete individual details.
    
# Progress (master branch)
    - For the progress once I have crossed off the item that means it has been merged into the master branch.
    - <strike>Merged in user login, registration, password change, and account activation features.</strike>
- <strike>Add support to be able to to create, delete/remove, and edit a ComputerBuild and write automated tests for this functionality.</strike>
- <strike>Add backend functionality to be able to add, delete, and update ComputerParts for a ComputerBuild.</strike>
- <strike>Add backend functionality to be able to add, delete, and update Directions for a ComputerBuild.</strike> 
- <strike>Add backend functionality to be able to add, delete, and update Overclocking Notes for a ComputerBuild.</strike> 
- <strike>Add backend functionality to be able to add, delete, and update Other Notes for a ComputerBuild.</strike> 
- Add frontend functionality to be able to add, delete, and update ComputerParts for a ComputerBuild.
- Add frontend functionality to be able to add, delete, and update Directions for a ComputerBuild. 
- Add frontend functionality to be able to add, delete, and update Overclocking Notes for a ComputerBuild. 
- Add frontend functionality to be able to add, delete, and update Other Notes for a ComputerBuild. 
