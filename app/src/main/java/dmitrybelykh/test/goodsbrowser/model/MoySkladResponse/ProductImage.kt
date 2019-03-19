package dmitrybelykh.test.goodsbrowser.model.MoySkladResponse

import com.google.gson.annotations.SerializedName

class ProductImage(
    @SerializedName("meta")
    val imageFull: ImageMeta,
    @SerializedName("miniature")
    val imageMini: ImageMeta
)

class ImageMeta(
    @SerializedName("href")
    val href: String
)