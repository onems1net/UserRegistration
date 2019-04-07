# spring-boot-user-registration

Please follow these instructions to run the application

Check out the project 

1. git clone https://github.com/onems1net/spring-boot-user-registration.git

2. Go to the project base directory

3. Run the shell script using following command to build the docker image for the application jar file and to run the application in docker container under 8085 port

./run.sh

4. Application REST endpoints will be available under the URI

List all registered users: localhost:8085/user/list
Register an user: localhost:8085/user/add
Search an user: localhost:8085/user/find/{id}
Update an user details: localhost:8085/user/update/{id}
Delete an user: localhost:8085/user/delete/{id}

P.S. Application jar is already packaged under the target directory. To rebuild it run following command under project base directory
mvn clean install
