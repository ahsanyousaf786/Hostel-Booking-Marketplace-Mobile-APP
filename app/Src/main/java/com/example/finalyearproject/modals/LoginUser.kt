package com.example.fine_delivery.modals

class LoginUser{
   var email:String?=null
   var password:String?=null

   constructor()

   constructor(email:String,password:String?){
      this.email=email
      this.password=password
   }
}

