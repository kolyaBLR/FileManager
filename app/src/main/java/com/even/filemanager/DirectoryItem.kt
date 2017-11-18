package com.even.filemanager

import java.io.File

class DirectoryItem(private var path: String = "",
                    val parent: DirectoryItem? = null,
                    var list: ArrayList<DirectoryItem> = ArrayList<DirectoryItem>(),
                    var visible: Boolean = false) {

    fun getAbsolutePath(): String = File(path).absolutePath

    public fun getPreviewPath(): String {
        var path = File(path).path
        if (list.count() == 1 && !File(list[0].getPreviewPath()).isFile) {
            path = list[0].getPreviewPath()
        }
        return path
    }

    fun setPath(path: String) {
        this.path = path
    }
}
