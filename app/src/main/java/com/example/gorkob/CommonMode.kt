package com.example.gorkob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.gorkob.databinding.ActivityCommonModeBinding
import com.google.firebase.database.FirebaseDatabase

class CommonMode : AppCompatActivity() ,Adapter.ClickListener{
    lateinit var binding: ActivityCommonModeBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ListAdapter:Adapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCommonModeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recUser.layoutManager= GridLayoutManager(this,2)
        ListAdapter = Adapter(this)
        binding.recUser.adapter=ListAdapter
        ListAdapter?.loadListToAdapter(getData())
    }

    fun getData():ArrayList<mParty>{



        val List=ArrayList<mParty>()
        database.getReference("Party").get().addOnSuccessListener {
            for (i in it.children){
                var party=i.getValue(mParty::class.java)
                if(party!=null){
                    List.add(party)
                    ListAdapter?.loadListToAdapter(List)
                }

            }
        }
        return List
    }
    override fun onClick(party: mParty) {
        startActivity(Intent(this, BookParty::class.java).apply {
            putExtra("party",party)

        })
    }
    override fun onStart() {
        super.onStart()
        ListAdapter?.loadListToAdapter(getData())
    }
}