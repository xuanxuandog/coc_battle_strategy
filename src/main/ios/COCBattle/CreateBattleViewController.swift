//
//  CreateBattleViewController.swift
//  COCBattle
//
//  Created by xualu on 11/5/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import UIKit

class CreateBattleViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource,
    UITableViewDelegate, UITableViewDataSource{

    // MARK: Constants
    
    let MIN_PLAYERS = 5
    let MAX_PLAYERS = 50
    
    // MARK: Properties
    
    //maintain a array to cache all cells
    var defenders = [Defender?]()
    
    var battle : Battle?
    
    
    // MARK: Outlets
    
    @IBOutlet weak var playerNumberPickerView: UIPickerView!
    
    @IBOutlet weak var setInitialCompletedStarsSwitch: UISwitch!
    
    
    @IBOutlet weak var tableDefenders: UITableView!
    
    @IBOutlet weak var btnSave: UIBarButtonItem!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        playerNumberPickerView.delegate = self
        playerNumberPickerView.selectRow((MAX_PLAYERS - MIN_PLAYERS) / 2, inComponent: 0, animated: true)
        
        setInitialCompletedStarsSwitch.isOn = false
        
        tableDefenders.delegate = self
        tableDefenders.dataSource = self
        
        for i in 0 ... MAX_PLAYERS {
            defenders.append(Defender(String(i+1)))
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK: UIPickerViewDataSource functions
    
    public func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    public func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return MAX_PLAYERS - MIN_PLAYERS + 1

    }
    
    // MARK: UIPickerViewDelegate functions
    public func pickerView(_ pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
        let ret = row + MIN_PLAYERS
        let str = String(ret)
        return NSAttributedString(string:str)
    }
    
    public func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if (setInitialCompletedStarsSwitch.isOn) {
            tableDefenders.reloadData()
        }
    }
    
    // MARK: UITableViewDelegate functions

    
    // MARK: UITableViewDataSource functions
    
    public func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    public func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if (!setInitialCompletedStarsSwitch.isOn) {
            return 0
        }
        return playerNumberPickerView.selectedRow(inComponent: 0) + MIN_PLAYERS
    }
    
    public func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        
        let cellIdentifier = "StarTableViewCell"
        //let cell = StarTableViewCell()
        let cell = tableView.dequeueReusableCell(withIdentifier: cellIdentifier, for: indexPath) as! StarTableViewCell

        // Configure the cell...
        let defender = defenders[indexPath.row]
        
        //set the defender to the cell's callback of star changed
        cell.viewStar.starChanged = defender
        
        //init cell's value
        cell.viewStar.selectedStars = (defender?.initialStarCount)!
        cell.labelIndex.text = String(indexPath.row + 1)
        cell.labelAlreadyAttacked.isHidden = true
        cell.switchAlreadyAttacked.isHidden = true
        
        return cell

    }
    

    // MARK: Actions
    
    @IBAction func cancel(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func setInitialCompletedStars(_ sender: Any) {
        tableDefenders.reloadData()
    }
    
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        
        if segue.identifier == "CreateBattle" {
            let viewBattle = segue.destination as! ViewBattleViewController
            viewBattle.battle = self.battle
        }
    }
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        
        if identifier == "CreateBattle" {
            var result = false
            Utils.navigationItemAcitivityIndicatorStart(self.navigationItem, leftOrRight: "right")
            battle = Battle()
            battle?.defenders = self.defenders
            battle?.create()
            
            while (true) {
                let state = battle?.getState()
                if (state == AsyncTaskState.DONE) {
                    result = true
                    break
                } else if (state == AsyncTaskState.ERROR) {
                    break
                }
                RunLoop.current.run(mode: RunLoopMode.defaultRunLoopMode, before: NSDate(timeIntervalSinceNow: 1) as Date)
            }
            
            if (!result) {
                Utils.showAlert(title: "Error", message: "Create battle failed, please try later.", parentView: self)
            }
                
            self.navigationItem.rightBarButtonItem = self.btnSave
            Utils.navigationItemActivityIndicatorStop()
            return result
        } else {
            return true
        }
    }
    

}
