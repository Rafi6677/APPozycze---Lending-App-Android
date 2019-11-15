package com.example.appozycze.activities.games

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.appozycze.R
import com.example.appozycze.activities.books.BooksActivity
import com.example.appozycze.database.AppDB
import com.example.appozycze.database.GameEntity
import kotlinx.android.synthetic.main.activity_add_book.*
import kotlinx.android.synthetic.main.activity_add_game.*

class AddGameActivity : AppCompatActivity() {

    var platform: String = "PC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)

        supportActionBar?.title = "Dodaj grę"

        cancelSavingGame_Button.setOnClickListener {
            finish()
        }

        platform_AddGameRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            platform = when (checkedId) {
                R.id.pcPlatform_AddGameRadioButton -> "PC"
                R.id.ps4Platform_AddGameRadioButton -> "PS4"
                else -> "Xbox One"
            }
        }

        saveGame_Button.setOnClickListener {
            if (checkIfInputsFilledCorrect()) {
                saveGameToDB()
            }
        }
    }

    private fun checkIfInputsFilledCorrect() : Boolean
    {
        if (gameTitle_AddGameInput.text.toString() == "") {
            Toast.makeText(this, "Pola muszą być wypełnione.", Toast.LENGTH_SHORT)
                .show()

            return false
        } else {
            return true
        }
    }

    private fun saveGameToDB()
    {
        val title = gameTitle_AddGameInput.text.toString()
        val status = "AT_HOME"
        val lendTo = ""

        val gameEntity = GameEntity(title, platform, status, lendTo)

        Thread{
            AppDB.getInstance(this)!!.gameDao().saveGame(gameEntity)
        }.start()

        Toast.makeText(this, "Dodano grę", Toast.LENGTH_SHORT)
            .show()

        finish()
        val intent = Intent(this, GamesActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
