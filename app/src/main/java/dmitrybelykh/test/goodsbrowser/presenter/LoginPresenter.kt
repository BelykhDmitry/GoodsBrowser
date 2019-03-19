package dmitrybelykh.test.goodsbrowser.presenter

import dmitrybelykh.test.goodsbrowser.view.LoginView

interface LoginPresenter {
    fun handleOnStart(view: LoginView)
    fun handleOnStop()
    fun onLoginClicked(login: String, pass: String)
    fun onTerminate()
}