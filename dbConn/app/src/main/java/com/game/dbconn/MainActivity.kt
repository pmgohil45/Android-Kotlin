package com.game.dbconn


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var myRef: DatabaseReference
    //lateinit var binding: ActivityMainBinding

    lateinit var id: EditText
    lateinit var nm: EditText
    lateinit var mail: EditText
    lateinit var btnIns: Button
    lateinit var btnSel: Button
    lateinit var btnUpd: Button
    lateinit var btnDel: Button
    lateinit var tvId: TextView
    lateinit var tvNm: TextView
    lateinit var tvMail: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        id = findViewById(R.id.etId)
        nm = findViewById(R.id.etNm)
        mail = findViewById(R.id.etMail)
        btnIns = findViewById(R.id.ins)
        btnSel = findViewById(R.id.sel)
        btnUpd = findViewById(R.id.upd)
        btnDel = findViewById(R.id.del)
        tvId = findViewById(R.id.SelTvId)
        tvNm = findViewById(R.id.SelTvNm)
        tvMail = findViewById(R.id.SelTvMail)
        myRef = FirebaseDatabase.getInstance().getReference("data")

        btnIns.setOnClickListener() { insertData() }
        btnSel.setOnClickListener() { selectData() }
        btnUpd.setOnClickListener() { updateData() }
        btnDel.setOnClickListener() { deleteData() }

    }

    private fun insertData() {
        val i = id.text.toString()
        val n = nm.text.toString()
        val m = mail.text.toString()


        if (i.isNotEmpty() && n.isNotEmpty() && m.isNotEmpty()) {
            val dataM = dataModel(i, n, m)
            myRef.child(i).setValue(dataM).addOnSuccessListener {
                Toast.makeText(this, "successfully insert", Toast.LENGTH_SHORT).show()
                clearData()
            }.addOnFailureListener {
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            id.error = "Enter a id"
            nm.error = "Enter a name"
            mail.error = "Enter a mail"
        }
    }

    private fun selectData() {

        val userId: String = id.text.toString()
        if (userId.isNotEmpty()) {
            //readData(sid)
            myRef.child(userId).get().addOnSuccessListener {
                if (it.exists()) {
                    val id = it.child("id").value
                    val nm = it.child("nm").value
                    val mail = it.child("mail").value
                    Toast.makeText(this, "successfully select", Toast.LENGTH_SHORT).show()
                    clearData()
                    tvId.text = id.toString()
                    tvNm.text = nm.toString()
                    tvMail.text = mail.toString()
                } else {
                    Toast.makeText(this, "Not Ezists", Toast.LENGTH_SHORT).show()
                }
                clearData()
            }.addOnFailureListener {
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateData() {
        val i = id.text.toString()
        val n = nm.text.toString()
        val m = mail.text.toString()
        val userId: String = id.text.toString()
        val updateData = mapOf(
            "id" to i,
            "nm" to n,
            "mail" to m
        )
        myRef.child(userId).updateChildren(updateData).addOnSuccessListener {
            Toast.makeText(this, "successfully insert", Toast.LENGTH_SHORT).show()
            clearData()
        }.addOnFailureListener {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteData() {

        val sid: String = id.text.toString()
        myRef.child(sid).get().addOnSuccessListener {
            myRef.child(sid).removeValue()
        }.addOnFailureListener {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearData() {
        id.text.clear()
        nm.text.clear()
        mail.text.clear()
        id.requestFocus()
    }
}
