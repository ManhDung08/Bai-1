package com.example.dsngin

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var numberText: EditText
    lateinit var evenNumberCheck: RadioButton
    lateinit var squareNumberCheck: RadioButton
    lateinit var primeNumberCheck: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberText = findViewById(R.id.editText)
        evenNumberCheck = findViewById(R.id.evenNumber_btn)
        squareNumberCheck = findViewById(R.id.squareNumber_btn)
        primeNumberCheck = findViewById(R.id.primeNumber_btn)

        findViewById<Button>(R.id.show_btn).setOnClickListener{
            val input = numberText.text.toString()
            if (input.isEmpty()) {
                numberText.error = "Vui lòng nhập số tự nhiên vào chỗ trống"
            }else{
                val number = input.toIntOrNull()
                if (number == null) {
                    numberText.error = "Dữ liệu nhập vào không hợp lệ"
                }
                else if (number < 0) {
                    numberText.error = "Số cần nhập phải là số tự nhiên"
                }
                else {
                    var even: String = ""
                    var square: String = ""
                    var prime: String = ""
                    val inputNumber = input.toInt()

                    if (evenNumberCheck.isChecked) {
                        even = "Các số chẵn từ 0 đến $inputNumber là: "
                        for (i in 0..inputNumber step 2) {
                            even += "$i "
                        }
                    }

                    // Xử lý số chính phương
                    if (squareNumberCheck.isChecked) {
                        square = "Các số chính phương từ 0 đến $inputNumber là: "
                        for (i in 0..inputNumber) {
                            if (isSquareNumber(i)) {
                                square += "$i "
                            }
                        }
                    }

                    // Xử lý số nguyên tố
                    if (primeNumberCheck.isChecked) {
                        prime = "Các số nguyên tố từ 0 đến $inputNumber là: "
                        for (i in 2..inputNumber) {
                            if (isPrimeNumber(i)) {
                                prime += "$i "
                            }
                        }
                    }
                    val listNumber = listOf(
                        ListData(even, square, prime)
                    )

                    val numberAdapter = ListAdapter(this, listNumber)
                    val listView = findViewById<ListView>(R.id.listView)
                    listView.adapter = numberAdapter
                }
            }

        }

    }

    // Hàm kiểm tra số chính phương
    fun isSquareNumber(num: Int): Boolean {
        val sqrt = Math.sqrt(num.toDouble())
        return sqrt == sqrt.toInt().toDouble()
    }

    // Hàm kiểm tra số nguyên tố
    fun isPrimeNumber(num: Int): Boolean {
        if (num < 2) return false
        for (i in 2..Math.sqrt(num.toDouble()).toInt()) {
            if (num % i == 0) return false
        }
        return true
    }

    class ListAdapter(private val context: Context, private val listNumber: List<ListData>): BaseAdapter(){
        override fun getCount(): Int {
            return listNumber.size
        }

        override fun getItem(position: Int): Any {
            return listNumber[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val inflater = LayoutInflater.from(context)
            val view = convertView ?: inflater.inflate(R.layout.list_item, parent, false)

            val evenNumberString = view.findViewById<TextView>(R.id.evenNumberView)
            val squareNumberString = view.findViewById<TextView>(R.id.squareNumberView)
            val primeNumberString = view.findViewById<TextView>(R.id.primeNumberView)

            val item = listNumber[position]

            evenNumberString.text = item.evenNumberString
            squareNumberString.text = item.squareNumberString
            primeNumberString.text = item.primeNumberString

            return view
        }

    }
}