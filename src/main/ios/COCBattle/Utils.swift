//
//  Utils.swift
//  COCBattle
//
//  Created by xualu on 11/7/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import Foundation
import UIKit

class Utils {
    
    // MARK: Properties
    
    static let activityIndicator = UIActivityIndicatorView(activityIndicatorStyle:.gray)
    
    static let barButtonActivityIndicator = UIBarButtonItem(customView: activityIndicator)
    
    static public func navigationItemAcitivityIndicatorStart(_ item : UINavigationItem, leftOrRight : String) {
        activityIndicator.isHidden = false
        activityIndicator.startAnimating()
        if (leftOrRight == "left") {
            item.leftBarButtonItem = barButtonActivityIndicator
        } else if (leftOrRight == "right") {
            item.rightBarButtonItem = barButtonActivityIndicator
        }
    }
    
    static public func navigationItemActivityIndicatorStop() {
        activityIndicator.stopAnimating()
        activityIndicator.isHidden = true
    }
    
    static public func showAlert(title : String, message : String, parentView : UIViewController?, completion :  ((UIAlertAction) -> Swift.Void)? = nil) {
        if let parentView = parentView {
            let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: completion))
            parentView.present(alert, animated: true, completion: nil)
        }
    }
    
    static public func showAlert(title : String, parentView : UIViewController?) -> UIAlertController?{
        if let parentView = parentView {
            
            let alert = UIAlertController(title: title, message: nil, preferredStyle: UIAlertControllerStyle.alert)
            alert.addTextField(configurationHandler: {(textField : UITextField) in 
                textField.text = ""
            })
            parentView.present(alert, animated: true, completion: nil)
            return alert
        }
        return nil
    }
    
}
