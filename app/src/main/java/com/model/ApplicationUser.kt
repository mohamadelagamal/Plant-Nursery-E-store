package com.model
// this class play roll between application and firebase
data class ApplicationUser(
    var id :String?=null,
    var userName :String?=null,
    var email : String?=null,
    var imageID : String?=null
){
    companion object{
        const val COLLECTION_NAME ="users"
    }
}