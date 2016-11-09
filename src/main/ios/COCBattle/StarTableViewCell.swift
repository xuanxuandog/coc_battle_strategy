//
//  StarTableViewCell.swift
//  COCBattle
//
//  Created by xualu on 11/5/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import UIKit

class StarTableViewCell: UITableViewCell {

    // MARK: Outlets
    
    @IBOutlet weak var labelIndex: UILabel!
    
    @IBOutlet weak var viewStar: Star!
    
    var positionChanged : ValueChanged?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    

}
