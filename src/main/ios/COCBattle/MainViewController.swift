//
//  MainViewController.swift
//  COCBattle
//
//  Created by xualu on 11/4/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import UIKit

class MainViewController: UIViewController {

    // MARK: Properties
    var battle : Battle?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Actions
    
    @IBAction func joinBattle(_ sender: Any) {
        let alert = Utils.showAlert(title: "Battle ID:", parentView: self)
        alert?.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: {(UIAlertAction)in
            
            self.battle = Battle()
            let textField = (alert?.textFields?.first)! as UITextField
            self.battle?.id = textField.text!
            self.battle?.load()
            
            
            var result = false
            while (true) {
                let state = self.battle?.getState()
                if (state == AsyncTaskState.DONE) {
                    result = true
                    break
                } else if (state == AsyncTaskState.ERROR) {
                    break
                }
                RunLoop.current.run(mode: RunLoopMode.defaultRunLoopMode, before: NSDate(timeIntervalSinceNow: 1) as Date)
            }
            
            if (!result) {
                
                Utils.showAlert(title: "Error", message: "Join battle failed, please try later", parentView: self, completion : nil)
            } else {
            
                self.performSegue(withIdentifier: "JoinBattleStepOne", sender: self)
            }
            
            
            
        }))
        
        
    }
    
    // MARK: - Navigation
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if segue.identifier == "JoinBattleStepOne" {
            let joinBattleStepOneViewController = segue.destination as! JoinBattleStepOneViewController
            joinBattleStepOneViewController.battle = self.battle
        }
    }
    
   
    
}

