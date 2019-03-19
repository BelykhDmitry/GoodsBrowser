package dmitrybelykh.test.goodsbrowser.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import dmitrybelykh.test.goodsbrowser.App
import dmitrybelykh.test.goodsbrowser.R
import dmitrybelykh.test.goodsbrowser.presenter.LoginPresenter

class LoginActivity : AppCompatActivity(), LoginView {
    //val login: String = "admin@lunna24"
    //val pass: String = "123123"

    var login: TextInputEditText? = null
    var password: TextInputEditText? = null
    var button: AppCompatButton? = null

    var presenter: LoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        button = findViewById(R.id.button_login)
        login = findViewById(R.id.login_edit_text)
        password = findViewById(R.id.password_edit_text)
        if (application is App) {
            presenter = (application as App).loginPresenter
        }
        addTextEditListeners()
    }

    override fun onStart() {
        super.onStart()
        presenter?.handleOnStart(this)
    }

    override fun onStop() {
        presenter?.handleOnStop()
        presenter = null
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        button?.setOnClickListener { _ ->
            presenter?.onLoginClicked(login?.text.toString(), password?.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSuccess() {
        Toast.makeText(this, "Succes!", Toast.LENGTH_SHORT).show()
        val intent: Intent = Intent(this, ProductsActivity::class.java)
        startActivity(intent)
    }

    override fun onError(message: String?) {
        setEnabled(true)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSendRequest() {
        setEnabled(false)
    }

    fun addTextEditListeners() {
        login?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setEnabled(s?.length!! > 0 && password?.text?.length!! > 0)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        password?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setEnabled(s?.length!! > 0 && login?.text?.length!! > 0)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    fun setEnabled(enabled: Boolean) {
        button?.isEnabled = enabled
    }
}