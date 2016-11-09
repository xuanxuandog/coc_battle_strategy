//
//  StarChanged.swift
//  COCBattle
//
//  Created by xualu on 11/6/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import Foundation

protocol ValueChanged {
    /*
       starCount is the number of selected star after change
    */
    func changed(_ caller : Any?)
}
