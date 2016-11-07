//
//  Battle.swift
//  COCBattle
//
//  Created by xualu on 11/7/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import Foundation

class Battle : AsyncTask {
    
    // MARK: Properties
    var id : String = ""
    
    var defenders = [Defender?]()
    
    public func create() {
        
        //call RESTAPI to create a battle
        
        self.state = AsyncTaskState.RUNNING
        
        var request = URLRequest(url: URL(string: "http://1599q744m9.51mypc.cn/room")!)
        request.httpMethod = "POST"
        
        
        var initialCompletedStars = [Int?]()
        
        for defender in defenders {
            initialCompletedStars.append(defender?.initialStarCount)
        }
        
        let json = MyJSON()
        json.set(key: "defenders", value: initialCompletedStars)
        
        let postString = json.toString()
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
            }
            
            let responseString = String(data: data, encoding: .utf8)
            
            self.state = AsyncTaskState.DONE
            print("responseString = \(responseString)")
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
