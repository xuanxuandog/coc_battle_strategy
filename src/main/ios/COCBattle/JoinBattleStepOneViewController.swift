//
//  JoinBattleStepOneViewController.swift
//  COCBattle
//
//  Created by xualu on 11/7/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import UIKit

class JoinBattleStepOneViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource, UITableViewDelegate, UITableViewDataSource,ValueChanged {

    // MARK: Properties
    var battle : Battle!
    var attacker : Attacker!
    
    //the value is the id of the defender that this attacker has already attacked.
    var attackedDefenders = [Int?]()
    
    // MARK: Outlets
    
    @IBOutlet weak var pickerAttackerPosition: UIPickerView!
    
    @IBOutlet weak var tableAttacked: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        pickerAttackerPosition.delegate = self
        pickerAttackerPosition.selectRow(0, inComponent: 0, animated: true)
        
        tableAttacked.delegate = self
        tableAttacked.dataSource = self
        
        self.attacker = Attacker()
        //init array of attacked defenders, 0 means no defender
        for _ in 0...Attacker.TOTAL_ATTACK_CHANCE {
            attackedDefenders.append(0)
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: - Attacker posistion picker view data source functions
    public func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    public func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return (self.battle?.defenders.count)!
        
    }
    
    
    // MARK: Attacker posistion picker view delegate functions
    public func pickerView(_ pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
        let ret = row + 1
        let str = String(ret)
        return NSAttributedString(string:str)
    }
    
    public func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        self.attacker?.id = String(row + 1)
    }
    
    // MARK: UITableViewDataSource functions
    
    public func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        var count = 0
        for i in self.attackedDefenders {
            if (i == 0) {
                return count
            } else {
                count = count + 1
            }
        }
        print (count)
        return count
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        
        let cellIdentifier = "AttackedEnemyTableViewCell"
        //let cell = StarTableViewCell()
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as! AttackedEnemyTableViewCell
        cell.enemyCount = self.battle.defenders.count
        cell.positionChanged = self
        cell.rowInTable = indexPath.row
        cell.pickerEnemyPosition.delegate = cell
        cell.pickerEnemyPosition.selectRow(self.attackedDefenders[indexPath.row]! - 1, inComponent: 0, animated: false)
        
        return cell
        
    }
    
    // MARK: - ValueChanged protocol function
    func changed(_ caller : Any?) {
        if let caller = caller as! AttackedEnemyTableViewCell? {
            self.attackedDefenders[caller.rowInTable!] = caller.selectedPosition
        }
    }

    // MARK: Actions
    
    
    @IBAction func btnAddAttackedEnemy(_ sender: Any) {
        for i in 0...self.attackedDefenders.count {
            if (self.attackedDefenders[i] == 0) {
                self.attackedDefenders[i] = 1
                break
            }
        }
        self.tableAttacked.reloadData()
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
