package dmitrybelykh.test.goodsbrowser.view

interface LoginView {
    fun onSendRequest()
    fun onSuccess()
    fun onError(message: String?)
}