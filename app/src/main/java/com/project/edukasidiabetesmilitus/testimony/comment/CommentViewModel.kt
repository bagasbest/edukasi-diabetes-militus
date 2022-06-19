package com.project.edukasidiabetesmilitus.testimony.comment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class CommentViewModel : ViewModel() {

    private val commentList = MutableLiveData<ArrayList<CommentModel>>()
    private val listData = ArrayList<CommentModel>()
    private val TAG = CommentViewModel::class.java.simpleName


    fun setListComment(postId: String) {
        listData.clear()

        try {
            FirebaseFirestore.getInstance().collection("testimony")
                .document(postId)
                .collection("comment")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val model = CommentModel()

                        model.comment = document.data["comment"].toString()
                        model.uid = document.data["uid"].toString()
                        model.date = document.data["date"].toString()
                        model.name = document.data["name"].toString()

                        listData.add(model)
                    }
                    commentList.postValue(listData)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }


    fun getComment() : LiveData<ArrayList<CommentModel>> {
        return commentList
    }


}