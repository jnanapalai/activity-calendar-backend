# Activity Calendar
This application responsible for create and manage activity of a user throughout 
any year. 

For details check open api document [openapi](documentation/openapi/openapi.yml)

# Build Application
  mvn application
  
# Run Docker for Key cloak and postgres
  docker-compose up -d
  **_NOTE:_**
  Running docker will not require if you want to run application with
  configuration parameter: com.activity.authorization.type = oauth

# Run Application
  Any of the way you can run application
  1. Run application as spring boot  application from Main class - ActivityCalendarApplication.java
     with configuration 
        **either**
        i) com.activity.authorization.type = oauth 
          (Running docker with keycloak and postgress require for this. 
           Do not forget to do [Running Docker File](#run-docker-for-key-cloak-and-postgres))
        **or**
        ii) com.activity.authorization.type = oauth 

  2. Run Application through maven command from command prompt (cmd)
     a) mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dcom.activity.authorization.type=oauth"
      (Running docker with keycloak and postgress require for this.
     Do not forget to do [Running Docker File](#run-docker-for-key-cloak-and-postgres))
     b) mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dcom.activity.authorization.type=custom"

  3. Run Application through java command from command promnt (cmd) and from **/target** folder
     a) java -jar activity-calendar-backend-0.0.1-SNAPSHOT.jar --com.activity.authorization.type=oauth
     (Running docker with keycloak and postgress require for this. 
      Do not forget to do [Running Docker File](#run-docker-for-key-cloak-and-postgres)) 
     b) java -jar activity-calendar-backend-0.0.1-SNAPSHOT.jar --com.activity.authorization.type=custom

    