//
//  JoinBattleViewController.swift
//  COCBattle
//
//  Created by xualu on 11/7/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import UIKit

class JoinBattleViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource, UITableViewDelegate, UITableViewDataSource, ValueChanged {

    // MARK: Properties
    var battle : Battle!
    var attacker = Attacker()
    
    var attackedEnemyPosition = 0
    
    // MARK: Outlets
    
    @IBOutlet weak var pickerAttackerPosition: UIPickerView!
    
    @IBOutlet weak var tableAttacked: UITableView!
    
    @IBOutlet weak var switchAttacked: UISwitch!
    
    @IBOutlet weak var btnJoin: UIBarButtonItem!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        for _ in 0...battle.defenders.count {
            self.attacker.starConfidence.append(0)
        }
        
        pickerAttackerPosition.delegate = self
        pickerAttackerPosition.selectRow(0, inComponent: 0, animated: true)
        
        tableAttacked.delegate = self
        tableAttacked.dataSource = self
        
        self.attacker.observer = self
        
        updateJoinButtonStatus()
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
        self.attacker.id = String(row + 1)
    }
    
    // MARK: UITableViewDataSource functions
    
    public func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.battle.defenders.count
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        print ("index:\(indexPath.row)")
        
        let cellIdentifier = "StarTableViewCell"
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as! StarTableViewCell
        
        //set self attacker to the cell's callback of star changed
        cell.viewStar.starChanged = self.attacker
        cell.viewStar.rowInTable = indexPath.row
        
        //init cell's value
        cell.viewStar.selectedStars = self.attacker.starConfidence[indexPath.row]!
        cell.labelIndex.text = String(indexPath.row + 1)
        return cell
        
    }

    // MARK: Actions
    
    @IBAction func alreadyAttacked(_ sender: Any) {
    }

    @IBAction func joinBattle(_ sender: Any) {
    }
    
    // MARK: ValueChanged protocol function
    func changed(_ caller: Any?) {
        if let caller = caller as! Attacker? {
            self.updateJoinButtonStatus()
        }
    }
    
    func updateJoinButtonStatus() {
        var hasStar = false
        for star in self.attacker.starConfidence {
            if (star! > 0) {
                hasStar = true
                break
            }
        }
        if (hasStar) {
            self.btnJoin.isEnabled = true
        } else {
            self.btnJoin.isEnabled = false
        }
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
