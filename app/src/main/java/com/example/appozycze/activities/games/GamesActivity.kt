package com.example.appozycze.activities.games

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appozycze.R
import com.example.appozycze.activities.MainActivity
import com.example.appozycze.activities.SettingsActivity
import com.example.appozycze.activities.books.BooksActivity
import com.example.appozycze.database.AppDB
import com.example.appozycze.database.GameEntity
import com.example.appozycze.viewmodels.GameFilter
import com.example.appozycze.viewmodels.GameItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_games.*
import java.lang.IllegalStateException

class GamesActivity : AppCompatActivity() {

    private val adapter = GroupAdapter<ViewHolder>()
    private var gamesList: List<GameEntity> ?= null
    private var gameFilter = GameFilter.Title

    companion object {
        const val GAME_KEY = "gameKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        supportActionBar!!.title = "Gry"

        addGame_Button.setOnClickListener {
            val intent = Intent(this, AddGameActivity::class.java)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        gameList_RecyclerView.setHasFixedSize(true)
        gameList_RecyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(gameList_RecyclerView.context, layoutManager.orientation)
        gameList_RecyclerView.addItemDecoration(dividerItemDecoration)

        setupData()
    }

    private fun setupData() {
        Thread{
            val sortedList: List<GameEntity>
            gamesList = AppDB.getInstance(this)!!.gameDao().getGames()

            when (gameFilter) {
                GameFilter.Title -> {
                    sortedList = gamesList!!.sortedBy {
                        it.gameTitle
                    }
                }
                GameFilter.Platform -> {
                    sortedList = gamesList!!.sortedBy {
                        it.gamePlatform
                    }
                }
                GameFilter.Status -> {
                    sortedList = gamesList!!.sortedBy {
                        it.gameStatus
                    }
                }
            }

            runOnUiThread {
                adapter.clear()

                sortedList.forEach {
                    adapter.add(GameItem(it))
                }

                adapter.setOnItemClickListener { item, _ ->
                    val gameItem = item as GameItem
                    val id = gameItem.game.gameID
                    val intent = Intent(this, EditGameActivity::class.java)
                    intent.putExtra(GAME_KEY, id)
                    startActivity(intent)
                }

                adapter.setOnItemLongClickListener { item, _ ->
                    val gameItem = item as GameItem

                    AlertDialog.Builder(this)
                        .setTitle("UWAGA")
                        .setMessage("Czy na pewno chcesz usunąć tę grę?")
                        .setPositiveButton("OK") { _, _ ->
                            Thread {
                                AppDB.getInstance(this)!!.gameDao().deleteGame(gameItem.game)

                                runOnUiThread {
                                    adapter.clear()
                                    setupData()
                                }
                            }.start()

                            Toast.makeText(this, "Gra została usunięta.", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .setNegativeButton("Anuluj") { _, _ -> }
                        .show()

                    item.isLongClickable
                }
                gameList_RecyclerView.adapter = adapter
            }

        }.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.games, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.books_section -> {
                finish()
                val intent = Intent(this, BooksActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                return true
            }
            R.id.settings -> {
                finish()
                val intent = Intent(this, SettingsActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                return true
            }
            R.id.games_filter -> {
                openFilterDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        finish()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        super.onBackPressed()
    }

    private fun openFilterDialog(){
        val values = arrayOf<CharSequence>(" Tytuł ", " Platforma ", " Status ")
        var checkedItem: Int = -1

        checkedItem = when (gameFilter) {
            GameFilter.Title -> 0
            GameFilter.Platform -> 1
            GameFilter.Status -> 2
        }

        AlertDialog.Builder(this)
            .setTitle("Sortuj wg:")
            .setSingleChoiceItems(values, checkedItem) { dialog, which ->
                when (which) {
                    0 -> {
                        gameFilter = GameFilter.Title
                        dialog.dismiss()
                        try {
                            setupData()
                        } catch (e: IllegalStateException){}
                    }
                    1 -> {
                        gameFilter = GameFilter.Platform
                        dialog.dismiss()
                        try {
                            setupData()
                        } catch (e: IllegalStateException){}

                    }
                    2 -> {
                        gameFilter = GameFilter.Status
                        dialog.dismiss()
                        try {
                            setupData()
                        } catch (e: IllegalStateException){}
                    }
                }
            }
            .show()
    }
}
