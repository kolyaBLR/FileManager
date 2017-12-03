package com.even.filemanager

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import android.widget.TextView

class MenuActivity : AppCompatActivity() {

    private lateinit var Path: TextView
    private lateinit var Directory: ListView

    private lateinit var containerPath: ContainerPath

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val path = Environment.getExternalStorageDirectory().absolutePath
        containerPath = ContainerPath(path, getFilterPath())

        Path = findViewById(R.id.path) as TextView
        Directory = findViewById(R.id.direcroty) as ListView
        Directory.setOnItemClickListener { _, _, i, _ ->
            if (!containerPath.isFile(containerPath.get().list[i])) {
                containerPath.openChild(i)
                showDir(containerPath.get())
            }
        }
        showDir(containerPath.get())
    }

    override fun onBackPressed() = if (!containerPath.isParent()) {
        containerPath.openParent()
        showDir(containerPath.get())
    } else {
        super.onBackPressed()
    }

    private fun showDir(item: DirectoryItem) {
        Path.text = item.getAbsolutePath()
        Directory.adapter = PathAdapter(this,
                R.layout.item_path, containerPath)
    }

    private fun getFilterPath(): ArrayList<String> = ArrayList<String>().apply {
        //add(".mp3")
    }
}
