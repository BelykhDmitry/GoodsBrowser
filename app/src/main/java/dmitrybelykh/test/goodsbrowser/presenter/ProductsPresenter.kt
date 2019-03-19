package dmitrybelykh.test.goodsbrowser.presenter

import dmitrybelykh.test.goodsbrowser.entity.ProductEntity
import dmitrybelykh.test.goodsbrowser.view.ProductsListView

interface ProductsPresenter {
    fun handleOnStart(view: ProductsListView)
    fun handleOnStop()
    fun loadProducts()
    fun onTerminate()
    fun getProductAt(position: Int): ProductEntity?
}