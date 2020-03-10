package com.caiosilva.myapplication.helper

import android.util.Base64

class Base64converter {

    fun code(email: String): String {
        return Base64.encodeToString(email.toByteArray(), Base64.DEFAULT).replace(("\\n|\\r"), "")
    }

    fun decode(codedEmail: String) {
    }
}