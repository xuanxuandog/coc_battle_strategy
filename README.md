# Build/Start Web Service
- Start as a local application. After boot successfully, the web service can be accessed at localhost:8080
>./gradlew bootRun

- Build a war file, which can be deployed in web container like tomcat. After built, the war file will be generated at build/libs/coc-0.1.0.war
>./gradlew build

# Web Service REST API

- create a room

> **POST** http://hostname:port/room

```
{
  "defenders":[0,0,0,..., 0],//required, the initial completed stars of each defender
  "target":23 // optional, the target stars that user want to achieve from all the defenders, if it's not set, the default value is 3 * the number of defenders
}
```
