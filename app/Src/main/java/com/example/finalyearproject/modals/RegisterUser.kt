package com.example.fine_delivery.modals

class RegisterUser{
   var name:String?=null
   var email:String?=null
   var pushKey:String?=null
   var password:String?=null

   constructor()

   constructor(name:String?,email:String,pushKey:String?,password:String?){
      this.name=name
      this.email=email
      this.pushKey=pushKey
      this.password=password
   }
}

