# spring-boot-user-registration

Please follow these instructions to run the application

Check out the project 

1. git clone https://github.com/onems1net/spring-boot-user-registration.git

2. Go to the project base directory

3. Application jar is already packaged under the target directory for Google Drive resource. If checking out from GitHub then build and package it by running following command under project base directory

mvn clean package

4. Run this shell script using following command to build the docker image for the application jar file and to run the application in docker container under 8085 port

./run.sh

4. Application REST endpoints will be available under the URI

a. List all registered users (method GET): http://localhost:8085/user/list

Register an user (requires JSON body to POST): http://localhost:8085/user/add
e.g. JSON body
{"firstName":"Animesh","lastName":"Mondal","email":"onems1net@gmail.com"}

b. Search an user: http://localhost:8085/user/find/{id}

c. Update an user details (requires JSON body to PUT): http://localhost:8085/user/update/{id}
e.g. JSON body
{"firstName":"Animesh","lastName":"Mondal","email":"animesh.mondal@tcs.com"}

d. Delete an user (method DELETE): http://localhost:8085/user/delete/{id}

Included features
1. REST endpoints for user add/edit/search/list/delete facility
2. On successful registration email sending using Fake SMTP server
3. User input validation and error handling
4. Tested usint REST Assured and Postman tool

Incomplete features
1. API documentation using Swagger
2. UI facility
3. Performance testing
