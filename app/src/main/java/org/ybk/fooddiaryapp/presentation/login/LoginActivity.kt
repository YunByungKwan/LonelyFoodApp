package org.ybk.fooddiaryapp.presentation.login

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_act.*
import org.ybk.fooddiaryapp.BuildConfig
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.databinding.LoginActBinding
import org.ybk.fooddiaryapp.presentation.MainActivity
import org.ybk.fooddiaryapp.presentation.diary.DiaryViewModel
import org.ybk.fooddiaryapp.util.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var binding: LoginActBinding
    private lateinit var callbackManager: CallbackManager // Facebook
    private lateinit var backPressCloseHandler: BackPressCloseHandler
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "LoginActivity - onCreate()")
        Log.d("TAG", "LoginViewModel 초기화 후")
        binding = DataBindingUtil.setContentView(this, R.layout.login_act)
        binding.activity = this
        backPressCloseHandler = BackPressCloseHandler(this)
        dialog = Utils.loadingDialog(this)
        checkIfFirstAppStart()
        startMainIfCurrentUserExists()

        // Facebook Login
        callbackManager = CallbackManager.Factory.create()
        facebook_login_btn.setReadPermissions("email")
        facebook_login_btn.registerCallback(callbackManager, facebookCallback)

        Log.d("TAG", "observe 전")
        loginViewModel.signResponse.observe(this, Observer {
            Log.d("TAG", "${it.name}")
            Log.d("TAG", "")
            if(it == Status.SUCCESS) {
                dialog!!.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Timber.e(">>>>>>>>>>>>>>>>>>> 파이어베이스 인증에 실패하였습니다.")
                dialog!!.dismiss()
                showNetworkErrorDialog()
            }
        })
    }

    private fun checkIfFirstAppStart() {
        if(!Utils.isVisited()) {
            Timber.d(">>>>>>>>>>>>>>>>>>> 앱 첫 진입!")
            Utils.saveFirstVisitApp()
        }
    }

    private fun startMainIfCurrentUserExists() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun onClickGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .requestEmail()
            .build()

        val mGoogleSignClient = GoogleSignIn.getClient(this, gso)
        val intent = mGoogleSignClient.signInIntent
        startActivityForResult(intent, Constants.GOOGLE_LOGIN)
    }

    fun onClickFacebookLogin() {
        facebook_login_btn.performClick()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == Constants.GOOGLE_LOGIN) {
                Timber.d(">>>>>>>>>>>>>> Google Login")
                dialog!!.show() // Start loading...
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
                    Timber.d("==> idToken: ${account!!.idToken}")
                    loginViewModel.signInFirebaseWithCredential(credential)
                } catch (e: ApiException) {
                    Timber.e(">>>>>>>>>>>>>> Error Code: ${e.statusCode}")
                    Timber.e(">>>>>>>>>>>>>> Error Msg: ${e.message}")
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
                Timber.d(">>>>>>>>>>>>>> Facebook Login")
                dialog!!.show() // Start loading...
                callbackManager.onActivityResult(requestCode, resultCode, data)
            }
        } else {
            Timber.e(">>>>>>>>>>>>>> Result Fail.")
        }
    }

    private val facebookCallback = object: FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult?) {
            Timber.d(">>>>>>>>>>>>>> facebookCallback > onSuccess()")
            val token = result!!.accessToken.token
            Timber.d(">>>>>>>>>>>>>> Facebook Token: $token")
            val credential = FacebookAuthProvider.getCredential(token)
            loginViewModel.signInFirebaseWithCredential(credential)
        }

        override fun onCancel() {
            Timber.e(">>>>>>>>>>>>>> facebookCallback > onCancel()")
            if(dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        }

        override fun onError(error: FacebookException?) {
            Timber.e(">>>>>>>>>>>>>> facebookCallback > onError()")
            Timber.e(">>>>>>>>>>>>>> Error Message: ${error?.message}")
            if(dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
            }
            showNetworkErrorDialog()
        }
    }

    override fun onBackPressed() {
        backPressCloseHandler.onBackPressed()
    }

    private fun showNetworkErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.login_failed_title))
            .setMessage(getString(R.string.login_failed_msg))
            .setPositiveButton(getString(R.string.close)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}