//
//  AttackedEnemyTableViewCell.swift
//  COCBattle
//
//  Created by xualu on 11/9/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import UIKit

class AttackedEnemyTableViewCell: UITableViewCell, UIPickerViewDelegate, UIPickerViewDataSource {

    // MARK: Properties
    var enemyCount : Int?
    var positionChanged : ValueChanged?
    var rowInTable : Int?
    var selectedPosition : Int?
    
    // MARK: Outlets
    
    @IBOutlet weak var pickerEnemyPosition: UIPickerView!
    
    
    // MARK: - enemy posistion picker view data source functions
    public func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    public func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return self.enemyCount!
    }
    
    
    // MARK: enemy posistion picker view delegate functions
    public func pickerView(_ pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
        let ret = row + 1
        let str = String(ret)
        return NSAttributedString(string:str)
    }
    
    public func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        self.selectedPosition = row + 1
        self.positionChanged?.changed(self)
    }
    
    // MARK: Properties
    //the current row index in the parent table
    var rowIndex = 0
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
