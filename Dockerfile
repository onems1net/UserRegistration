FROM openjdk:8
ADD target/spring-boot-user-registration.jar spring-boot-user-registration.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","spring-boot-user-registration.jar"]