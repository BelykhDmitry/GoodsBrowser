package dmitrybelykh.test.goodsbrowser.presenter

import dmitrybelykh.test.goodsbrowser.model.MoySkladProductsModel
import dmitrybelykh.test.goodsbrowser.model.MoySkladProductsModelImpl
import dmitrybelykh.test.goodsbrowser.services.RetrofitServicesCreator
import dmitrybelykh.test.goodsbrowser.services.UriHeaders
import dmitrybelykh.test.goodsbrowser.view.LoginView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginPresenterImpl(var retrofitServicesCreator: RetrofitServicesCreator?) : LoginPresenter {

    var view: LoginView? = null
    var model: MoySkladProductsModel? = null
    var disposable: Disposable? = null

    override fun handleOnStart(view: LoginView) {
        this.view = view
    }

    override fun handleOnStop() {
        this.view = null
    }

    override fun onLoginClicked(login: String, pass: String) {
        val innerLogin = login
        val innerPassw = pass
        view?.onSendRequest()
        val service = retrofitServicesCreator?.createRetrofitServices(login, pass)?.skladService
        if (service != null) {
            model = MoySkladProductsModelImpl(service)
            disposable = model?.getGoods(0, 1)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.filter({ _ -> view != null })
                ?.subscribe({ _ ->
                    run {
                        UriHeaders.setAuthToken(innerLogin, innerPassw)
                        view!!.onSuccess()
                    }
                },
                    { error -> view!!.onError(error.message) })
        } else {
            view?.onError("Cannot create connection")
        }
    }

    override fun onTerminate() {
        disposable?.dispose()
        retrofitServicesCreator = null
    }
}