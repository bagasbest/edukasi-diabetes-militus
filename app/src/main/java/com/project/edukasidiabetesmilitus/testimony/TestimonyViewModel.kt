package com.project.edukasidiabetesmilitus.testimony

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class TestimonyViewModel : ViewModel() {

    private val testimonyList = MutableLiveData<ArrayList<TestimonyModel>>()
    private val listData = ArrayList<TestimonyModel>()
    private val TAG = TestimonyViewModel::class.java.simpleName


    fun setListTestimony() {
        listData.clear()

        try {
            FirebaseFirestore.getInstance().collection("testimony")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val model = TestimonyModel()

                        model.title = document.data["title"].toString()
                        model.uid = document.data["uid"].toString()
                        model.titleTemp = document.data["titleTemp"].toString()
                        model.status = document.data["status"].toString()
                        model.name = document.data["name"].toString()
                        model.avatar = document.data["avatar"].toString()
                        model.image = document.data["image"].toString()
                        model.commentCount = document.data["commentCount"].toString()
                        model.description = document.data["description"].toString()

                        listData.add(model)
                    }
                    testimonyList.postValue(listData)
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }


    fun getTestimony() : LiveData<ArrayList<TestimonyModel>> {
        return testimonyList
    }

}