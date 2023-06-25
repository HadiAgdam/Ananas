package com.hadiagdamapps.ananas.tools

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Data(private val context: Context) {

    private val mode = 0
    private val loginKey = "user"

    fun getLocalData(): LoginModel? {

        val preferences = context.getSharedPreferences(loginKey, mode)

        val username = preferences.getString("username", null)
        val password = preferences.getString("password", null)

        if (username == null || password == null)
            return null

        return LoginModel(username, password)
    }


    fun updateLocalData(userModel: UserModel) {
        val editor = context.getSharedPreferences(loginKey, mode).edit()

        editor.putString("username", userModel.loginModel.username)
        editor.putString("password", userModel.loginModel.password)
        editor.putString("name", userModel.name)
        editor.putString("picture", userModel.picture)

        editor.apply()
    }

    fun updateLocalData(picture: String, name: String) {

        val editor = context.getSharedPreferences(loginKey, mode).edit()

        editor.putString("picture", picture)
        editor.putString("name", name)

        editor.apply()

    }

    fun clear() {
        val editor = context.getSharedPreferences(loginKey, mode).edit()

        editor.clear()

        editor.apply()
    }


}

class Database : SQLiteOpenHelper {

    val contentTypeText = "text"
    val contentTypeImage = "image"

    constructor(
        context: Context?,
    ) : super(context, "database.db", null, 1)

    fun getInboxes() : ArrayList<InboxModel> {
        val result = ArrayList<InboxModel>()

        val cursor = readableDatabase.rawQuery("select * from inbox", null)
        if (cursor == null || !cursor.moveToFirst()) return result

        do {

            val model = InboxModel(cursor.getString(0), cursor.getString(1), cursor.getString(3), cursor.getInt(4))
            result.add(model)

        }while (cursor.moveToNext())


        return result
    }

    fun getMessages(sender: String) : ArrayList<MessageModel>{

        val result = ArrayList<MessageModel>()

        val cursor = readableDatabase.rawQuery("select * from message where (sender_username='$sender')", null)
        if (cursor == null || !cursor.moveToFirst()) return result

        do {

            val messageModel = MessageModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4) == 1)
            result.add(messageModel)

        } while (cursor.moveToNext())

        cursor.close()

        return result
    }

    fun newMessage(inboxModel: InboxModel, messageModel: MessageModel) {
        // update the inbox
        // insert the new message

        val inbox = readableDatabase.rawQuery("select * from inbox where username == '${inboxModel.username}'", null) ?: return

        if (inbox.moveToNext()) {
            // update inbox
            val count = inbox.getInt(3) + 1
            writableDatabase.execSQL("update inbox set image='${inboxModel.image}', title='${inboxModel.title}', unseen_count=${count} where(username=='${inboxModel.username}') ")
        }
        else {
            // insert inbox
            insertInbox(inboxModel)
        }
        inbox.close()

        insertMessage(messageModel)
    }

    private fun insertMessage(model: MessageModel) {
        writableDatabase.execSQL("insert into message (sender_username, content_type, content, timestamp, me) values ('${model.sender_username}', '${model.content_type}', '${model.content}', '${model.timestamp}', ${if (model.me) 1 else 0} )")
    }

    private fun insertInbox(model: InboxModel) {
        writableDatabase.execSQL("insert into inbox (username, image, title, unseen_count) values ('${model.username}', '${model.image}', '${model.title}', ${model.count}) ")
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table inbox (username text primary key unique, image text, title text, unseen_count integer default 1)")
        db?.execSQL("create table message (sender_username text, content_type text, content text, timestamp text, me boolean)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}

