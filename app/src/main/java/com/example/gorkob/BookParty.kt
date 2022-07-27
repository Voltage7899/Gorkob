package com.example.gorkob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gorkob.databinding.ActivityBookPartyBinding
import com.squareup.picasso.Picasso

class BookParty : AppCompatActivity() {
    lateinit var binding: ActivityBookPartyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBookPartyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item= intent.getSerializableExtra("party") as mParty
        Picasso.get().load(item.link).fit().into(binding.bookImage)

        binding.bookName.setText(item.name)
        binding.bookDesc.setText(item.desc)

        binding.bookData.setText(item.data)
        binding.bookPrice.setText(item.price)

        binding.bookParty.setOnClickListener {
            Toast.makeText(this,"Забронировано", Toast.LENGTH_LONG).show()
            finish()
        }


    }
}