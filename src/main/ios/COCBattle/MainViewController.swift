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
    var battle : Battle!
    
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
        let alert = Utils.showInput(title: "Battle ID:", parentView: self)
        alert?.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: {(UIAlertAction)in
            
            self.battle = Battle()
            let textField = (alert?.textFields?.first)! as UITextField
            self.battle?.id = textField.text!
            self.battle?.load()
            
            Utils.waitForAsyncTask(parentView: self, task: self.battle, waitingMessage: "Loading battle", errorMessage: "Load battle failed, please try again later", successCompletionHandler: {()->Void in
            
                  self.performSegue(withIdentifier: "JoinBattle", sender: self)
            
            }, errorCompletionHandler: nil)
            
            
        }))
        
        
    }
    
    
    @IBAction func createBattle(_ sender: Any) {
        self.performSegue(withIdentifier: "mainToCreate", sender: self)
    }
    
    // MARK: - Navigation
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if segue.identifier == "JoinBattle" {
            let joinBattleViewController = segue.destination as! JoinBattleViewController
            joinBattleViewController.battle = self.battle
        } 
    }    
}

