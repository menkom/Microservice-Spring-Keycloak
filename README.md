Project to practice of microservice securing with Keycloak


### Articles

* [Интеграция Keycloak в приложение Spring Boot 3 с использованием протокола OAuth2.0](https://habr.com/ru/companies/axenix/articles/780422/)

* [A Quick Guide to OAuth2 With Spring Boot And Keycloak](https://www.baeldung.com/spring-boot-keycloak)

* [Keycloak User Self-Registration](https://www.baeldung.com/keycloak-user-registration)

* [Keycloak documentation](https://wjw465150.gitbooks.io/keycloak-documentation/content/index.html)


### Keycloak

* [Get started with Keycloak on Docker](https://www.keycloak.org/getting-started/getting-started-docker)

* [Latest version of Keycloak docker image](https://quay.io/keycloak/keycloak)

* [Complete list of all build options and configuration for Keycloak](https://www.keycloak.org/server/all-config)

#### Export/import process 

[Export/import documentation](https://www.keycloak.org/server/importExport)
[Export and import keycloak realm with users](https://simonscholz.dev/tutorials/keycloak-realm-export-import)

Command to export realm MicroserviceRealm with users running on container
```shell
/opt/keycloak/bin/kc.sh export --file "/tmp/realm-export.json" --realm MicroserviceRealm
```

Same command to run from shell
```shell
docker exec -it keycloak /opt/keycloak/bin/kc.sh export --file "/tmp/realm-export.json" --realm MicroserviceRealm
```

_Notices:_ 

1. It is better to export in file then in dir because it is easier to import.
2. It is better to export in not `/opt/keycloak/data/import` folder because it already can contain same file, and you can get confused was it created or not and why not 
3. Possible problem "Failed to start server in (import_export) mode". This error message comes after finishing of export. For some reason container wants to restart. Export should finish successfully with message **Export finished successfully**

```idea log
INFO  [org.keycloak.services] (main) KC-SERVICES0034: Export of realm 'MicroserviceRealm' requested.
INFO  [org.keycloak.exportimport.singlefile.SingleFileExportProvider] (main) Exporting realm 'MicroserviceRealm' into file /tmp/export-realm.json
INFO  [org.keycloak.services] (main) KC-SERVICES0035: Export finished successfully
INFO  [com.arjuna.ats.jbossatx] (main) ARJUNA032014: Stopping transaction recovery manager
ERROR [org.keycloak.quarkus.runtime.cli.ExecutionExceptionHandler] (main) ERROR: Failed to start server in (import_export) mode
ERROR [org.keycloak.quarkus.runtime.cli.ExecutionExceptionHandler] (main) ERROR: Unable to start HTTP server
ERROR [org.keycloak.quarkus.runtime.cli.ExecutionExceptionHandler] (main) ERROR: java.lang.IllegalStateException: Unable to start the management interface
```

As soon as you did export we need to copy resulting file on local host
```shell
docker cp keycloak:/tmp/realm-export.json /your/local/machine/path
```

But error on Keycloak docker container start mean that we have a problem anyway. It is because of scripts in exported file. To enable scripts we need to add environmental variables:
 * KC_FEATURES: scripts
 * KC_SPI_UPLOAD_SCRIPTS_ENABLED: true

But if you still have error on start you need to enable debug logs with command: `start-dev --verbose`

In my case I have to delete policy field `authorizationSettings.policies` with `type:js` which contained script code.

#### Keycloak administration scope entities that you have to know to be able to use it.

From general to specific

* **Realm** - a security policy domain defined for a web or application server. Realms are isolated from one another and can only manage and authenticate the users that they control. 
* **Client** - represent application or service that can use Keycloak for user authentication and authorization (applications or services you want to secure with Keycloak). Clients can be with authentication for security reason or without. To enable this you need on tab "Settings" turn on "Client authentication". This will display tab "Credentials" and you can see "Client Secret" there. 
* **Role** - used to manage access to resources. Roles can be assigned to users or groups of users. They allow controlling what actions users can perform in applications. Roles define a type of user and applications assign permission and access control to roles. Keycloak has roles for the whole realm or a specific client.
  * _Realm Roles_: Global roles available throughout the realm.
  * _Client Roles_: Client-specific roles used to manage access within a particular client. 
* **Group** - allow you to manage a common set of attributes and role mappings for a set of users. A collection of users that you can apply roles and attributes to in one place. Users can be members of zero or more groups.
* **User** - most specific part of Keycloak hierarchy. Users can have attributes associated with themselves like email, username, etc. In addition, they can be assigned group membership and have specific roles assigned to them. 

Some article about Keycloak core concepts:

* [Server Administration Guide](https://www.keycloak.org/docs/latest/server_admin/)
* [Understanding Realms, Clients, and Roles in Keycloak](https://mi-do.medium.com/understanding-realms-clients-and-roles-in-keycloak-c88a6e57d74f)
* [Keycloak, clients and roles: a tutorial](https://tomas-pinto.medium.com/keycloak-clients-and-roles-a-tutorial-b334147f1dbd)
* [Groups vs. Roles](https://wjw465150.gitbooks.io/keycloak-documentation/content/server_admin/topics/groups/groups-vs-roles.html)


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

### Own User API
Of course, it is convenient to use Keycloak API. But this can become not so secured and flexible enough in some cases. For this reason you can create your own service or endpoints in monolith to use as proxy for Keycloak operations.