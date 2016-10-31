# Summary
This project is to build software which is used for COC(clash of clan) players to get best strategy(who attack who) for clan battle based on each user's confidence to get the nubmer of stars from the players of the counterpart clan(we call them defenders). The software includes two parts: 
- the web service part which providing all RESTFUL APIs. 
- the IOS app client part which can be run on iphone/ipad device

# Use Cases
- The capatain of the clan can create a room and input the number of defenders that they need attack, also can set the initial stars of each defenders that has already been completed(this is used for the scenarios that the battle is already on the way, so that capatain may need adjust the strategy). After room created, the capatain will be informed the room id, then he/she can forecast the room id to all the players that will attend this battle.
- Each player can join a room using room id and input the number of stars that he/she can get from each defender of the other clan
- Then after all players have finished their input, the capatain can get the best strategy. (Capatain can also set a target number of stars based on all players input)

# Build/Start Web Service
- Start as a local application. After boot successfully, the web service can be accessed at localhost:8080
>./gradlew bootRun

- Build a war file, which can be deployed in web container like tomcat. After built, the war file will be generated at build/libs/coc-0.1.0.war
>./gradlew build

# Build/Deploy IOS app

# Web Service REST API

- create a room

> **POST** http://hostname:port/room

```
{
  "defenders":[0,0,0,..., 0],//required, the initial completed stars of each defender
  "target":23 // optional, the target stars that user want to achieve from all the defenders, if it's not set, the default value is 3 * the number of defenders
}
```
