package com.example.gorkob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gorkob.databinding.ActivityRedactBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class Redact : AppCompatActivity() {
    lateinit var binding: ActivityRedactBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRedactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra("party") as mParty

        Picasso.get().load(item.link).fit().into(binding.redImage)

        binding.redLink.setText(item.link)
        binding.redName.setText(item.name)
        binding.redDesc.setText(item.desc)

        binding.redData.setText(item.data)
        binding.redPrice.setText(item.price)



        binding.redParty.setOnClickListener {
            val nomer=mParty(item.id,binding.redName.text.toString(),binding.redDesc.text.toString(),binding.redData.text.toString(),binding.redPrice.text.toString(),binding.redLink.text.toString())
            database.getReference("Party").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child(nomer.id.toString()).exists()){
                        Toast.makeText(this@Redact,"Такая запись уже существует", Toast.LENGTH_SHORT).show()
                    }
                    else{

                        database.getReference("Party").child(item.id.toString()).setValue(nomer)
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
        binding.redImage.setOnClickListener {
            try {
                Picasso.get().load(binding.redLink.text.toString()).fit().into(binding.redImage)
            }catch (ex:Exception){
                Toast.makeText(this,"Нет ссылки на картинку", Toast.LENGTH_SHORT).show()
            }

        }

    }
}