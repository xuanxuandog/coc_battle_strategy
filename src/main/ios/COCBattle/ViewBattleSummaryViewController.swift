//
//  ViewBattleSummaryViewController.swift
//  COCBattle
//
//  Created by xualu on 11/12/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import UIKit

class ViewBattleSummaryViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    // MARK: Properties
    var battle : Battle?

    
    // MARK: Outlets

    @IBOutlet weak var tableAttackers: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.tableAttackers.delegate = self
        self.tableAttackers.dataSource = self
        
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - table datasource functions
    public func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        var count = 0
        for attacker in (self.battle?.attackers)! {
            if let id = attacker?.id {
                count = count + 1
            }
        }
        return count
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        
        let cellIdentifier = "attackerStarConfidenceTableCell"
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as! AttackerStarConfidenceTableViewCell
        
        // Configure the cell...
        var index = 0
        for attacker in (self.battle?.attackers)! {
            if let id = attacker?.id {
                if (index == indexPath.row) {
                    //find the cell to display
                    cell.labelAttackerId.text = "Position: \(id)"
                    for i in 0 ..< (attacker)!.starConfidence.count {
                        cell.addGotStar(starCount: (attacker?.starConfidence[i])!, enemyPosition: i + 1)
                    }
                    
                    return cell
                }
                index = index + 1
            }
        }
        return cell
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
