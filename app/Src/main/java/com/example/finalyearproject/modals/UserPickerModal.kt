package com.example.fine_delivery.modals

import java.io.Serializable

class UserPickerModal: Serializable {
    var name:String?=null
    var phone:String?=null
    var house_no:String?=null
    var address:String?=null
    var delivery_address:String?=null
    var landmark:String?=null
    var order:String?=null
    var pieces:String?=null
    var weight:String?=null

    constructor(){}

    constructor(name:String?,phone:String?,house_no:String?,address:String?,delivery_address:String?,landmark:String?,order:String?
    ,pieces:String?,weight:String?){
        this.name=name
        this.phone=phone
        this.house_no=house_no
        this.address=address
        this.delivery_address=delivery_address
        this.landmark=landmark
        this.order=order
        this.pieces=pieces
        this.weight=weight


    }

}