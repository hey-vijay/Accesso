package vijay.signInFlow.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import vijay.signInFlow.ui.util.ResponseListener

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG: String = LoginViewModel::class.java.name
    private var mAuth:FirebaseAuth
    init {
        mAuth = FirebaseAuth.getInstance()
    }

    public fun createAccount(email:String, password:String, listener:ResponseListener) {
        val TAG = LoginViewModel::class.java.name
        mAuth = FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Log.d(TAG, "createAccount: account successfully created")
                    listener.onSuccess()
                } else {
                    listener.onFailure()
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "createAccount : fail $it")
            }

    }

    public fun signIn(email:String, password:String, listener: ResponseListener) {
        mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Log.d(TAG, "signIn: Login Successful")
                    listener.onSuccess()
                } else {
                    Log.e(TAG, "${it.exception}: ");
                    listener.onFailure()
                }
            }
    }

}