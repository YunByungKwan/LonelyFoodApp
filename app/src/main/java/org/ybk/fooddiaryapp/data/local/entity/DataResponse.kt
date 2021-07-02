package org.ybk.fooddiaryapp.data.local.entity

class DataResponse<L, R>(private val local: L?, private val remote: R?) {

    fun getLocalResponse(): L? = local

    fun getRemoteResponse(): R? = remote
}