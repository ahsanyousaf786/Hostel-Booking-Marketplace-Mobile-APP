package com.example.fine_delivery.modals

class BookHostelModal{
   var name:String?=null
   var email:String?=null
   var contact:String?=null
   var hotelName:String?=null
   var baths:String?=null
   var beds:String?=null
   var type:String?=null
   var date:String?=null
   var key:String?=null

   constructor()

   constructor(name:String?,email:String,contact:String?,hotelName:String?,
               baths:String?,beds:String?,type:String?,date:String?,key:String?){
      this.name=name
      this.email=email
      this.contact=contact
      this.hotelName=hotelName
      this.baths=baths
      this.beds=beds
      this.type=type
      this.date=date
      this.key=key
   }
}

