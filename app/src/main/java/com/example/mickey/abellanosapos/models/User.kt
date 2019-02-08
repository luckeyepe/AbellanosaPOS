package com.example.mickey.abellanosapos.models

import android.os.Parcel
import android.os.Parcelable

class User :Parcelable {
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var user_email: String?=null
    var user_dispalyName: String?=null

    constructor(parcel: Parcel) : this() {
        user_email = parcel.readString()
        user_dispalyName = parcel.readString()

    }

    constructor()

    constructor(user_email: String?, user_dispalyName: String?) {
        this.user_email = user_email
        this.user_dispalyName = user_dispalyName
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}