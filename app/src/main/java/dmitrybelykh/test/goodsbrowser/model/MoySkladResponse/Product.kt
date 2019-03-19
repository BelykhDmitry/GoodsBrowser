package dmitrybelykh.test.goodsbrowser.model.MoySkladResponse

import com.google.gson.annotations.SerializedName
import dmitrybelykh.test.goodsbrowser.entity.ProductEntity

class Product(
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: ProductImage? = null
) {
    fun mapToProductEntity(): ProductEntity {
        return ProductEntity(name, image?.imageMini?.href, image?.imageFull?.href)
    }
}