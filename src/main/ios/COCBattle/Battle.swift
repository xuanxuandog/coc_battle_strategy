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
        
        var request = URLRequest(url: URL(string: Battle.BASEURL)!)
        request.httpMethod = "POST"
        
        
        var initialCompletedStars = [Int?]()
        
        for defender in defenders {
            initialCompletedStars.append(defender?.initialStarCount)
        }
        
        let json = MyJSON()
        json.set(key: "defenders", value: initialCompletedStars)
        
        let postString = json.toString()
        print ("requestString = \(postString)")
        request.httpBody = postString.data(using: .utf8)
 
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {                                                 // check for fundamental networking error
                print("error=\(error)")
                self.state = AsyncTaskState.ERROR
                return
            }
            
            if let httpStatus = response as? HTTPURLResponse, httpStatus.statusCode != 200 {           // check for http errors
                print("statusCode should be 200, but is \(httpStatus.statusCode)")
                print("response = \(response)")
                self.state = AsyncTaskState.ERROR
                return
            }
            
            let responseString = String(data: data, encoding: .utf8)
            
            self.state = AsyncTaskState.DONE
            print("responseString = \(responseString)")
        }
        task.resume()
        
        
    }
    
    public func load() {
        //call RESTAPI to create a battle
        
        self.state = AsyncTaskState.RUNNING
        
        let url = "\(Battle.BASEURL)/\(self.id)"
        print ("url=\(url)")
        var request = URLRequest(url: URL(string:url)!)
        request.httpMethod = "GET"
        
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            guard let data = data, error == nil else {                                                 // check for fundamental networking error
                print("error=\(error)")
                self.state = AsyncTaskState.ERROR
                return
            }
            
            if let httpStatus = response as? HTTPURLResponse, httpStatus.statusCode != 200 {           // check for http errors
                print("statusCode should be 200, but is \(httpStatus.statusCode)")
                print("response = \(response)")
                self.state = AsyncTaskState.ERROR
                return
            }
            
            let responseString = String(data: data, encoding: .utf8)
            
            self.state = AsyncTaskState.DONE
            print("responseString = \(responseString)")
            let responseData = responseString?.data(using: String.Encoding.utf8)
            let responseJson = JSON(data: responseData!)
            
            self.defenders = [Defender?]()
            var index = 0
            for item in responseJson["initialStars"].arrayValue {
                let defender = Defender(String(index))
                defender.initialStarCount = item.intValue
                self.defenders.append(defender)
                index = index + 1
            }
        }
        task.resume()
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
