package com.example.project9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project9.view_modal.data_modal

class MainActivity : AppCompatActivity() {

    var arr = ArrayList<data_modal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
//        val arrData: Array<String> = arrayOf("a", "b", "c", "d", "e", "f")


//        var d1 = data_modal()
//        d1.a1 = "Rajkot"
//        d1.a2 = 360004

//        arr.add(modal("a",1))
//        arr.add(modal("b",2))
//        arr.add(modal("c",3))

        //   val a = arrData.size
        val adapter = CustomAdapter(this, arr)
        recycler.adapter = adapter


        modal1("Rajkot", 1)
        modal1("Surat", 2)
        modal1("Vadodara", 3)
        modal1("Jetput", 4)
        modal1("Jamnagar", 5)

    }

    fun modal1(b1: String, b2: Int){
        var dataModal = data_modal()
        dataModal.a1 = b1
        dataModal.a2 = b2
        arr.add(dataModal)
    }
fun modal2(b1 : String, b2 : Int) : data_modal{
    var dataModa2 = data_modal()
    dataModa2.a1 = b1
    dataModa2.a2 = b2
    return dataModa2
}
}