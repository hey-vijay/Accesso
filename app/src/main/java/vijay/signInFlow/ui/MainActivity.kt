package vijay.signInFlow.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import vijay.signInFlow.R
import vijay.signInFlow.ui.util.SessionInformation

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.name

    private lateinit var buyme: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setUpUI()
    }

    private fun init(){
        buyme = findViewById(R.id.btBuy)
        buyme.setOnClickListener {
            var text = ""
            if(SessionInformation.isLogin()){
                text = "Happy shopping"
            } else {
                text = "Please Login before purchasing"
                startActivity(Intent(this, Login::class.java))
                finish()
            }
            Toast.makeText(this, text, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setUpUI(){
        val imageView = imageView
        if(imageView != null)
            Glide.with(applicationContext).load(R.drawable.ic_hood).into(imageView)
    }

    /*
    *   show log out button if user is already logged in
    * */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(SessionInformation.isLogin()) {
            val menuInflater = menuInflater
            menuInflater.inflate(R.menu.menu, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_log_out) {
            Log.d(TAG, "onOptionsItemSelected: Log Out")
            SessionInformation.logOut()
            startActivity(Intent(this, Login::class.java))
            finish()
        }
        return true
    }
}