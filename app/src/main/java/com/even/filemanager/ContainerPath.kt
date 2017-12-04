package com.even.filemanager

import java.io.File
import java.util.*

class ContainerPath(path: String = "/", val filterType: ArrayList<String>) {

    private var directory = DirectoryItem()

    fun get(): DirectoryItem = directory
    fun openChild(i: Int) {
        directory = directory.list[i]
        if (directory.list.count() == 1 && !isFile(directory.list[0])) {
            openChild(0)
        }
    }
    fun openParent() {
        directory = directory.parent!!
    }
    fun isFile(it: DirectoryItem = directory): Boolean
            = File(it.getAbsolutePath()).isFile
    fun isParent(it: DirectoryItem = directory): Boolean
            = it.parent == null

    init {
        directory.setPath(path)
        directory.list = getListDir(directory)
        clearNoFilterPath(directory)
    }

    protected fun getListDir(item: DirectoryItem): ArrayList<DirectoryItem> {
        val array = ArrayList<DirectoryItem>()
        val file = File(item.getAbsolutePath())
        try {
            for (it in file.list()) {
                val newFile = File(file, it)
                val newItem = DirectoryItem(newFile.absolutePath, item)
                if (newFile.isFile && isFilterType(getType(newFile.name))) {
                    checkingDir(newItem)
                }
                if (newFile.isDirectory) {
                    newItem.list = getListDir(newItem)
                }
                array.add(newItem)
            }
        } catch (ex: Exception) {
            return array
        }
        return array
    }

    protected fun checkingDir(item: DirectoryItem) {
        if (!item.visible) {
            item.visible = true
            item.parent?.let { checkingDir(it) }
        }
    }

    protected fun getType(name: String): String = try {
        name.substring(name.lastIndexOf('.'), name.length)
    } catch (ignored: Exception) {
        ""
    }

    protected fun isFilterType(type: String): Boolean =
            filterType.size == 0 || filterType.contains(type)

    protected fun clearNoFilterPath(item: DirectoryItem) {
        var i = 0
        while (i < item.list.count()) {
            if (!item.list[i].visible) {
                item.list.remove(item.list[i--])
            } else {
                clearNoFilterPath(item.list[i])
            }
            i++
        }
    }
}
