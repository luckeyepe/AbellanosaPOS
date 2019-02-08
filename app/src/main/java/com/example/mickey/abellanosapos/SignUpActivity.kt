package com.example.mickey.abellanosapos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        title = "Sign Up"

        button_signupActivityCreateUser.setOnClickListener {
            if (textInputEditText_signupActivityEmail.text.toString().trim().isEmpty()
                && textInputEditText_signupActivityDisplayName.text.toString().trim().isEmpty()
                && textInputEditText_signupActivityPassword.text.toString().trim().isEmpty()
                && textInputEditText_signupActivityConfirmPassword.text.toString().trim().isEmpty()){

                Snackbar.make(it, "Fill in All the Fields", 1000).show()
                return@setOnClickListener

            }else{
                val email = textInputEditText_signupActivityEmail.text.toString().trim()
                val displayName = textInputEditText_signupActivityDisplayName.text.toString().trim()
                val password = textInputEditText_signupActivityPassword.text.toString().trim()
                val confirmPassword = textInputEditText_signupActivityConfirmPassword.text.toString().trim()

                if (!isValidEmail(email)){
                    Snackbar.make(it, "Enter Valid Email", 1000).show()
                    return@setOnClickListener
                }

                if (!isValidPassword(password, confirmPassword,it)){
                    Snackbar.make(it, "Passwords must match", 1000).show()
                    return@setOnClickListener
                }

                createUser(email, displayName, password, it)
            }
        }
    }

    private fun createUser(email: String, displayName: String, password: String, view: View) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                task: Task<AuthResult> ->
                if (task.isSuccessful){

                    var currentUser = FirebaseAuth.getInstance().currentUser!!

                    val updatedProfile = UserProfileChangeRequest
                        .Builder()
                        .setDisplayName(displayName)
                        .build()

                    currentUser.updateProfile(updatedProfile)

                    val user = com.example.mickey.abellanosapos.models.User(email, displayName)
                    FirebaseFirestore
                        .getInstance()
                        .collection("User")
                        .document(task.result!!.user.uid)
                        .set(user).addOnCompleteListener {
                            task: Task<Void> ->
                            if (task.isSuccessful){
                                Log.d("Sing Up", "The user ${currentUser.displayName} has been created")
                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                                finish()
                            }else{
                                Snackbar.make(view, "Password must be at least 8 characters long", 1000).show()
                                Log.e("Sign Up", task.exception.toString())
                            }
                        }
                }else{
                    Snackbar.make(view, "User already exists", 1000).show()
                    Log.e("Sign Up", task.exception.toString())
                    return@addOnCompleteListener
                }
            }

    }

    private fun isValidPassword(password: String, confirmPassword: String, view: View): Boolean {
        if(isStringContainNumber(password, view)){
            if(isStringContainUpperCase(password, view)){
                if(isStringContainLowerCase(password, view)){
                    if(isStringContainSpecialCharacter(password, view)){
                        if(isOver8Characters(password, view)){
                            if (password == confirmPassword) {
                                return true
                            }
                        }
                    }
                }
            }
        }
        return false
    }

    private fun isOver8Characters(password: String, view: View): Boolean{
        if(password.length >= 8){
            return true
        }

        Snackbar.make(view, "Password must be at least 8 characters long", 1000).show()
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
