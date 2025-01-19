package com.example.finalyearproject.instances

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthSingleton {
    val authInstance: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
}
