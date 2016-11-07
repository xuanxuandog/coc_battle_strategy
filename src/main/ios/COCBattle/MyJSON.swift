//
//  MyJSON.swift
//  COCBattle
//
//  Created by xualu on 11/7/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import Foundation

class MyJSON {
    
    // MARK: Properties
    let para : NSMutableDictionary = NSMutableDictionary()

  
    public func set(key: String, value: Any) {
        para.setObject(value, forKey: key as NSCopying)
    }
    
    public func toString() -> String {
        let jsonData = try! JSONSerialization.data(withJSONObject: para, options: JSONSerialization.WritingOptions.prettyPrinted)
        let jsonString = NSString(data: jsonData, encoding: String.Encoding.utf8.rawValue) as! String
        return jsonString
    }
    
}
