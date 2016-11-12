//
//  Battle.swift
//  COCBattle
//
//  Created by xualu on 11/7/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import Foundation

class Battle : AsyncTask {
    
    static let BASEURL = "http://1599q744m9.51mypc.cn/room"
    //static let BASEURL = "http://localhost:8080/room"
    
    // MARK: Properties
    var id : String = ""
    
    var defenders = [Defender?]()
    
    public func create() {
        
        //call RESTAPI to create a battle
        
        self.state = AsyncTaskState.RUNNING
        
        var initialCompletedStars = [Int?]()
        
        for defender in defenders {
            initialCompletedStars.append(defender?.initialStarCount)
        }
        
        let json = MyJSON()
        json.set(key: "defenders", value: initialCompletedStars)
        let postString = json.toString()
 
        Utils.sendHttpRequest(url: Battle.BASEURL, method: "POST", body: postString, successHandler: {(responseString : String)in
            self.loadFromResponseString(responseString)
            self.state = AsyncTaskState.DONE
        }, errorHandler : {(error : Error) in
            self.state = AsyncTaskState.ERROR
        })
    }
    
    public func join(attacker : Attacker) {
        //call RESTAPI to let the given attacker join the battle
        
        self.state = AsyncTaskState.RUNNING
        
        var url = Battle.BASEURL
        url += "/join/\(self.id)/\(attacker.id!)"
        
        let json = MyJSON()
        json.set(key: "starConfidence", value: attacker.starConfidence)
        
        Utils.sendHttpRequest(url: url, method: "POST", body: json.toString(), successHandler: {(responseString : String)in
            self.loadFromResponseString(responseString)
            self.state = AsyncTaskState.DONE
        }, errorHandler : {(error : Error) in
            self.state = AsyncTaskState.ERROR
        })
        
    }
    
    public func load() {
        //call RESTAPI to create a battle
        
        self.state = AsyncTaskState.RUNNING
        
        let url = "\(Battle.BASEURL)/\(self.id)"
        
        Utils.sendHttpRequest(url: url, method: "GET", body: nil, successHandler: {(responseString: String) in
            self.loadFromResponseString(responseString)
            self.state = AsyncTaskState.DONE
        }, errorHandler: {(Error) in
            self.state = AsyncTaskState.ERROR
        })
    }
    
    
    private func loadFromResponseString(_ response : String) {
        let responseData = response.data(using: String.Encoding.utf8)
        let responseJson = JSON(data: responseData!)
        
        //set id
        self.id = responseJson["id"].stringValue
        
        //init defenders with initial stars
        self.defenders = [Defender?]()
        var index = 0
        for item in responseJson["initialStars"].arrayValue {
            let defender = Defender(String(index))
            defender.initialStarCount = item.intValue
            self.defenders.append(defender)
            index = index + 1
        }
        //set completed stars based on current battle map
        index = 0
        for item in responseJson["completedStars"].arrayValue {
            self.defenders[index]?.completedStarCount = item.intValue
            index = index + 1
        }
        
        //init attackers
        
        
        

    }
    
    // MARK: AsyncTask functions
    
    var state = AsyncTaskState.INIT
    
    public func getState() -> AsyncTaskState {
        return state
    }
    
    public func getResult() -> Any? {
        return self
    }
    
}
