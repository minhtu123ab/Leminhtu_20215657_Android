package vn.edu.hust.studentman

import android.app.AlertDialog
import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(val students: MutableList<StudentModel>, val fragmentManager: FragmentManager): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
  class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item, parent, false)
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {

    val student = students[position]
    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId


    holder.imageEdit.setOnClickListener {
      val dialog = StudentDialogFragment.newInstance(student.studentName, student.studentId,true,position)
      dialog.show(fragmentManager, "EditStudentDialog")
      Log.d("Edit Button", "Edit Button Clicked $position")
    }

    holder.imageRemove.setOnClickListener {
      AlertDialog.Builder(holder.itemView.context)
        .setMessage("Do you really want to delete this student?")
        .setPositiveButton("Yes") { dialog, id ->
          val removedStudent = students.removeAt(position)
          notifyItemRemoved(position)
          Snackbar.make(holder.itemView, "Student removed", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
              students.add(position, removedStudent)
              notifyItemInserted(position)
            }.show()
        }
        .setNegativeButton("No") { dialog, id ->
          dialog.cancel()
        }
        .create()
        .show()
    }
  }


}