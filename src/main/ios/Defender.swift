//
//  Defender.swift
//  COCBattle
//
//  Created by xualu on 11/6/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import Foundation

class Defender : ValueChanged {
    
    // MARK: Properties
    
    let id : String?
    
    var initialStarCount : Int = 0
    
    var completedStarCount : Int = 0
    
    init(_ id : String) {
        self.id = id
    }
    
    public func changed(_ caller: Any?) {
        if let caller = caller as! Star? {
            self.initialStarCount = caller.selectedStars
        }
    }
}
