package com.pmgohil.thoughts

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ThoughtsDataAdapter(
    private var context: Context,
    private val thoughtList: List<ThoughtsDataModel>,
    private var imageList: List<ImageModel>
) : RecyclerView.Adapter<ThoughtsDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.thoughts_layout, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {

            val thoughtModel = thoughtList[position]
            val imageModel = imageList[position]

            holder.txt_select_thoughts.text = thoughtModel.thoughts
            Glide.with(holder.itemView).load(imageModel.imageUrl).into(holder.imageView)

            holder.txt_name.text = GlobalVariableFunction.name
            if (GlobalVariableFunction.internal_image != "null" || GlobalVariableFunction.internal_image.isNotEmpty()) {
                Glide.with(holder.user_image_view_on_thoughts)
                    .load(GlobalVariableFunction.internal_image)
                    .error(R.drawable.wrong)
                    .into(holder.user_image_view_on_thoughts)
                holder.user_image_view_on_thoughts.visibility = View.VISIBLE
                holder.ll_thoughts_layout.visibility = View.VISIBLE
            } /*else if (GlobalVariableFunction.mail_id_image != "null" || GlobalVariableFunction.mail_id_image.isNotEmpty()) {
                Glide.with(holder.user_image_view_on_thoughts)
                    .load(GlobalVariableFunction.mail_id_image)
                    .error(R.drawable.wrong)
                    .into(holder.user_image_view_on_thoughts)
                holder.user_image_view_on_thoughts.visibility = View.VISIBLE
                holder.ll_thoughts_layout.visibility = View.VISIBLE
            } */ else {
                GlobalVariableFunction.showToast(context, "wrong")
            }


            holder.image_thoughts_share.setOnClickListener {
                val screenshotBitmap = captureScreenshot(holder.card_view_thoughts_user_data)
                val savedImagePath = saveImageToGallery(screenshotBitmap)
                shareImage(savedImagePath)
            }
            holder.image_thoughts_edit.setOnClickListener {
                GlobalVariableFunction.showToast(context, "Edit Facility Coming Soon...")
            }
            //setupDragAndDrop(holder.user_image_view_on_thoughts, holder.card_view_thoughts_user_data, position)
            holder.user_image_view_on_thoughts.setOnLongClickListener { view ->
                val dragData = ClipData.newPlainText("", "")
                val dragShadowBuilder = View.DragShadowBuilder(view)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.startDragAndDrop(dragData, dragShadowBuilder, view, 0)
                } else {
                    view.startDrag(dragData, dragShadowBuilder, view, 0)
                }
                true
            }
            holder.card_view_thoughts_user_data.setOnDragListener { _, event ->
                when (event.action) {
                    DragEvent.ACTION_DROP -> {
                        val draggedView = event.localState as View
                        val newParent = holder.card_view_thoughts_user_data

                        // Ensure the dragged view is not the same as the new parent
                        if (draggedView.parent != newParent) {
                            // Remove the view from the old parent
                            (draggedView.parent as ViewGroup).removeView(draggedView)

                            // Add the view to the new parent
                            newParent.addView(draggedView)
                            draggedView.visibility = View.VISIBLE

                            // Update the userImagePosition in the ThoughtsData
                            val newPosition = holder.adapterPosition
                            var userImagePosition: Int = -1
                            userImagePosition = newPosition
                            notifyItemChanged(position)
                        }

                        true
                    }

                    else -> false
                }
            }

        } catch (e: Exception) {
            //GlobalVariableFunction.showToast(context, "Array Index: $e")
        }
    }

    override fun getItemCount(): Int {
        return thoughtList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view_thoughts)
        val txt_select_thoughts: TextView = itemView.findViewById(R.id.txt_select_thoughts)
        val user_image_view_on_thoughts: ImageView =
            itemView.findViewById(R.id.user_image_view_on_thoughts)
        val txt_name: TextView = itemView.findViewById(R.id.txt_name)
        val card_view_thoughts_user_data: CardView =
            itemView.findViewById(R.id.card_view_thoughts_user_data)
        val image_thoughts_share: ImageView = itemView.findViewById(R.id.image_thoughts_share)
        val image_thoughts_edit: ImageView = itemView.findViewById(R.id.image_thoughts_edit)
        val ll_thoughts_layout: LinearLayout = itemView.findViewById(R.id.ll_thoughts_layout)
    }

    private fun captureScreenshot(view: View): Bitmap {
        view.isDrawingCacheEnabled = true
        val screenshotBitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false
        return screenshotBitmap
    }

    private fun saveImageToGallery(bitmap: Bitmap): String {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!

        val imageFile = File.createTempFile(
            imageFileName, ".jpg", storageDir
        )

        try {
            val fos = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()

            // Add image to the system gallery
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
            )

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return imageFile.absolutePath
    }

    private fun shareImage(imagePath: String) {
        val imageUri = FileProvider.getUriForFile(
            context, context.packageName + ".provider", File(imagePath)
        )
        val link = "https://pmgohil.site/"
        val message: String =
            "Get your daily thoughts from our \n'Art of Thoughts' Application. \n\n'Art of Thoughts' Download From - $link"
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg"
            putExtra(Intent.EXTRA_STREAM, imageUri)
            putExtra(Intent.EXTRA_TEXT, message) // Add the message here
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }

    private fun setupDragAndDrop(dragView: View, dropTarget: View, position: Int) {
        dragView.setOnLongClickListener {
            val dragData = ClipData.newPlainText("", "")
            val dragShadowBuilder = View.DragShadowBuilder(it)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.startDragAndDrop(dragData, dragShadowBuilder, it, 0)
            } else {
                it.startDrag(dragData, dragShadowBuilder, it, 0)
            }
            true
        }

        dropTarget.setOnDragListener { _, event ->
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    // Handle the drop event
                    val draggedView = event.localState as View
                    val owner = draggedView.parent as ViewGroup
                    owner.removeView(draggedView)

                    // Add the draggedView to the dropTarget
                    val container = event.localState as ViewGroup
                    container.addView(draggedView)
                    draggedView.visibility = View.VISIBLE

                    // Update the user image position in the thoughtsList
                    val newPosition = dropTarget.tag as Int
                    val thoughtsData = thoughtList[position]
                    thoughtList.toMutableList().removeAt(position)
                    thoughtList.toMutableList().add(newPosition, thoughtsData)
                    notifyItemMoved(position, newPosition)


                    true
                }

                else -> false
            }
        }
    }

}