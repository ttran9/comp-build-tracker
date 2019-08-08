# Live Demo
- The running demo can be seen [here](https://tt-comp-build.herokuapp.com/).

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
- I realize I still have to improve the tests I have written in terms of code quality and testing more cases for
the functionality I have in place.

# Notes for the specific branch (computer-build-refactor-1-2)

- For this branch I was focused on displaying the price for the computer build (sum of all the computer parts). While
working on this I also had to add the totalPrice to the state in order to update it without requiring a page refresh
when deleting a computer part.
    - I also updated the tests in the computer part controller and service classes.
- I realize I still have to improve the tests I have written in terms of code quality and testing more cases for
the functionality I have in place.
    
# TODO:
- Refactor tests and/or possibly add tests. (on-going)   

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
- <strike>Refactor (repetitive) code in the front-end/react app.</strike> 
- <strike>Displaying total price of a computer and writing automated tests for back-end functionality.</strike> 

