package dmitrybelykh.test.goodsbrowser.model

import dmitrybelykh.test.goodsbrowser.entity.ProductEntity
import dmitrybelykh.test.goodsbrowser.model.MoySkladResponse.Product
import dmitrybelykh.test.goodsbrowser.model.MoySkladResponse.ProductsResponse
import dmitrybelykh.test.goodsbrowser.services.MoySkladService
import io.reactivex.Observable
import io.reactivex.Single

class MoySkladProductsModelImpl(val skladService: MoySkladService) : MoySkladProductsModel {

    override fun getGoods(offset: Int, limit: Int): Single<List<ProductEntity>> {
        return skladService.getGoods(offset, limit)
            .map { t: ProductsResponse -> t.rows }
            .flatMapObservable { Observable.fromIterable(it) }
            .map { t: Product -> t.mapToProductEntity() }
            .toList()
    }
}