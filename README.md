# oauth-service
a microservice for oauth2 authentication services

# Database Setup 
This is for mysql. Please start mysql service first

- Login MySQL with root

- Create new database
``` CREATE DATABASE jumkid ```

- Create userEntity
``` CREATE USER 'jumkid'@'localhost' IDENTIFIED BY 'password';```

- Grant permission
``` GRANT ALL ON jumkid.* TO 'jumkid'@'localhost' WITH GRANT OPTION;```

