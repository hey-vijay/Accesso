package vijay.signInFlow.ui.util

import com.google.firebase.auth.FirebaseAuth

public class SessionInformation {
    val TAG = SessionInformation::class.java.name

    companion object {
        fun isLogin() : Boolean {
            val mAuth:FirebaseAuth = FirebaseAuth.getInstance()
            if(mAuth == null || mAuth.currentUser == null) return false
            return true
        }

        fun logOut() {
            FirebaseAuth.getInstance().signOut()
        }

    }
}