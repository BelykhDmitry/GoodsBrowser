package dmitrybelykh.test.goodsbrowser.model.MoySkladResponse

import com.google.gson.annotations.SerializedName

class ProductsResponse(
    @SerializedName("rows")
    val rows: List<Product>? = null
)