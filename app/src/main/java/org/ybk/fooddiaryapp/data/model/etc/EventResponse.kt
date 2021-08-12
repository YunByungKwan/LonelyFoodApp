package org.ybk.fooddiaryapp.data.model.etc

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

sealed class EventResponse {
    data class Changed(val snapshot: DataSnapshot): ValueEventResponse()
    data class Cancelled(val error: DatabaseError): ValueEventResponse()
}