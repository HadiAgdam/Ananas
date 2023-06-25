package com.hadiagdamapps.ananas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hadiagdamapps.ananas.tools.ActivityParent
import com.hadiagdamapps.ananas.tools.Data
import com.hadiagdamapps.ananas.tools.Database
import com.hadiagdamapps.ananas.tools.InboxModel
import com.hadiagdamapps.ananas.tools.InboxRecyclerAdapter

class MainActivity : ActivityParent(R.layout.activity_main) {


    lateinit var searchView: SearchView
    lateinit var recycler: RecyclerView
    lateinit var adapter: InboxRecyclerAdapter
    private lateinit var database: Database

    private fun updateContent() {
        val list = database.getInboxes()
        val adapter = InboxRecyclerAdapter(list, this)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

    }


    private fun poll() {

    }

    override fun initialView() {
        searchView = findViewById(R.id.searchView)
        recycler = findViewById(R.id.recycler)
    }

    override fun main() {
        database = Database(this)
        updateContent()
        poll()
    }
}