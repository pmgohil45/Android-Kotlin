package com.example.design2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager


class MainActivity : AppCompatActivity() {
    private var foodArrayList = ArrayList<FoodModel>()
    private lateinit var mainPartAdapter: FoodPartAdapter
    private lateinit var mainRecyclerView: RecyclerView

    //private lateinit var mainRecyclerView2: RecyclerView
    private lateinit var recyclerview: RecyclerView
    private var referArray = ArrayList<ReferEarnModel>()
    private lateinit var foodRefer: FoodRefer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainRecyclerView = findViewById(R.id.mainRecyclerview) //for the food list
        //  mainRecyclerView2 = findViewById(R.id.mainRecyclerview2) //for the earn nd refer
        recyclerview = findViewById(R.id.recyclerview)
        foodCategoryList()
        foodList()
        // referEarn()
    }

    //  function for the food category list adapter
    private fun foodCategoryList() {
        var foodCategoryArray: Array<String> = arrayOf(
            "Vegetables", "Fruits", "Grains", "legumes", "nuts", "seafood", "Dairy foods", "Eggs"
        )
        val foodCataeforyAdapter = FoodCategoryAdapter(this, foodCategoryArray)
        val horizontal = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerview.layoutManager = horizontal
        recyclerview.adapter = foodCataeforyAdapter
    }

    //  function for the food manu list adapter
    private fun foodList() {
        addfooditem("file:///android_asset/img1.png", "Flower", 50F)
        addfooditem("file:///android_asset/img2.png", "Tometo", 40F)
        addfooditem("file:///android_asset/img3.png", "Stobery", 60F)
        addfooditem("file:///android_asset/img4.png", "Mirch", 80F)
        addfooditem("file:///android_asset/img2.png", "Tometo", 40F)
        addfooditem("file:///android_asset/img3.png", "Stobery", 60F)
        mainPartAdapter = FoodPartAdapter(this, foodArrayList)
        mainRecyclerView.layoutManager = GridLayoutManager(this, 2) //LinearLayoutManager(this)
        mainRecyclerView.adapter = mainPartAdapter
    }

    //  function for the data calling using view model
    fun addfooditem(foodImg: String, foodName: String, foodPrice: Float) {
        val view = FoodModel()
        view.foodImg = foodImg
        view.foodName = foodName
        view.foodPrice = foodPrice
        foodArrayList.add(view)
    }

    //code for the foodRefer Adapter class
    /* private fun referEarn() {
         addReferAndEarn("file:///android_asset/contact1.png", "Refer and Earn", "prakash gohil")
         mainRecyclerView.layoutManager = LinearLayoutManager(this)
         foodRefer = FoodRefer(this@MainActivity, referArray)
         mainRecyclerView.adapter = foodRefer
     }

     // viewModel
     fun addReferAndEarn(contactImg: String, referAndEarn: String, referDescription: String) {
         val viewModel = ReferEarnModel()
         viewModel.contactImg = contactImg
         viewModel.referAndEarn = referAndEarn
         viewModel.referDescription = referDescription
         referArray.add(viewModel)
     }*/
}