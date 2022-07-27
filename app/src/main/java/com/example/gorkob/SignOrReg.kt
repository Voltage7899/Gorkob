package com.example.gorkob

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.gorkob.databinding.ActivityMainBinding
import com.example.gorkob.databinding.ActivitySignOrRegBinding
import com.google.firebase.database.*

class SignOrReg : AppCompatActivity() {
    lateinit var binding: ActivitySignOrRegBinding
    private var tableName:String="User"
    private var database:DatabaseReference=FirebaseDatabase.getInstance().getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivitySignOrRegBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.registration.setOnClickListener {
            startActivity(Intent(this,Reg::class.java))
        }

        val database = FirebaseDatabase.getInstance().getReference()
        Log.d(TAG,"database: "+database)

        changeModeTable()
        binding.signSign.setOnClickListener {
            sign()
        }

    }

    fun changeModeTable(){
        binding.modeAdmin.setOnClickListener {
            binding.signSign.setText("Вход как админ")
            tableName="Admin"
            binding.modeAdmin.visibility=GONE
            binding.modeUser.visibility= VISIBLE
        }
        binding.modeUser.setOnClickListener {
            binding.signSign.setText("Вход как Юзер")
            tableName="User"
            binding.modeUser.visibility=GONE
            binding.modeAdmin.visibility= VISIBLE
        }

    }
    fun sign(){

        if(tableName=="User"){

            if (TextUtils.isEmpty(binding.phoneSign.text.toString()) && TextUtils.isEmpty(binding.passSign.text.toString())) {
                Toast.makeText(this@SignOrReg, "Введите все данные", Toast.LENGTH_SHORT).show()
            } else {
                database.child(tableName).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(binding.phoneSign.text.toString()).exists()) {
                            val userCurrentData: UserModel? = snapshot.child(binding.phoneSign.text.toString()).getValue(
                                UserModel::class.java
                            )

                            if (userCurrentData?.phone.equals(binding.phoneSign.text.toString()) && userCurrentData?.pass.equals(binding.passSign.text.toString())) {
                                Toast.makeText(this@SignOrReg, "Вы вошли как Юзер", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@SignOrReg, CommonMode::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@SignOrReg, "Неверные данные", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(this@SignOrReg, "Номера не существует", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }
        else{
            if (TextUtils.isEmpty(binding.phoneSign.text.toString()) && TextUtils.isEmpty(binding.passSign.text.toString())) {
                Toast.makeText(this@SignOrReg, "Введите все данные", Toast.LENGTH_SHORT).show()
            } else {
                database.child(tableName).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.child(binding.phoneSign.text.toString()).exists()) {
                            val userCurrentData: UserModel? = snapshot.child(binding.phoneSign.text.toString()).getValue(UserModel::class.java)


                            if (userCurrentData?.phone.equals(binding.phoneSign.text.toString()) && userCurrentData?.pass.equals(
                                    binding.passSign.text.toString()))
                            {
                                Toast.makeText(this@SignOrReg, "Вы вошли как админ", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@SignOrReg, AdvancedMode::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@SignOrReg, "Неверные данные", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(this@SignOrReg, "Номера не существует", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }



    }


}