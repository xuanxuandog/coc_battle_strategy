//
//  AttackerStarConfidenceTableViewCell.swift
//  COCBattle
//
//  Created by xualu on 11/15/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import UIKit

class AttackerStarConfidenceTableViewCell: UITableViewCell {

    // MARK: Outlets
    
    @IBOutlet weak var labelAttackerId: UILabel!
    
    @IBOutlet weak var labelAttacked: UILabel!
    
    @IBOutlet weak var star3: Star!
    
    @IBOutlet weak var labelStar3: UILabel!
    
    @IBOutlet weak var star2: Star!
    
    @IBOutlet weak var labelStar2: UILabel!
    @IBOutlet weak var star1: Star!
    @IBOutlet weak var labelStar1: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
