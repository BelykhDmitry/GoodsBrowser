package dmitrybelykh.test.goodsbrowser.model

import dmitrybelykh.test.goodsbrowser.entity.ProductEntity
import io.reactivex.Single

interface MoySkladProductsModel {
    fun getGoods(offset: Int, limit: Int): Single<List<ProductEntity>>
}