package com.example.finalyearproject.modals

class AddHostelModal:java.io.Serializable{
    var name:String?=null
    var email:String?=null
    var contact:String?=null
    var city:String?=null
    var province:String?=null
    var beds:String?=null
    var baths:String?=null
    var sqft:String?=null
    var address:String?=null
    var desc:String?=null
    var hotselfor:String?=null
    var image:String?=null
    var type:String?=null
    var pushkey:String?=null

    constructor()

    constructor(name:String?,email:String,contact:String?,city:String?,province:String?,beds:String?
                ,baths:String?,sqft:String?,address:String?,desc:String?
                ,hotselfor:String?,image:String?,tyoe:String?,pushkey:String?){
        this.name=name
        this.email=email
        this.contact=contact
        this.city=city
        this.province=province
        this.beds=beds
        this.baths=baths
        this.sqft=sqft
        this.address=address
        this.desc=desc
        this.hotselfor=hotselfor
        this.type=type
        this.image=image
        this.pushkey=pushkey
    }
}