package com.hadiagdamapps.ananas.tools

class LoginModel(val username: String, val password: String)

class UserModel(val loginModel: LoginModel, val picture: String, val name: String)
