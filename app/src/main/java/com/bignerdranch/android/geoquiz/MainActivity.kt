package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button //자동으로 추가된다.
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity" //최상위 수준 속성 (인스턴스를 생성하지 않고 바로 사용할 수 있음 like static)
//속성값을 계속 보존해야 할 때, 애플리케이션 전체에서 사용하는 상수를 정의할 때 유용하다.

class MainActivity : AppCompatActivity() {
    //AppCompatActivity?
    //Activity 클래스의 서브 클래스이며, 과거 안드로이드 버전과의 호환성을 지원하기 위해 제공된다.

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    //lateinit?
    //코틀린에서 클래스 속성 정의할 때 초기화 하지 않으면 컴파일 에러가 발생.
    //두 속성을 사용하기 전에 책임지고 초기화하겠다는 약속을 한 것.
    //두 속성이 컴파일 시점에서 초기화될 수 없기 때문에(oncreate 이후 setContentView가 되어야
    // 각 객체가 인플레이트되어 참조할 수 있기 때문에)

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideats, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate(Bundle?) called") //첫번째 파라미터에는 출처를, 두 번째에는 메세지 내용을 넣는다.
        setContentView(R.layout.activity_main)

        val provider: ViewModelProvider = ViewModelProvider(this)
        val quizViewModel = provider.get(QuizViewModel::class.java)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View -> //람다식 사용.
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View->
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            currentIndex = ( currentIndex +1 ) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart() called")
    }

    override fun onResume() { //재개하다.
        super.onResume()
        Log.d(TAG,"onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer (userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

    }
}