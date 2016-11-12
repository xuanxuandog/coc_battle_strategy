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
    
    let PICKER_ATTACKER_POSITION = 1
    let PICKER_ATTACKED_ENEMY_POSITION = 2
    
    var battle : Battle!
    var attacker = Attacker()
    
    var attackedEnemyPosition = 0 {
        didSet {
            self.tableAttacked.reloadData()
            updateJoinButtonStatus()
        }
    }
    
    var alert : UIAlertController!
    
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
        pickerAttackerPosition.tag = self.PICKER_ATTACKER_POSITION
        
        tableAttacked.delegate = self
        tableAttacked.dataSource = self
        
        self.attacker.observer = self
        
        updateJoinButtonStatus()
        
        initAttackedEnemyPickerInput()
        
        // init UI for input attacked enemy position
        if (self.attackedEnemyPosition > 0) {
            self.switchAttacked.isOn = true
        } else {
            self.switchAttacked.isOn = false
        }
        
    }
    
    func initAttackedEnemyPickerInput() {
        
        alert = UIAlertController(title:"Input enemy position", message:"\n\n\n\n\n", preferredStyle: UIAlertControllerStyle.alert)
        
        let pickerFrame: CGRect = CGRect(x: 85, y:50, width:100,height:80);
        let picker = UIPickerView(frame: pickerFrame)
        picker.delegate = self;
        picker.dataSource = self;
        picker.tag = self.PICKER_ATTACKED_ENEMY_POSITION
        
        
        alert.view.addSubview(picker)
        alert.isModalInPopover = true
        alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: {(UIAlertAction) in
            self.attackedEnemyPosition = picker.selectedRow(inComponent: 0) + 1
        }))
        
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
        if (pickerView.tag == self.PICKER_ATTACKER_POSITION) {
            self.attacker.id = String(row + 1)
        }
    }
    
    // MARK: UITableViewDataSource functions
    
    public func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.battle.defenders.count
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cellIdentifier = "StarTableViewCell"
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as! StarTableViewCell
        
        //set self attacker to the cell's callback of star changed
        cell.viewStar.starChanged = self.attacker
        cell.viewStar.rowInTable = indexPath.row
        
        //init cell's value
        cell.viewStar.selectedStars = self.attacker.starConfidence[indexPath.row]
        
        let label : String!
        if (self.attackedEnemyPosition == indexPath.row + 1) {
            label = "\(indexPath.row + 1)     attacked"
        } else {
            label = "\(indexPath.row + 1)"
        }
        cell.labelIndex.text = label
        return cell
        
    }

    // MARK: Actions
    
    @IBAction func alreadyAttacked(_ sender: Any) {
        if (self.switchAttacked.isOn) {
            self.present(alert!, animated: true, completion: nil)
        } else {
            self.attackedEnemyPosition = 0
        }
    }

    @IBAction func joinBattle(_ sender: Any) {
        self.attacker.id = String(self.pickerAttackerPosition.selectedRow(inComponent: 0) + 1)
        if (self.attackedEnemyPosition > 0) {
            self.attacker.attacked = [self.attackedEnemyPosition]
        }
        self.battle.join(attacker: self.attacker)
        Utils.waitForAsyncTask(parentView: self, task: self.battle, waitingMessage: "Joining Battle", errorMessage: "Failed to join the battle, please try again later", successCompletionHandler: {()->Void in
            
            self.performSegue(withIdentifier: "joinToView", sender: self)

        }, errorCompletionHandler: nil)
        
    }
    
    // MARK: ValueChanged protocol function
    func changed(_ caller: Any?) {
        if let caller = caller as! Attacker? {
            self.updateJoinButtonStatus()
        }
    }
    
    func updateJoinButtonStatus() {
        var hasStar = false
        var index = 0
        for star in self.attacker.starConfidence {
            //the attacker can get star from some defender and this defender
            //has not been attacked by this attacker before
            if (star > 0 && (index + 1) != self.attackedEnemyPosition) {
                hasStar = true
                break
            }
            index = index + 1
        }
        if (hasStar) {
            self.btnJoin.isEnabled = true
        } else {
            self.btnJoin.isEnabled = false
        }
    }

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        if segue.identifier == "joinToView" {
            let viewBattle = segue.destination as! ViewBattleViewController
            viewBattle.battle = self.battle
        }
    }
    
    

}
