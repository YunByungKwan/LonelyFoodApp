package org.ybk.fooddiaryapp.data

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import org.w3c.dom.Document
import org.ybk.fooddiaryapp.data.model.etc.*
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun DatabaseReference.valueEvent(): ValueEventResponse = suspendCoroutine { continuation ->
//    val valueEventListener = object: ValueEventListener {
//        override fun onCancelled(error: DatabaseError) {
//            Log.d("TAG", "DatabaseReference.valueEvent() - onCancelled")
//            continuation.resume(EventResponse.Cancelled(error))
//        }
//
//        override fun onDataChange(snapshot: DataSnapshot) {
//            Log.d("TAG", "DatabaseReference.valueEvent() - onDataChange")
//            continuation.resume(EventResponse.Changed(snapshot))
//        }
//    }
//    addValueEventListener(valueEventListener) // Subscribe to the event
}

suspend fun Query.execute(): DatabaseResponse = suspendCoroutine { continuation ->
//    val valueEventListener = object: ValueEventListener {
//        override fun onDataChange(snapshot: DataSnapshot) {
//            Log.d("TAG2", "Query.execute() - onDataChange")
//            continuation.resume(DatabaseResponse.Changed(snapshot))
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//            Log.d("TAG2", "Query.execute() - onCancelled")
//            continuation.resume(DatabaseResponse.Cancelled(error))
//        }
//    }
//    addValueEventListener(valueEventListener) // Subscribe to the event
}

suspend fun Task<DocumentSnapshot>.executeDocument(): DocumentResponse = suspendCoroutine { continuation ->
    Log.d("TAG2", "Task<DocumentSnapshot>.executeDocument()")
    val onSuccessListener = OnSuccessListener<DocumentSnapshot> {

    }
    val onFailureListener = OnFailureListener {

    }
    addOnSuccessListener(onSuccessListener)
    addOnFailureListener(onFailureListener)
}

suspend fun Task<QuerySnapshot>.executeQuery(): QueryResponse = suspendCoroutine { continuation ->
    Log.d("TAG", "Task<QuerySnapshot>.executeQuery()")
    val onCompleteListener = OnCompleteListener<QuerySnapshot> { snapshot ->
        continuation.resume(QueryResponse.Success(snapshot))
    }
    val onFailureListener = OnFailureListener { e ->
        continuation.resume(QueryResponse.Failure(e))
    }
    addOnCompleteListener(onCompleteListener)
    addOnFailureListener(onFailureListener)
}

suspend fun Task<Void>.execute(): TaskResponse = suspendCoroutine { continuation ->
    Log.d("TAG", "Task<Void>.execute()")
    val onCompleteListener = OnCompleteListener<Void> { task ->
        continuation.resume(TaskResponse.Complete(task))
    }
    val onFailureListener = OnFailureListener { e ->
        continuation.resume(TaskResponse.Failure(e))
    }
    addOnCompleteListener(onCompleteListener)
    addOnFailureListener(onFailureListener)
}

suspend fun UploadTask.execute(): UploadResponse = suspendCoroutine { continuation ->
    val onFailureListener = OnFailureListener { e ->
        continuation.resume(UploadResponse.Failure(e))
    }
    val uriOnCompleteListener = OnCompleteListener<Uri> { uri ->
        val downloadUri = uri.result.toString()
        continuation.resume(UploadResponse.Complete(downloadUri))
    }
    val onCompleteListener = OnCompleteListener<UploadTask.TaskSnapshot> { task ->
        if(task.isSuccessful) {
            val urlTask = task.result.storage.downloadUrl
            urlTask.addOnCompleteListener(uriOnCompleteListener)
            urlTask.addOnFailureListener(onFailureListener)
        } else {
            continuation.resume(UploadResponse.Failure(Exception()))
        }
    }
    addOnCompleteListener(onCompleteListener)
    addOnFailureListener(onFailureListener)
}