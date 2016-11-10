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
    
    static public func showAlert(title : String, message : String, parentView : UIViewController?, completion :  ((UIAlertAction) -> Swift.Void)? = nil) {
        if let parentView = parentView {
            let alert = UIAlertController(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: completion))
            
            parentView.present(alert, animated: true, completion: nil)
        }
    }
    
    static public func showInput(title : String, parentView : UIViewController?) -> UIAlertController?{
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
    
    static public func showWaiting(title : String, parentView : UIViewController) -> UIAlertController {
        let alert = UIAlertController(title: title, message: "\n\n\n\n", preferredStyle: UIAlertControllerStyle.alert)
        let spinnerIndicator: UIActivityIndicatorView = UIActivityIndicatorView(activityIndicatorStyle: UIActivityIndicatorViewStyle.whiteLarge)
        
        spinnerIndicator.center = CGPoint(x:135.0, y:65.5)
        spinnerIndicator.color = UIColor.black
        spinnerIndicator.startAnimating()
        
        alert.view.addSubview(spinnerIndicator)
        parentView.present(alert, animated: true, completion: nil)
        
        return alert
    }
}


