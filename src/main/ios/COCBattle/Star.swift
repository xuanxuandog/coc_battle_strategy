//
//  Star.swift
//  COCBattle
//
//  Created by xualu on 11/5/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import UIKit

class Star: UIView {

    // MARK: Properties
    let starCount = 3
    var selectedStars = 0 {
        didSet {
            setNeedsLayout()
        }
    }
    var starButtons = [UIButton]()
    
    let spacing = 5
    
    var starChanged : ValueChanged?
    
    // MARK: Initialization
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
        let filledStarImage = UIImage(named: "filledStar")
        let emptyStarImage = UIImage(named: "emptyStar")
        
        for _ in 0..<starCount {
            
            let button = UIButton()
            
            button.setImage(emptyStarImage, for: .normal)
            button.setImage(filledStarImage, for: .selected)
            button.setImage(filledStarImage, for: [.highlighted, .selected])
            button.adjustsImageWhenHighlighted = false
            button.addTarget(self, action: #selector(Star.starButtonTapped(_:)), for: .touchDown)
            starButtons += [button]
            addSubview(button)
        }
    }
    
    override func layoutSubviews() {
        
        // Set the button's width and height to a square the size of the frame's height.
        let buttonSize = Int(frame.size.height)
        
        var buttonFrame = CGRect(x: 0, y: 0, width: buttonSize, height: buttonSize)
        
        // Offset each button's origin by the length of the button plus spacing.
        for (index, button) in starButtons.enumerated() {
            buttonFrame.origin.x = CGFloat(index * (buttonSize + spacing))
            button.frame = buttonFrame
        }
        
        updateButtonSelectionStates()
    }
    
    override var intrinsicContentSize : CGSize {
        
        let buttonSize = Int(frame.size.height)
        let width = (buttonSize * starCount) + (spacing * (starCount - 1))
        
        return CGSize(width: width, height: buttonSize)
    }
    
    
    // MARK: Button Action
    func starButtonTapped(_ button: UIButton) {
        let index = starButtons.index(of: button)!
        
        if (index == 0 && selectedStars == 1) { //unselect the first star
            selectedStars = 0
        } else {
            selectedStars = index + 1
        }
        updateButtonSelectionStates()
        
        if let starChanged = self.starChanged {
            starChanged.changed(self)
        }
    }
    
    func updateButtonSelectionStates() {
        for (index, button) in starButtons.enumerated() {
            // If the index of a button is less than the rating, that button should be selected.
            button.isSelected = index < selectedStars
        }
    }
    
}
