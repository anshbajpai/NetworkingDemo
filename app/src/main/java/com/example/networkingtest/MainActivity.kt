package com.example.networkingtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val originalList = arrayListOf<User>()

    val adapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter.onItemClick = {
            val intent = Intent(this,UserActivity::class.java)
            intent.putExtra("ID",it)
            startActivity(intent)
        }

        userRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO){Client.api.getUsers() }
            if(response.isSuccessful){
                response.body()?.let {
                    adapter.swapData(it)
                }
            }
        }



    }
}