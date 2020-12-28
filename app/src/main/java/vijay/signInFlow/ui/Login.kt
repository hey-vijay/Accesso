package vijay.signInFlow.ui

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login.*
import vijay.signInFlow.R
import vijay.signInFlow.ui.util.Const
import vijay.signInFlow.ui.util.ResponseListener
import vijay.signInFlow.ui.util.SmartPreference

class Login : AppCompatActivity(), View.OnClickListener {
    val TAG = Login::class.java.name

    private lateinit var progressLayout: RelativeLayout
    private lateinit var etUserEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btSignIn: Button
    private lateinit var tvSignUp: TextView

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        initView()
        setUpUI()
    }

    private fun initView() {
        progressLayout = findViewById(R.id.progressLayout)
        etUserEmail = findViewById(R.id.etUserEmail)
        etPassword = findViewById(R.id.etPassword)
        btSignIn = findViewById(R.id.btSignIn)
        tvSignUp = findViewById(R.id.tvSignUp)

        //Attaching click listener
        btSignIn.setOnClickListener(this)
        tvSignUp.setOnClickListener(this)
    }


    private fun showProgress(show: Boolean) {
        if (show) {
            progressLayout.visibility = View.VISIBLE
        } else {
            progressLayout.visibility = View.GONE
        }
    }

    private fun setUpUI() {
        val image = this.imgAppImage
        if (image != null)
            Glide.with(applicationContext).load(R.drawable.ic_app_icon).into(image)

        val spannable = SpannableString("Don't have an account? create new")
        spannable.setSpan(UnderlineSpan(), 23, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvSignUp.text = spannable
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
        val email = SmartPreference(applicationContext).getValue(Const.EMAIL, "")
        if(email!!.isNotEmpty()) etUserEmail.setText(email)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            btSignIn.id -> {
                showProgress(true)
                if (verifyCred()) {
                    val email = etUserEmail.text.toString().trim()
                    val password = etPassword.text.toString().trim()
                    loginViewModel.signIn(email, password,
                        object : ResponseListener {
                            override fun onSuccess() {
                                SmartPreference(applicationContext).saveValue(Const.EMAIL, email)
                                gotoHomeScreen()
                                //showProgress(false)
                            }

                            override fun onFailure() {
                                showProgress(false)
                                Toast.makeText(applicationContext, R.string.something_went_wrong, Toast.LENGTH_SHORT)
                                    .show()
                            }

                        })
                } else {
                    showProgress(false)
                }
            }
            tvSignUp.id -> {
                val intent = Intent(this, SignUp::class.java)
                startActivity(intent)
                Toast.makeText(this, "Sign up", Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }

    private fun verifyCred(): Boolean {
        var valid = true
        val email = etUserEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etUserEmail.error = getString(R.string.invalid_mail)
            valid = false
        }

        if (password.length < Const.MIN_PASSWORD_LENGTH) {
            etPassword.error = getString(R.string.error_short_password)
            valid = false
        }
        return valid
    }

    private fun gotoHomeScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}