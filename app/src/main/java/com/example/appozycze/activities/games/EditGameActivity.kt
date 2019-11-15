package com.example.appozycze.activities.games

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.appozycze.R
import com.example.appozycze.database.AppDB
import com.example.appozycze.database.GameEntity
import kotlinx.android.synthetic.main.activity_edit_game.*
import java.text.SimpleDateFormat
import java.util.*

class EditGameActivity : AppCompatActivity() {

    private var id = 0
    private var game: GameEntity ?= null
    private var platform: String = "PC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_game)

        supportActionBar!!.title = "Edycja gry"

        id = intent.getIntExtra(GamesActivity.GAME_KEY, 0)

        Thread{
            game = AppDB.getInstance(this)!!.gameDao().getSingleGame(id)
            platform = game!!.gamePlatform

            runOnUiThread {
                gameTitle_EditGameInput.setText(game!!.gameTitle)
                platform_EditGameRadioGroup.check(when (game!!.gamePlatform) {
                    "PC" -> R.id.pcPlatform_EditGameRadioButton
                    "PS4" -> R.id.ps4Platform_EditGameRadioButton
                    else -> R.id.xboxOnePlatform_EditGameRadioButton
                })

                platform_EditGameRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                    platform = when (checkedId) {
                        R.id.pcPlatform_EditGameRadioButton -> "PC"
                        R.id.ps4Platform_EditGameRadioButton -> "PS4"
                        else -> "Xbox One"
                    }
                }

                if (game!!.gameStatus == "AT_HOME") {
                    gameStatus_EditTextView.text = "Wypożycz dla:"
                    gameDateStatus_EditTextView.visibility = View.INVISIBLE
                    lendGame_EditButton.text = "Wypożycz"
                    lendName_GameEditTextInput.isClickable = true

                    lendGame_EditButton.setOnClickListener {

                    }
                } else {
                    gameStatus_EditTextView.text = "Wypożyczona dla:"
                    gameDateStatus_EditTextView.visibility = View.VISIBLE
                    gameDateStatus_EditTextView.text = "Dnia: ${game!!.gameLendDate}"
                    lendName_GameEditTextInput.setText(game!!.gameLendTo)
                    lendName_GameEditTextInput.isClickable = false
                    lendGame_EditButton.text = "Zgłoś zwrot"

                    lendGame_EditButton.setOnClickListener {

                    }
                }

                lendGame_EditButton.setOnClickListener {
                    if (game!!.gameStatus == "AT_HOME") {
                        if (lendName_GameEditTextInput.text.toString() == "") {
                            Toast.makeText(this, "Musi być podane imię i nazwisko", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            val date = Date()
                            val formatter = SimpleDateFormat("dd-MM-yyyy")
                            val dateText: String = formatter.format(date)

                            Thread{
                                AppDB.getInstance(this)!!.gameDao()
                                    .changeGameStatusToBorrowed(id, "BORROWED", lendName_GameEditTextInput.text.toString(), dateText)
                            }.start()

                            Toast.makeText(this, "Gra wypożyczona", Toast.LENGTH_SHORT)
                                .show()

                            finish()
                            val intent = Intent(this, GamesActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    } else {
                        Thread{
                            AppDB.getInstance(this)!!.gameDao()
                                .changeGameStatusToReturned(id, "AT_HOME", "")
                        }.start()

                        Toast.makeText(this, "Gra zwrócona", Toast.LENGTH_SHORT)
                            .show()

                        finish()
                        val intent = Intent(this, GamesActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }

                saveGame_Button.setOnClickListener {
                    val title = gameTitle_EditGameInput.text.toString()

                    if (title == game!!.gameTitle && platform == game!!.gamePlatform) {
                        Toast.makeText(this, "Nie dokonano żadnych zmian.", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Thread{
                            AppDB.getInstance(this)!!.gameDao()
                                .updateGameData(id, title, platform)
                        }.start()

                        finish()
                        val intent = Intent(this, GamesActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }

                deleteGame_Button.setOnClickListener {
                    AlertDialog.Builder(this)
                        .setTitle("UWAGA")
                        .setMessage("Czy na pewno chcesz usunąć tę grę?")
                        .setPositiveButton("OK") { _, _ ->
                            Thread{
                                AppDB.getInstance(this)!!.gameDao().deleteGame(game!!)
                            }.start()

                            Toast.makeText(this, "Gra została usunięta.", Toast.LENGTH_SHORT)
                                .show()

                            finish()
                            val intent = Intent(this, GamesActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                        .setNegativeButton("Anuluj") { _, _ -> }
                        .show()
                }
            }
        }.start()
    }
}
