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
        print (battle?.id)
        // Do any additional setup after loading the view.q
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
