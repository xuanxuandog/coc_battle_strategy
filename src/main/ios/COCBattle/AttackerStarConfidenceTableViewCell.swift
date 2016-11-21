//
//  AttackerStarConfidenceTableViewCell.swift
//  COCBattle
//
//  Created by xualu on 11/15/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import UIKit

class AttackerStarConfidenceTableViewCell: UITableViewCell {

    // MARK: Properties
    
    var gotStars = [[Int]]()
    var labelStars = [UILabel]()
    
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
        self.star3.selectedStars = 3
        self.star2.selectedStars = 2
        self.star1.selectedStars = 1
        
        gotStars.append([Int]())
        gotStars.append([Int]())
        gotStars.append([Int]())
        
        self.labelStars.append(labelStar1)
        self.labelStars.append(labelStar2)
        self.labelStars.append(labelStar3)
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    public func addGotStar(starCount:Int, enemyPosition: Int) {
        if (starCount < 1 || starCount > self.gotStars.count) {
            return
        }
        
        self.gotStars[starCount - 1].append(enemyPosition)
        updateGotStars()
    }
    
    private func updateGotStars() {
        for i in 0 ..< self.labelStars.count {
            var text = ""
            for j in 0 ..< self.gotStars[i].count {
                if (j > 0) {
                    text = text + ","
                }
                text = text + String(self.gotStars[i][j])
            }
            self.labelStars[i].text = text
        }
    }

}
