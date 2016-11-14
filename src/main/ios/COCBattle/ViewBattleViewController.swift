//
//  ViewBattleViewController.swift
//  COCBattle
//
//  Created by xualu on 11/6/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import UIKit

class ViewBattleViewController: UITabBarController {

    // MARK: Properties
    
    var battle : Battle?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = (self.battle?.title)!
        (self.viewControllers?[0] as! ViewBattleSummaryViewController).battle = self.battle
        self.navigationItem.leftBarButtonItem = UIBarButtonItem(title: "Home", style: UIBarButtonItemStyle.plain, target: self, action: #selector(ViewBattleViewController.backToHome(_:)))
        // Do any additional setup after loading the view.q
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    // MARK: - Navigation
/*
    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
  */
    // MARK: - Actions
    
    public func backToHome(_ sender: Any?) {
        self.navigationController?.popToRootViewController(animated: false)
        
        /*
        for vc in (self.navigationController?.viewControllers)! {
            if ((vc as? MainViewController) != nil) {
                self.navigationController?.popToViewController(vc, animated: true)
            }
        }*/
    }
 

}
