Project to practice of microservice securing with Keycloak


### Articles

* [Интеграция Keycloak в приложение Spring Boot 3 с использованием протокола OAuth2.0](https://habr.com/ru/companies/axenix/articles/780422/)

* [A Quick Guide to OAuth2 With Spring Boot And Keycloak](https://www.baeldung.com/spring-boot-keycloak)

* [Keycloak User Self-Registration](https://www.baeldung.com/keycloak-user-registration)


### Keycloak

* [Get started with Keycloak on Docker](https://www.keycloak.org/getting-started/getting-started-docker)

* [Latest version of Keycloak docker image](quay.io/keycloak/keycloak)

* [Complete list of all build options and configuration for Keycloak](https://www.keycloak.org/server/all-config)

#### Export/import process 

[Export/import documentation](https://www.keycloak.org/server/importExport)

Command to export realm MicroserviceRealm with users running on container
```shell
/opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/import --users realm_file --realm MicroserviceRealm
```

Same command to run from shell
```shell
docker exec -it keycloak /opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/import --users skip --realm MicroserviceRealm
```

#### Notice

But this works only on running application but not container. [Link](https://github.com/keycloak/keycloak/issues/20442)

To export realm we can use "[Admin console](http://localhost:8070/admin/master/console/)" > "Realm settings" > "Action" (in right top conner) > "Partial export"

#### Create user

There are several ways to create user for realm
1. Import user with realm and settings
2. Create manually via UI of admin console
3. Create via keycloak endpoint (take a look at [existing requests](./scratches/steps.http))
4. With help of own service that has access as client to keycloak 

#### Possible problems

* Loading the admin console spinning
  On attempt to open admin console infinite "Loading the admin console" display. 
  Reason: keycloak hostname was differed as defined in the ingress router (redirect was not working as expected) = incorrect value for KC_HOSTNAME of docker container

https://keycloak.discourse.group/t/loading-the-admin-console-spinning/17105

https://stackoverflow.com/questions/73883728/keycloak-admin-console-loading-indefinitely
