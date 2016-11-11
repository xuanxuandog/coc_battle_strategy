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
    
    
    static public func sendHttpRequest(url : String, method : String, body : String?, completion : @escaping (Data?, URLResponse?, Error?) -> Swift.Void) {
        log("request url:\(method) \(url)", level: LOG_LEVEL.DEBUG)
        log("request body:\(body)", level: LOG_LEVEL.DEBUG)
        var request = URLRequest(url: URL(string: url)!)
        request.httpMethod = method
        
        if let postString = body {
            request.httpBody = postString.data(using: .utf8)
        }
        
        let task = URLSession.shared.dataTask(with: request, completionHandler: completion)
        task.resume()
        
        
    }
    
    static public func log(_ message: String, level : LOG_LEVEL) {
        print(message)
    }
    
    static public func waitForAsyncTask(parentView : UIViewController, task : AsyncTask, waitingMessage : String?, errorMessage : String?, successCompletionHandler : (() -> Void)?, errorCompletionHandler : ((UIAlertAction)->Void)? ){
        
        let waiting = Utils.showWaiting(title: waitingMessage!, parentView: parentView)
        
        var result = false
        //waiting for the async task to finish (correctly or wrongly)
        while (true) {
            let state = task.getState()
            if (state == AsyncTaskState.DONE) {
                result = true
                break
            } else if (state == AsyncTaskState.ERROR) {
                break
            }
            //waiting for another one second
            RunLoop.current.run(mode: RunLoopMode.defaultRunLoopMode, before: NSDate(timeIntervalSinceNow: 1) as Date)
        }
        
        if (!result) {
            Utils.log("execute task error:", level: LOG_LEVEL.ERROR)
            waiting.dismiss(animated: true, completion: {
                Utils.showAlert(title: "Error", message: errorMessage!, parentView: parentView, completion : errorCompletionHandler)
            })
        } else {
            Utils.log("execute task success:", level: LOG_LEVEL.DEBUG)
            waiting.dismiss(animated: true, completion: successCompletionHandler)
        }
        
        

    }
}

enum LOG_LEVEL {
    case INFO, DEBUG, WARN, ERROR
}


