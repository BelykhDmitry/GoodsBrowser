package dmitrybelykh.test.goodsbrowser

import android.app.Application
import dmitrybelykh.test.goodsbrowser.presenter.LoginPresenter
import dmitrybelykh.test.goodsbrowser.presenter.LoginPresenterImpl
import dmitrybelykh.test.goodsbrowser.presenter.ProductsPresenter
import dmitrybelykh.test.goodsbrowser.presenter.ProductsPresenterImpl
import dmitrybelykh.test.goodsbrowser.services.RetrofitServices
import dmitrybelykh.test.goodsbrowser.services.RetrofitServicesCreator

class App : Application(), RetrofitServicesCreator {

    private var retrofitServices: RetrofitServices? = null

    val loginPresenter: LoginPresenter = LoginPresenterImpl(this)
    var productsPresenter: ProductsPresenter? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun createRetrofitServices(login: String, password: String): RetrofitServices? {
        retrofitServices = RetrofitServices(this, login, password)
        return retrofitServices
    }

    override fun onTerminate() {
        retrofitServices = null
        loginPresenter.onTerminate()
        productsPresenter?.onTerminate()
        super.onTerminate()
    }

    fun initProductsPresenter(): ProductsPresenter? {
        if (productsPresenter == null)
            productsPresenter = ProductsPresenterImpl(retrofitServices?.skladService!!)
        return productsPresenter
    }
}