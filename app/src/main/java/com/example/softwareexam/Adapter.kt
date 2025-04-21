package com.example.softwareexam

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Adapter(private var taskList : ArrayList<Task>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val task = itemView.findViewById<EditText>(R.id.viewtask)
        val desc = itemView.findViewById<EditText>(R.id.viewdesc)
        val date = itemView.findViewById<EditText>(R.id.viewdate)
        val status = itemView.findViewById<EditText>(R.id.viewstatus)
        val edit = itemView.findViewById<Button>(R.id.edit)
        val delete = itemView.findViewById<Button>(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewnote,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tasks = taskList[position]

        holder.task.setText(tasks.task)
        holder.desc.setText(tasks.description)
        holder.date.setText(tasks.duedate)
        holder.status.setText(tasks.state)

        holder.edit.setOnClickListener {

            val db = Firebase.firestore
            val updates = hashMapOf<String, Any>(
                "task" to holder.task.text.toString(),
                "description" to holder.desc.text.toString(),
                "duedate" to holder.date.text.toString(),
                "state" to holder.status.text.toString()
            )

            db.collection("TASK")
                .document(tasks.docId)
                .update(updates)
                .addOnCompleteListener {
                    Log.d(TAG, "DocumentSnapshot successfully updated!")
                }
                .addOnFailureListener {
                    Log.d(TAG, "DocumentSnapshot updation failed!")
                }

        }

        holder.delete.setOnClickListener {

            val db = Firebase.firestore
            db.collection("TASK")
                .document(tasks.docId)
                .delete()
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

        }


    }
}