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


# Branches

- For each "major" feature I release I will be making a branch for it and then I will merge the changes to the master branch
once the functionality is working.
    - I will also be writing unit tests for the back-end portion of the code I implement as I want to get more practice
    using JUnit because although I have exposure to it, I still need to greatly improve my skill set.


# Notes for the specific branch (user-login-and-registration)

- I will be using these two features together because if one registers a user it is expected that one can login following
the registration.
    - I will be testing individual components for the registration and login ability but I am not sure how to unit
    test the sending email feature so I will not write a test for that.
        - I will be taking code that I added to the personal project management tool that I followed from a Udemy course
        and the email sending code seems to work (sends an email after registration and sends an email when the user
        requests for a new password).
        - An initial thought that I have here is to perhaps to some form of BDD to just ensure that certain methods are
        called from the email sender bean/component.
        
- When testing the confirm registration email and change password, both of these require a token so I decided
to refactor the ResponseEntity objects to return the token. I'm not sure if this is a secure practice but I decided
to do this because the tokens expire after a certain amount of time.
    - If I was doing this at a production level and used by more users and security was a more important issue I would
put a higher priority on this and perhaps look up solutions on how to write an automated test for this type
of feature.
