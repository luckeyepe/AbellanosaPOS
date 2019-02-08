package com.example.mickey.abellanosapos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        title = "Log In"

        if (FirebaseAuth.getInstance().currentUser != null){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        button_loginActivityLogin.setOnClickListener {
            if (textInputEditText_loginActivityEmail.text.toString().trim().isEmpty() &&
                textInputEditText_loginActivityPassword.text.toString().trim().isEmpty()){

                Snackbar.make(it, "Fill in all fields", 1000).show()
                return@setOnClickListener
            }else{
                val email = textInputEditText_loginActivityEmail.text.toString().trim()
                val password = textInputEditText_loginActivityPassword.text.toString().trim()

                if (!isValidEmail(email)){
                    Snackbar.make(it, "Enter Valid Email", 1000).show()
                    return@setOnClickListener
                }

                if (!isValidPassword(password, it)){
                    return@setOnClickListener
                }

                logInUser(email, password, it)
            }
        }

        button_loginActivitySignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun logInUser(email: String, password: String, view: View) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                task: Task<AuthResult> ->
                if (task.isSuccessful){
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }else{
                    Snackbar.make(view, "User does not exist", 1000).show()
                    Log.e("Log In Error",task.exception.toString())
                    return@addOnCompleteListener
                }
            }
    }

    private fun isValidPassword(password: String, view:View): Boolean {
        if(isStringContainNumber(password, view)){
            if(isStringContainUpperCase(password, view)){
                if(isStringContainLowerCase(password, view)){
                    if(isStringContainSpecialCharacter(password, view)){
                        if(password.length >=8){
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    private fun isValidEmail(email: String): Boolean {
        return email!!.contains("@") && email.contains(".com")
    }

    private fun isStringContainSpecialCharacter(string: String, view: View): Boolean {
        for(c in string.toCharArray()){
            if (!c.isLetterOrDigit())
                return true
        }

        Snackbar.make(view, "Password must contain at least one (1) special character", 1000).show()

        return false
    }

    private fun isStringContainLowerCase(string: String, view: View): Boolean {
        for(c in string.toCharArray()){
            if (c.isLowerCase())
                return true
        }

        Snackbar.make(view, "Password must contain at least one (1) special character", 1000).show()

        return false
    }

    private fun isStringContainUpperCase(string: String, view: View): Boolean {
        for(c in string.toCharArray()){
            if (c.isUpperCase())
                return true
        }
        Snackbar.make(view, "Password must contain at least one (1) upper case character", 1000).show()

        return false
    }

    private fun isStringContainNumber(string: String, view: View): Boolean {

        for(c in string.toCharArray()){
            if (c.isDigit())
                return true
        }

        Snackbar.make(view, "Password must contain at least one (1) digit", 1000).show()

        return false
    }
}
