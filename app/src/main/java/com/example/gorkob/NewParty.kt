package com.example.gorkob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gorkob.databinding.ActivityNewPartyBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlin.random.Random

class NewParty : AppCompatActivity() {
    private lateinit var binding: ActivityNewPartyBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNewPartyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addParty.setOnClickListener {

            val id = Random.nextInt(1,100000)

            val nomer=mParty(id.toString(),binding.addName.text.toString(),binding.addDesc.text.toString(),binding.addData.text.toString(),binding.addPrice.text.toString(),binding.addLink.text.toString())

            database.getReference("Party").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(nomer.id.toString()).exists()){
                        Toast.makeText(this@NewParty,"Уже есть", Toast.LENGTH_SHORT).show()
                    }
                    else{

                        database.getReference("Party").child(id.toString()).setValue(nomer)
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        binding.addImage.setOnClickListener {
            try {
                Picasso.get().load(binding.addLink.text.toString()).fit().into(binding.addImage)
            }catch (ex:Exception){
                Toast.makeText(this,"Нет ссылки на картинку", Toast.LENGTH_SHORT).show()
            }

        }

    }
}