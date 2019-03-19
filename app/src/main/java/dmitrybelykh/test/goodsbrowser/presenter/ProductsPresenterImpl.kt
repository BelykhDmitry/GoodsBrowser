package dmitrybelykh.test.goodsbrowser.presenter

import dmitrybelykh.test.goodsbrowser.adapters.ProductsAdapter
import dmitrybelykh.test.goodsbrowser.entity.ProductEntity
import dmitrybelykh.test.goodsbrowser.model.MoySkladProductsModel
import dmitrybelykh.test.goodsbrowser.model.MoySkladProductsModelImpl
import dmitrybelykh.test.goodsbrowser.services.MoySkladService
import dmitrybelykh.test.goodsbrowser.view.ProductsListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class ProductsPresenterImpl(service: MoySkladService) : ProductsPresenter, ProductsAdapter.LoadMoreListener {

    val mySkladModel: MoySkladProductsModel
    val products: MutableList<ProductEntity> = ArrayList<ProductEntity>()
    var size: Int = 0
    private var productsView: ProductsListView? = null
    var disposable: Disposable? = null

    init {
        mySkladModel = MoySkladProductsModelImpl(service)
    }


    override fun handleOnStart(view: ProductsListView) {
        productsView = view
        productsView!!.setProductsList(products)
        if (products.size == 0) {
            loadProducts()
        }
    }

    override fun handleOnStop() {
        productsView = null
    }

    override fun loadMore() {
        loadProducts()
    }

    override fun loadProducts() {
        if (disposable != null && !disposable!!.isDisposed)
            return
        else {
            disposable = mySkladModel.getGoods(size, 20)
                .doOnSuccess({ list -> products.addAll(list) })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter({ _ -> productsView != null })
                .subscribe(
                    { list ->
                        productsView!!.notifyUsersChanged(size, list.size)
                        size = products.size
                    },
                    { error -> productsView!!.onError(error.message) })
        }
    }

    override fun onTerminate() {
        disposable?.dispose()
    }

    override fun getProductAt(position: Int): ProductEntity? {
        return products.get(position)
    }
}