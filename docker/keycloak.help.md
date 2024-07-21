[Export/import documentation](https://www.keycloak.org/server/importExport)

Command to export realm MicroserviceRealm with users running on container
```shell
/opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/import --users realm_file --realm MicroserviceRealm
```

Same command to run from shell
```shell
docker exec -it keycloak /opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/import --users skip --realm MicroserviceRealm
```

But this works only on running application but not container. [Link](https://github.com/keycloak/keycloak/issues/20442) 
 
To export realm we can use "Admin console" > "Realm settings" > "Action" (in right top conner) > "Partial export"  