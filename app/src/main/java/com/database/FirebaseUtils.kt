package com.database

import com.admin.model.Teacher
import com.database.room.entity.Category
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.model.ApplicationUser
import com.user.main.ui.home.ConstantCollection

fun createCollection(collectionName:String):CollectionReference{
    val database = Firebase.firestore
    return database.collection((collectionName))
}
fun addUserToFirebase(user:ApplicationUser , onSuccessListener: OnSuccessListener<Void>,
                      onFailureListener: OnFailureListener){
    //val db = Firebase.firestore
    val userCollection = createCollection(ApplicationUser.COLLECTION_NAME)
    val userDoc =  userCollection.document(user.id!!)
    userDoc.set(user).
    addOnSuccessListener (onSuccessListener)
        .addOnFailureListener(onFailureListener)
}
// check user in firebase
fun getUser(uid:String,onSuccessListener: OnSuccessListener<DocumentSnapshot>,
            onFailureListener: OnFailureListener){
    val collectionREF = createCollection(ApplicationUser.COLLECTION_NAME)
    collectionREF.document(uid).get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)
}
//.. add categories
fun addCategories(
    category: Teacher,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
){
    val collection = createCollection(Category.CONSTANTS_CATEGORY)
    val doc = collection.document()
    category.id = doc.id
    doc.set(category).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)
}
fun getCollection(collectionName:String):CollectionReference{
    val db = Firebase.firestore
    // val collectionRef = db.collection(ApplicationUser.COLLECTION_NAME)
    return db.collection(collectionName)
}

fun addUserToFireStore(user:ApplicationUser  , onSuccessListener: OnSuccessListener<Void>, onFailureListener: OnFailureListener){
    //val db = Firebase.firestore
    val userCollection = getCollection(ApplicationUser.COLLECTION_NAME)
    val userDoc =  userCollection.document(user.id!!)
    userDoc.set(user).
    addOnSuccessListener (onSuccessListener)
        .addOnFailureListener(onFailureListener)
}
fun addFavorite(
    room: Teacher,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val collection = getCollection(ConstantCollection.COLLECTION_LOVE)
    val doc = collection.document()
    room.id = doc.id
    doc.set(room).addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getFavoriteREF(
    onSuccessListener: OnSuccessListener<QuerySnapshot>,
    onFailureListener: OnFailureListener
) {
    val collection = getCollection(ConstantCollection.COLLECTION_LOVE)
    collection.get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)

}
fun addProduct(
    room: Teacher,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val collection = getCollection(ConstantCollection.COLLECTION_PRODUCT_FIRESTORE)
    val doc = collection.document()
    room.id = doc.id
    doc.set(room).addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getProduct(
    onSuccessListener: OnSuccessListener<QuerySnapshot>,
    onFailureListener: OnFailureListener
) {
    val collection = getCollection(ConstantCollection.COLLECTION_PRODUCT_FIRESTORE)
    collection.get().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)

}