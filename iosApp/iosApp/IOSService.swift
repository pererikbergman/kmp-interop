//
//  IOSService.swift
//  iosApp
//
//  Created by Per-Erik Bergman on 8/9/2567 BE.
//  Copyright © 2567 BE orgName. All rights reserved.
//

import Foundation
import ComposeApp

class IOSService: Service {
    func onDone(callback: @escaping (String) -> Void) {
        callback("Waiting for iOS!")
        DispatchQueue.global().asyncAfter(deadline: .now() + 3.0) {
            callback("iOS Done!")
        }
    }
}
