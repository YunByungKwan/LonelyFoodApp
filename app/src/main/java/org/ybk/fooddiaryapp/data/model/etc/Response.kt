package org.ybk.fooddiaryapp.data.model.etc

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask

/**
 * A response state for firebase events.
 */
sealed class Response<T> {
    data class Complete<T>(val data: T): Response<T>()
    data class Error<T>(val error: Exception): Response<T>()

    companion object {
        fun<T> complete(data: T): Response<T> = Complete(data)
        fun<T> error(e: Exception): Response<T> = Error(e)
    }
}

/**
 * A response state for ChildEvent listener.
 */
sealed class ChildEventResponse {
    data class Added(val snapshot: DataSnapshot, val previousChildName: String?): ChildEventResponse()
    data class Changed(val snapshot: DataSnapshot, val previousChildName: String?): ChildEventResponse()
    data class Moved(val snapshot: DataSnapshot, val previousChildName: String?): ChildEventResponse()

    data class Removed(val snapshot: DataSnapshot): ChildEventResponse()
    data class Cancelled(val error: DatabaseError): ChildEventResponse()
}

/**
 * A response state for ValueEvent listener.
 */
sealed class ValueEventResponse {
    data class Changed(val snapshot: DataSnapshot): ValueEventResponse()
    data class Cancelled(val error: DatabaseError): ValueEventResponse()
}

sealed class DatabaseResponse {
    data class Changed(val snapshot: DataSnapshot) : DatabaseResponse()
    data class Cancelled(val error: DatabaseError): DatabaseResponse()
}

sealed class DocumentResponse {
    data class Success(val snapshot: DocumentSnapshot): DocumentResponse()
    data class Failure(val e: Exception): DocumentResponse()
}

sealed class QueryResponse {
    data class Success(val snapshot: Task<QuerySnapshot>): QueryResponse()
    data class Failure(val e: Exception): QueryResponse()
}

sealed class TaskResponse {
    data class Complete(val task: Task<Void>): TaskResponse()
    data class Failure(val e: Exception): TaskResponse()
}

sealed class UploadResponse {
    data class Complete(val uri: String): UploadResponse()
    data class Failure(val e: Exception): UploadResponse()
}