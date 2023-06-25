package com.hadiagdamapps.ananas.tools

class LoginModel(val username: String, val password: String)

class UserModel(val loginModel: LoginModel, val picture: String, val name: String)

class InboxModel(val username: String, val title: String, val image: String, val count: Int = -1)

public class MessageModel(val sender_username: String, val content_type: String, val content: String, val timestamp: String, val me: Boolean = false)

