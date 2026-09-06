# Identity service
This microservice is responsible for:
* Onboarding users
* Roles and permissions
* Authentication

## Tech stack
* Build tool: maven >= 3.9.16
* Java: 21
* Framework: Spring boot 3.2.x
* DBMS: MySQL

## Start application
'mvn spring-boot:run'

## Build application
'mvn clean package'

## Docker guideline
### Create network
'docker network create devteria-network'
### Start mysql in devteria-network
'docker run --network devteria-network --name mysql -p 3307:3306 -e MYSQL_ROOT_PASSWORD=... -d mysql:8.0.45-debian'
### Run your application with devteria-network
'docker run --name identity-service --network devteria-network -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://mysql:3306/identity_service identity-service:0.0.1'