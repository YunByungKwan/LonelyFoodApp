package org.ybk.fooddiaryapp.data.repository.diary.datasourceimpl

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import io.reactivex.Observable
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.execute
import org.ybk.fooddiaryapp.data.executeQuery
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.diary.FoodImage
import org.ybk.fooddiaryapp.data.model.etc.*
import org.ybk.fooddiaryapp.data.repository.diary.datasource.DiaryRemoteDataSource
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.Utils
import javax.inject.Inject

class DiaryRemoteDataSourceImpl @Inject constructor(
    private val mDatabase: FirebaseDatabase,
    private val mStorage: FirebaseStorage,
    private val mFirestore: FirebaseFirestore
): DiaryRemoteDataSource {
    /***/
    override suspend fun addDiaryToFirestoreDB(diary: Diary): TaskResponse {
        Log.d("TAG2", "DiaryRemoteDataSourceImpl - addDiaryToFirestoreDB")
        val hashMap = Utils.convertObjectToHashMap(diary)
        val documentReference = mFirestore.collection(Constants.ROOT)
            .document(diary.email)
            .collection(Constants.DIARY)
            .document(diary.registerTime)
        val response = documentReference.set(hashMap).execute()

        return when(response) {
            is TaskResponse.Complete -> {
                TaskResponse.Complete(response.task)
            }
            is TaskResponse.Failure -> {
                TaskResponse.Failure(response.e)
            }
        }
    }

    override suspend fun getDiaryListFromFirestoreDB(email: String): QueryResponse {
        val querySnapshot = Firebase.firestore
            .collection(Constants.ROOT)
            .document(email)
            .collection(Constants.DIARY)
            .get()

        val response = querySnapshot.executeQuery()

        return when(response) {
            is QueryResponse.Success -> {
                QueryResponse.Success(response.snapshot)
            }
            is QueryResponse.Failure -> {
                QueryResponse.Failure(response.e)
            }
        }
    }

    override suspend fun getOpenDiaryListFromFirestoreDB(open: String): QueryResponse {
        Log.d("TAG2", "getOpenDiaryListFromFirestoreDB")

        val querySnapshot = Firebase.firestore
            .collection(Constants.RECOMMENDATION_LIST)
            .get()
            .addOnSuccessListener { result ->
                val res = result.documents
                res.forEach {
                    val document = it.toObject(Diary::class.java)
                    Log.d("TAG2", "contents:${document?.contents}")
                }
            }
        val response = querySnapshot.executeQuery()
        return when(response) {
            is QueryResponse.Success -> {
                QueryResponse.Success(response.snapshot)
            }
            is QueryResponse.Failure -> {
                QueryResponse.Failure(response.e)
            }
        }
    }

    override suspend fun addDiaryToRecommendList(diary: Diary): TaskResponse {
        Log.d("TAG2", "DiaryRemoteDataSourceImpl - addDiaryToRecommendList")
        val hashMap = Utils.convertObjectToHashMap(diary)
        val documentReference = mFirestore.collection(Constants.RECOMMENDATION_LIST)
            .document()
        val response = documentReference.set(hashMap).execute()

        return when(response) {
            is TaskResponse.Complete -> {
                TaskResponse.Complete(response.task)
            }
            is TaskResponse.Failure -> {
                TaskResponse.Failure(response.e)
            }
        }
    }

    override suspend fun updateDiaryToOpenOrHideInFirestoreDB(diary: Diary): TaskResponse {
        Log.d("TAG2", "DiaryRemoteDataSourceImpl - updateDiaryToOpenInFirestoreDB")
        val map = mutableMapOf<String, Any>()
        map["open"] = if(diary.open == "Y") "N" else "Y"

        val task = mFirestore.collection(Constants.ROOT)
            .document(diary.email)
            .collection(Constants.DIARY)
            .document(diary.registerTime)
            .update(map)

        val response = task.execute()
        return when(response) {
            is TaskResponse.Complete -> {
                if(map["open"] == "N") {
                    deleteDiaryFromRecommendList(diary)
                }
                TaskResponse.Complete(response.task)
            }
            is TaskResponse.Failure -> {
                TaskResponse.Failure(response.e)
            }
        }
    }

    // TaskResponse
    override suspend fun deleteDiaryFromRecommendList(diary: Diary) {
        Log.d("TAG2", "DiaryRemoteDataSourceImpl - deleteDiaryFromRecommendList")
        Log.d("TAG2", "검색할 이메일, 시간: ${diary.email}, ${diary.registerTime}")
        val task = mFirestore.collection(Constants.RECOMMENDATION_LIST)
            .whereEqualTo("email", diary.email)
            .whereEqualTo("registerTime", diary.registerTime)
            .get()
            .addOnSuccessListener { result ->
                val res = result.documents
                res.forEach {
                    mFirestore.collection(Constants.RECOMMENDATION_LIST)
                        .document(it.id).delete()
                }
            }
    }

    override suspend fun deleteDiaryFromFirestoreDB(diary: Diary): QueryResponse {
        Log.d("TAG2", "DiaryRemoteDataSourceImpl - deleteDiaryFromFirestoreDB")

        val task = mFirestore.collection(Constants.ROOT)
            .document(diary.email)
            .collection(Constants.DIARY)
            .whereEqualTo("email", diary.email)
            .whereEqualTo("registerTime", diary.registerTime)
            .get()

        val response = task.executeQuery()
        return when(response) {
            is QueryResponse.Success -> {
                val documents = response.snapshot.result.documents
                documents.forEach { document ->
                    Log.d("TAG2", "contents: ${document["contents"]}")
                    Log.d("TAG2", "name: ${document["name"]}")
                    Log.d("TAG2", "registerTime: ${document["registerTime"]}")
                    mFirestore
                        .collection(Constants.ROOT)
                        .document(diary.email)
                        .collection(Constants.DIARY)
                        .document(diary.registerTime)
                        .delete()
                }
                QueryResponse.Success(response.snapshot)
            }
            is QueryResponse.Failure -> {
                QueryResponse.Failure(response.e)
            }
        }
    }
    /***/

    override suspend fun uploadFoodImage(foodImage: FoodImage): UploadResponse {
        Log.d("TAG", "remoteDataSource - uploadFoodImage")
        val imageName = Utils.getImageFileName(foodImage.email, foodImage.registerTime)
        val storageRef = mStorage.reference.child(Constants.IMAGE_FOLDER).child(imageName)
        val uploadTask = storageRef.putFile(Uri.parse(foodImage.localPath))
        val response = uploadTask.execute()
        Log.d("TAG", "abc")
        return when(response) {
            is UploadResponse.Complete -> {
                UploadResponse.Complete(response.uri)
            }
            is UploadResponse.Failure -> {
                UploadResponse.Failure(response.e)
            }
        }
    }

    override suspend fun getDiary(id: String): DatabaseResponse {
        Log.d("TAG", "getDiary")
        val databaseReference = mDatabase.reference.child(Constants.DIARY_LIST).child(id)
        val response = databaseReference.execute()
        return when(response) {
            is DatabaseResponse.Changed -> {
                DatabaseResponse.Changed(response.snapshot)
            }
            is DatabaseResponse.Cancelled -> {
                DatabaseResponse.Cancelled(response.error)
            }
        }
        //return ref
    }

    override fun deleteDiaryDataFromDB(diary: Diary) {
        val key = "${Utils.convertDotToDash(diary.email)}-${diary.registerTime}"
        val ref = mDatabase.getReference("${Constants.DIARY_LIST}/$key")
        ref.removeValue()
    }

    override fun deleteImageFromStorage(fileName: String): Task<Void> {
        val file = mStorage.reference.child("${Constants.IMAGE_FOLDER}/$fileName")
        return file.delete()
    }
}