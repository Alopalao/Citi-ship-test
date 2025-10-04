package com.example.cittyapp
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.vector.VectorProperty
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

private const val INITIAL_TIP_PERCENT = 10
class MainActivity : ComponentActivity() {
    private lateinit var txcounter: TextView
    private lateinit var txquestion: TextView
    private lateinit var txanswers: TextView
    private lateinit var btrandom: Button
    private lateinit var btreset: Button
    private lateinit var bthide_show: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txcounter = findViewById(R.id.TX_counter)
        txquestion = findViewById(R.id.TX_question)
        txanswers = findViewById(R.id.TX_answers)
        btrandom = findViewById(R.id.BT_random)
        btreset = findViewById(R.id.BT_reset)
        bthide_show = findViewById(R.id.BT_hide_show)

        var hiding: Boolean = true
        var cu_answer: List<String> = emptyList()
        var counter: Int = 0
        //val question: String = "What am I doing?"
        //val answers: List<String> = listOf(
        //    "-Maine", "-New Hampshire", "-Vermont",
        //    "-New York", "-Pennsylvania", "-Ohio"
        //)
        //populate_question(question, txquestion)
        //populate_answers(answers, txanswers)
        val all_questions = read_from_assets(this, "every.txt")
        /*
        Start methods of components
         */
        btrandom.setOnClickListener {
            if (all_questions.isNotEmpty()) {
                extinct_answers(txanswers)
                hiding = true

                val randomIndex = Random.nextInt(all_questions.size)
                val randomItem = all_questions.removeAt(randomIndex)
                cu_answer = randomItem.slice(1..<randomItem.size)
                val question = randomItem[0]
                populate_question(question, txquestion)
                //populate_answers(answers, txanswers)

            } else {
                Toast.makeText(this, "No more questions", Toast.LENGTH_SHORT).show()
            }
        }
        bthide_show.setOnClickListener {
            if (hiding) {
                hiding = false
                populate_answers(cu_answer, txanswers)
            } else {
                hiding = true
                extinct_answers(txanswers)
            }
        }
        /*
        End methods of components
         */
    }
    fun read_from_assets(context: Context, fileName: String): MutableList<List<String>> {
        val all_questions: MutableList<List<String>> = mutableListOf()
        try{
            // File
            val file = context.assets.open(fileName)
            // idk
            val reader = BufferedReader(InputStreamReader(file))
            val lines = reader.readLines()
            lines.forEach{ line ->
                val whole_line: List<String> = line.split("|")
                all_questions.add(whole_line)
            }

        } catch (e: Exception){
            e.printStackTrace()
        }
        return all_questions
    }

    fun populate_question(question: String, txquestion: TextView){
        txquestion.text = question
    }

    fun populate_answers(answers: List<String>, txanswers: TextView){
        var text = ""
        for (answer in answers){
            text += answer + "\n"
        }
        txanswers.text = text
    }

    fun extinct_answers(txanswers: TextView){
        txanswers.text = ""
    }
}