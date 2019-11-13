package com.example.appozycze.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.appozycze.R
import com.example.appozycze.database.AppDB
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        deleteAllBooks_Button.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("UWAGA")
                .setMessage("Czy na pewno chcesz usunąć wszystkie książki?")
                .setPositiveButton("OK") { _, _ ->
                    Thread{
                        AppDB.getInstance(this)!!.bookDao().deleteAllBooks()
                    }.start()

                    Toast.makeText(this, "Wszystkie książki zostały usunięte.", Toast.LENGTH_SHORT)
                        .show()
                }
                .setNegativeButton("Anuluj") { _, _ -> }
                .show()
        }

        deleteAllGames_Button.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("UWAGA")
                .setMessage("Czy na pewno chcesz usunąć wszystkie gry?")
                .setPositiveButton("OK") { _, _ ->
                    Thread{
                        AppDB.getInstance(this)!!.bookDao().deleteAllGames()
                    }.start()

                    Toast.makeText(this, "Wszystkie gry zostały usunięte.", Toast.LENGTH_SHORT)
                        .show()
                }
                .setNegativeButton("Anuluj") { _, _ -> }
                .show()
        }
    }
}
