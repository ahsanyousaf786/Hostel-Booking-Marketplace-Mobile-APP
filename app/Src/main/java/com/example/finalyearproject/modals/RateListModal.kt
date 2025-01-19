package com.example.fine_delivery.modals

class RateListModal:java.io.Serializable{
    var location:String?=null
    var price:String?=null
    var key:String?=null

    constructor(){}

    constructor(location:String?,price:String?,key:String?){
        this.location=location
        this.price=price
        this.key=key
    }
}
