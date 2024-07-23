package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
//        var arrData: Array<String> = arrayOf(
//            "Abarth",
//            "Acura",
//            "Alfa Romeo",
//            "Aston Martin",
//            "Audi",
//            "Bentley",
//            "BMW",
//            "Buick",
//            "Cadillac",
//            "Chevrolet",
//            "Chrysler",
//            "Citroen",
//            "Dacia",
//            "Dodge",
//            "Ferrari",
//            "Fiat",
//            "Ford",
//            "GMC",
//            "Honda",
//            "Hummer",
//            "Hyundai",
//            "Infiniti",
//            "Isuzu",
//            "Jaguar",
//            "Jeep",
//            "Kia",
//            "Lamborghini",
//            "Lancia",
//            "Land Rover",
//            "Lexus",
//            "Lincoln",
//            "Lotus",
//            "Maserati",
//            "Mazda",
//            "Mercedes-Benz",
//            "Mercury",
//            "Mini",
//            "Mitsubishi",
//            "Nissan",
//            "Opel",
//            "Peugeot",
//            "Pontiac",
//            "Porsche",
//            "Ram",
//            "Renault",
//            "Saab",
//            "Saturn",
//            "Scion",
//            "Seat",
//            "Skoda",
//            "Smart",
//            "SsangYong",
//            "Subaru",
//            "Suzuki",
//            "Tesla",
//            "Toyota",
//            "Volkswagen",
//            "Volvo",
//            "Wiesmann",
//        )
//        val length = arrData.size

        val arrData = ArrayList<String>()
        arrData.add("Test")

        val hello = "Hello"
        arrData.add(hello)

        val adapter = adapterClass(this, arrData)
        recyclerView.adapter = adapter
    }
}