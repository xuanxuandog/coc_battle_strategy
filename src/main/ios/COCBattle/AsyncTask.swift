//
//  AsyncTask.swift
//  COCBattle
//
//  Created by xualu on 11/7/16.
//  Copyright © 2016 xualu. All rights reserved.
//

import Foundation

enum AsyncTaskState {
    case INIT, RUNNING, DONE, ERROR, TIMEOUT
}

protocol AsyncTask {
    
    func getState() -> AsyncTaskState
    
    func getResult() -> Any?
}
