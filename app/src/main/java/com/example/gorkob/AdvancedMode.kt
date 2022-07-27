package com.example.gorkob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gorkob.databinding.ActivityAdvancedModeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdvancedMode : AppCompatActivity() ,Adapter.ClickListener{
    lateinit var binding: ActivityAdvancedModeBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ListAdapter:Adapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdvancedModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newAdmin.setOnClickListener {
            startActivity(Intent(this,NewParty::class.java))
        }



        binding.recAdmin.layoutManager= GridLayoutManager(this,2)
        ListAdapter= Adapter(this)
        binding.recAdmin.adapter=ListAdapter
        ListAdapter?.loadListToAdapter(getData())

        val simpleCallback =object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id =ListAdapter?.deleteItem(viewHolder.adapterPosition)
                database.getReference("Party").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (id != null) {
                            database.getReference("Party").child(id.toString()).removeValue()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            }

        }
        val itemTouchHelper= ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recAdmin)
    }


    fun getData():ArrayList<mParty>{



        val List=ArrayList<mParty>()
        database.getReference("Party").get().addOnSuccessListener {
            for (el in it.children){
                var party=el.getValue(mParty::class.java)
                if(party!=null){
                    List.add(party)
                    ListAdapter?.loadListToAdapter(List)
                }

            }
        }
        return List
    }
    override fun onClick(party: mParty) {
        startActivity(Intent(this, Redact::class.java).apply {
            putExtra("party",party)

        })
    }

    override fun onStart() {
        super.onStart()
        ListAdapter?.loadListToAdapter(getData())
    }
}