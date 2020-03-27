Cake Manager MicroService RESTFul services 
=======================================

## Pre-Requisite Software:
    * Apache Maven
    * JDK 8
    * Google - gmail userid and password for Oauth2 Authentication.
    * Docker Hub.
    * Pivotal Cloud Foundry
    * API testing postman

### Implemented REST end point:
    http://localhost:8081/cakes
        This returns all cake information.
    http://localhost:8081/cakes/{cakeId}
        This returns cake information based on cakeId.
    http://localhost:8081/addCake
        This adds new cake information.
    http://localhost:8081/cakeJsonData
        This download the cake information into json file.

### Things added.
#### Tests
    Spock testing framework used.
#### Authentication via OAuth2:
    This functionality achieved using Google OAuth2.
#### Continuous Integration via any cloud CI system
    This functionality achieved using jenkin also CD has implemented (Jenkinfile) and pushing docker image on cloud foundry.
    https://console.run.pivotal.io
#### Containerisation
    This functionality done via Docker (Dockerfile).
#### Reporting with Code Coverage:
    * Run below command to generate reporting:
    Eg: /Users/atulkumar/workspace/assessment> mvn cobertura:cobertura
    * You can find report inside target folder under jaococo-ut folder (index.html).

### Create Client ID and Secret that will use to authorize via google OAuth2
    * Go to https://console.developers.google.com/
    * Go to OAuth consent screen
    * Give Application Name
    * Domain name like localhost
    * Go to Credentials
    * CREATE CREDENTIALS
    * Choose OAuth Client ID
    * Select Application Type as Web Application
    * Add Authorized redirect URIs as http://localhost:8081/login/oauth2/code/google
    * Click on CREATE button.
    * You will get Client id and Secret in popup window.
    * Copy the Client id and Secret in text editor.
    * Update below two keys filed in application.properties file
        spring.security.oauth2.client.registration.google.client-id=
        spring.security.oauth2.client.registration.google.client-secret=
 
### Create Docker ID and command for containerization
    * Create Docker ID from https://hub.docker.com
    * Download the docker container and install.
    * Open command terminal and use the following command
    * docker -v to check the docker version
    * docker login to logged in into Docker
    * docker build -t user/cakemanager .
    * docker images
    * docker ps -a
    * docker run -p 8081:8081 user/cakemanager to run the docker image
    * to run in background use command - docker run -d -p 8081:8081 user/cakemanager

### Cloud Foundry important commands
    * https://docs.cloudfoundry.org/cf-cli/getting-started.html

### Future Enhancement
    * Create our own Oauth2 authorization server.
    * Front end using React or Angular.
    * Include Swagger for API documentation.
