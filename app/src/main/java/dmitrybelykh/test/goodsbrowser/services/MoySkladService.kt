package dmitrybelykh.test.goodsbrowser.services

import dmitrybelykh.test.goodsbrowser.model.MoySkladResponse.ProductsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoySkladService {
    @GET("entity/product")
    fun getGoods(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int? = 20
    )
            : Single<ProductsResponse>
}