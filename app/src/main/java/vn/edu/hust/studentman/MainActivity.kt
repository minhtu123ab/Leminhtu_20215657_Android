package vn.edu.hust.studentman

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), StudentDialogFragment.DialogListener {
  lateinit var add: Button
  lateinit var students: MutableList<StudentModel>
  lateinit var studentAdapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    students = mutableListOf(
      StudentModel("Lê Minh Tú", "SV001"),
      StudentModel("Nguyễn Gia Bảo", "SV002"),
      StudentModel("Nguyễn Bình An", "SV003"),
      StudentModel("Nguyễn Việt Hoàng", "SV004"),
      StudentModel("Nguyễn Văn Hồng", "SV005"),
      StudentModel("Nguyễn Mai Hoa", "SV006"),

    )

    add = findViewById(R.id.btn_add_new)
    add.setOnClickListener {
      showAlertDialog()
    }

    studentAdapter = StudentAdapter(students, supportFragmentManager)

    findViewById<RecyclerView>(R.id.recycler_view_students).run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }
  }

  override fun onDialogPositiveClick(userName: String, userId: String, clickEdit : Boolean, position : Int) {
    // Handle the data received from the dialog
    if (clickEdit == false) {
      val newStudent = StudentModel(userName, userId)
      students.add(newStudent)
      studentAdapter.notifyItemInserted(students.size - 1)
    }  else {
        students[position] = StudentModel(userName, userId)
        studentAdapter.notifyItemChanged(position)
    }

  }

  private fun showAlertDialog() {
    val dialog = StudentDialogFragment()
    val fragmentManager: FragmentManager = supportFragmentManager
    dialog.show(fragmentManager, "Enter your information")
  }
}