@file:Suppress("DEPRECATION")

package com.example.whacamole.prefs

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit

private const val PREF_SCORE = "score"

object AppPrefs {

    //Получение рекорда пользователя
    fun getScore(context: Context): Int {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getInt(PREF_SCORE, 0)
    }

    fun setScore(context: Context, score: Int) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit {
                putInt(PREF_SCORE, score)
            }
    }

}