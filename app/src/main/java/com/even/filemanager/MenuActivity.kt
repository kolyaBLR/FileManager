package com.even.filemanager

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import android.widget.TextView
import android.content.Intent
import android.net.Uri
import java.io.File
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.view.View


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
            } else {
                val uri = Uri.parse(containerPath.get().list[i].getAbsolutePath())
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.setDataAndType(uri, "application/octet-stream")
                if (isIntentAvailable(applicationContext, intent)) {
                    startActivity(intent)
                }
            }
        }
        showDir(containerPath.get())
    }

    private fun isIntentAvailable(context: Context, intent: Intent): Boolean {
        val list = context.packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY)
        return !list.isEmpty()
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
