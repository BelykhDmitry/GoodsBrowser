package dmitrybelykh.test.goodsbrowser.view

import dmitrybelykh.test.goodsbrowser.entity.ProductEntity

interface ProductsListView {
    fun setProductsList(list: List<ProductEntity>)
    fun notifyUsersChanged(position: Int, length: Int)
    fun onError(message: String?)
}