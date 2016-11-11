//
//  Attacker.swift
//  COCBattle
//
//  Created by xualu on 11/7/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import Foundation

class Attacker : ValueChanged{
    
    static let TOTAL_ATTACK_CHANCE = 2
    
    // MARK: Properties
    
    var id : String?
    
    var starConfidence : [Int] = []
    
    var leftAttackChance = TOTAL_ATTACK_CHANCE
    
    var observer : ValueChanged?
    
    func changed(_ caller: Any?) {
        if let caller = caller as! Star? {
            self.starConfidence[caller.rowInTable] = caller.selectedStars
            observer?.changed(self)
        }
    }
    
}
