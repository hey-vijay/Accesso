package vijay.signInFlow.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_sign_up.*
import vijay.signInFlow.R
import vijay.signInFlow.ui.util.Const
import vijay.signInFlow.ui.util.ResponseListener
import vijay.signInFlow.ui.util.SmartPreference

class SignUp : AppCompatActivity() , View.OnClickListener {
    private val TAG = SignUp::class.java.name

    private lateinit var tvUserName: TextInputEditText
    private lateinit var tvUserMail: TextInputEditText
    private lateinit var tvUserPassword: TextInputEditText
    private lateinit var tvLogin: TextView
    private lateinit var tvContinue: TextView
    private lateinit var btSignUp: Button
    private lateinit var progressLayout:RelativeLayout

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        init()
        setUpUI()
    }

    private fun init() {
        tvUserName = findViewById(R.id.etName)
        tvUserMail = findViewById(R.id.etUserName)
        tvUserPassword = findViewById(R.id.etPassword)
        tvLogin = findViewById(R.id.tvLogin)
        tvContinue = findViewById(R.id.tvSkipToHome)
        btSignUp = findViewById(R.id.btSignUp)
        progressLayout = findViewById(R.id.progressLayout)

        //Attach click listeners
        btSignUp.setOnClickListener(this)
        tvLogin.setOnClickListener(this)
        tvContinue.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view!!.id) {
            btSignUp.id -> {
                showProgress(true)
                if(verifyCred()) {
                    createAccount()
                } else {
                    showProgress(false)
                }
            }
            tvLogin.id -> {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
            tvContinue.id -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setUpUI(){
        val imageView = imgAppImage
        if (imageView != null)
            Glide.with(applicationContext).load(R.drawable.ic_app_icon).into(imageView)
    }

    private fun verifyCred() : Boolean {
        var validData = true
        val username = tvUserName.text.toString().trim()
        val userMail = tvUserMail.text.toString().trim()
        val userPassword = tvUserPassword.text.toString().trim()

        if(username.length < Const.MIN_NAME_LENGTH ) {
            tvUserName.error = getString(R.string.error_short_name)
            validData = false
        }

        if(userMail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userMail).matches()) {
            tvUserMail.error = getString(R.string.invalid_mail)
            validData = false
        }

        if(userPassword.length < Const.MIN_PASSWORD_LENGTH) {
            tvUserPassword.error = getString(R.string.error_short_password)
            validData = false
        }

        return validData
    }

    private fun createAccount(){
        val email = tvUserMail.text.toString().trim()
        val password = tvUserPassword.text.toString().trim()
        loginViewModel.createAccount(email, password, object: ResponseListener{
            override fun onSuccess() {
                SmartPreference(applicationContext).saveValue(Const.EMAIL, email)
                Toast.makeText(applicationContext, R.string.account_created, Toast.LENGTH_SHORT)
                    .show()
                gotoHomeScreen()
                //showProgress(false)
            }

            override fun onFailure() {
                showProgress(false)
                Toast.makeText(applicationContext, R.string.something_went_wrong, Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun showProgress(show: Boolean){
        if(show){
            progressLayout.visibility = View.VISIBLE
        }else {
            progressLayout.visibility = View.GONE
        }
    }

    private fun gotoHomeScreen(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}