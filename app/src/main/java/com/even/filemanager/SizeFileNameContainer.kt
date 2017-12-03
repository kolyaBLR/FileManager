package com.even.filemanager

import android.util.Log
import java.util.HashMap

class SizeFileNameContainer(private val size: Long) {

    fun getPreviewNameSize(): String {
        val container = getContainerName()
        var result = "error read size"
        for (item in container) {
            val power = pow(10, item.key)
            if (size > power) {
                result = "${size / power} ${item.value}"
            }
        }
        return result
    }

    private fun getContainerName() = HashMap<Int, String>().apply {
        put(0, "Б")
        put(3, "Кбайт")
        put(6, "Мбайт")
        put(9, "Гбайт")
        put(12, "Тбайт")
        put(15, "Пбайт")
        put(18, "Эбайт")
        put(21, "Збайт")
        put(24, "Ибайт")
    }

    private fun pow(number: Long, power: Int): Long {
        var i = 0
        var result: Long = 1
        while (power > i++) {
            result *= number
        }
        return result
    }
}