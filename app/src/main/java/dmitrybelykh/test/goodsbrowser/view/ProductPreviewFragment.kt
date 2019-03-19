package dmitrybelykh.test.goodsbrowser.view


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import dmitrybelykh.test.goodsbrowser.R
import dmitrybelykh.test.goodsbrowser.services.UriHeaders

const val IMAGE_URI = "ImageUri"

class ProductsPreviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_product_preview, container, false)
        val imageView: AppCompatImageView = rootView.findViewById(R.id.product_image)
        val uri: String? = arguments?.getString(IMAGE_URI)
        Glide.with(rootView.context)
            .load(
                if (uri != null) UriHeaders.urlWithHeaders(uri)
                else Color.LTGRAY
            )
            .error(Color.LTGRAY)
            .into(imageView)
        return rootView
    }


}
