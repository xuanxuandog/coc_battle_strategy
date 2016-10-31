# Summary
This project is to build software which is used for COC(clash of clan) players to get best strategy(who attack who) for clan battle based on each user's confidence to get the nubmer of stars from the players of the counterpart clan(we call them defenders). The software includes two parts: 
- the web service part which providing all RESTFUL APIs. 
- the IOS app client part which can be run on iphone/ipad device

# Use Cases
- The capatain of the clan can create a room and input the number of defenders that they need attack, also can set the initial stars of each defenders that has already been completed(this is used for the scenarios that the battle is already on the way, so that capatain may need adjust the strategy). After room created, the capatain will be informed the room id, then he/she can forecast the room id to all the players that will attend this battle.
- Each player can join a room using room id and input the number of stars that he/she can get from each defender of the other clan. In addition, each player can input the number of attack chance that he/she left(the default value is 2). If some player has already attacked some defender, the attacker should also input this information.
- Then after all players have finished their input, the capatain can get the best strategy. (Capatain can also set a target number of stars based on all players input)

# Get the project
- git clone https://github.com/xuanxuandog/coc_battle_strategy

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
Request payload
{
  "defenders":[0,0,0,..., 0],// required, the initial completed stars of each defender
  "target":23 // optional, the target stars that user want to achieve from all the defenders, if it's not set, the default value is 3 * the number of defenders
}

Response
room details, please refer to later section
```

- get a room info

> **GET** http://hostname:port/room/id

```
Response
room details, please refer to later section
```

- get count of active rooms

> **GET** http://hostname:port/room/count

```
Response
{ 
  "count" : 1
}
```

- update target stars

> **GET** http://hostname:port/room/target/{roomId}/{number of target stars}

```
Response
room details, please refer to later section
```

- join as an attacker

> **POST** http://hostname:port/room/join/{roomId}/{attackerId}

```
Request payload
{
  "starConfidence":[1,3,3],//required, the number of stars that this attacker think he/she can get from each defenders.
                           //note, if some defender has already been attacked by this attacker, the value should be set to 0 for
                           //that defender
  "attackChance":1 //optional, the left attack chance that this attacker has. If not set, the default value is 2
}
Response
room details, please refer to later section
```

- response of room detail

```
{
   "id": "1", // id of created room
   "targetStars": 9, // target stars
   "initialStars": 0, // initial stars that already completed
   "battleMap": { // the main output of the strategy, which tell the clan who need attack who
       
   },
   "totalCompletedStars": 0, // total completed stars from all the defenders
   "attackers": [ //the detail information of all the registered attackers
   
   ],
   "completedStars": null // the detail number of completed stars of all the defenders
}

```

