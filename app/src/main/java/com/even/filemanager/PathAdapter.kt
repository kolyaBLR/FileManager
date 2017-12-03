package com.even.filemanager

import android.content.Context
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.io.File

class PathAdapter(context: MenuActivity, private val resource: Int,
                  private val data: ContainerPath):
        ArrayAdapter<DirectoryItem>(context as Context, resource, data.get().list) {

    private var mInflater: LayoutInflater = LayoutInflater.from(context as Context)

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val myView = view ?: mInflater.inflate(resource, parent, false)

        val path = myView.findViewById<TextView>(R.id.item_path)
        path.text = data.get().list[position].getPreviewPath()
                .replace( data.get().getAbsolutePath(), "").substring(1)

        val size = myView.findViewById<TextView>(R.id.item_size)
        val picture = myView.findViewById<ImageView>(R.id.item_picture)

        if (File(data.get().list[position].getAbsolutePath()).isFile) {
            size.visibility = View.VISIBLE
            size.text = data.get().list[position].getFormatSize()
            picture.setImageResource(R.drawable.ic_insert_drive_file_black_24dp)
        } else {
            size.visibility = View.GONE
            picture.visibility = View.VISIBLE
            picture.setImageResource(R.drawable.ic_folder_black_24dp)
        }

        return myView
    }
}