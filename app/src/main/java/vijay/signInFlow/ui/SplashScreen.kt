package vijay.signInFlow.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import vijay.signInFlow.ui.util.SessionInformation

class SplashScreen : AppCompatActivity() {
    val TAG = SplashScreen::class.java.name

    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val intent:Intent = if(SessionInformation.isLogin()) {
            Log.d(TAG, "onStart: User already logged in")
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, Login::class.java)
        }
        startActivity(intent)
        finish()
    }
}