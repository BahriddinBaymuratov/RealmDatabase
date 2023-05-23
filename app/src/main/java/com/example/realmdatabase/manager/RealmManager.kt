package com.example.realmdatabase.manager

import com.example.realmdatabase.model.Note
import io.realm.Realm
import io.realm.RealmResults

class RealmManager {
    companion object {
        private var realmManager: RealmManager? = null
        private lateinit var realm: Realm
        val instance: RealmManager?
            get() {
                if (realmManager == null) {
                    realmManager = RealmManager()
                }
                return realmManager
            }
    }

    init {
        realm = Realm.getDefaultInstance()
    }

    fun saveNote(note: Note) {
        realm.executeTransaction { // new way
            it.insert(note)
        }
//        realm.beginTransaction() // old way
//        realm.copyToRealm(note)
//        realm.commitTransaction()
    }

    fun getNotes(): List<Note> {
        val list = mutableListOf<Note>()
        val notes: RealmResults<Note> = realm.where(Note::class.java).findAll()
        for (i in notes) {
            list.add(i)
        }
        return list
    }

    fun deleteNote(id: Long) {
        realm.executeTransaction {
            val rows: RealmResults<Note> =
                it.where(Note::class.java).equalTo("id", id).findAll()
            rows.deleteAllFromRealm()
        }
    }

    fun clearNotes() {
        realm.executeTransaction {// new way
            it.deleteAll()
        }
//        realm.beginTransaction() // old way
//        realm.deleteAll()
//        realm.commitTransaction()

    }

}