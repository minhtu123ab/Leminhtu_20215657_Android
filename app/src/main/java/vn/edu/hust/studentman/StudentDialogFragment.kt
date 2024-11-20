package vn.edu.hust.studentman

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.DialogFragment

class StudentDialogFragment : DialogFragment() {

    //use interface to pass data from DialogFragment to Activity
    interface DialogListener {
        fun onDialogPositiveClick(userName: String, userId: String,clickEdit: Boolean, position: Int)
    }

    private lateinit var listener: DialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() + " must implement DialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //get data from arguments ( a Bundle contains data passed to DialogFragment
        // through newInstance() method)
        val studentName = arguments?.getString("studentName") ?: ""
        val studentId = arguments?.getString("studentId") ?: ""
        var isEditClicked = arguments?.getBoolean("clickEdit") ?: false
        var position: Int = arguments?.getInt("position") ?: -1


        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_dialog, null)
            val itemView = inflater.inflate(R.layout.layout_student_item, null)

            val userNameEditText: EditText = view.findViewById(R.id.editName)
            val userIdEditText: EditText = view.findViewById(R.id.editId)
            val editIcon: ImageView = itemView.findViewById(R.id.image_edit)
            //help user edit data
            userNameEditText.setText(studentName)
            userIdEditText.setText(studentId)
            editIcon.setOnClickListener(View.OnClickListener(){
                fun onClick(v : View){
                    isEditClicked = true
                }

            })

            builder.setView(view)
                .setPositiveButton("OK") { dialog, id ->
                    val userName = userNameEditText.text.toString()
                    val userId = userIdEditText.text.toString()
                    if (isEditClicked == true) {
                        listener.onDialogPositiveClick(userName, userId,true,position)
                    }
                    else listener.onDialogPositiveClick(userName, userId,false,position)

                }
                .setNegativeButton("Cancel") { dialog, id ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }



    companion object {
        fun newInstance(studentName: String, studentId: String, isEditClicked: Boolean, position: Int): StudentDialogFragment {
            val fragment = StudentDialogFragment()
            val args = Bundle()
            args.putString("studentName", studentName)
            args.putString("studentId", studentId)
            args.putBoolean("clickEdit",isEditClicked)
            args.putInt("position",position)
            fragment.arguments = args
            return fragment
        }
    }

}