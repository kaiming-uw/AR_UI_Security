//
//  ARComponent.swift
//  OverlappingObjects
//
//  Created by stlp on 5/17/22.
//

import Foundation

protocol ARComponent {
    func launch() -> Void
    func next(expNum: Int) -> Void
    func eraseText() -> Void
}

