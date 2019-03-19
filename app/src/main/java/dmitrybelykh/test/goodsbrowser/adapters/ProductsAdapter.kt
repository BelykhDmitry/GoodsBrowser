package dmitrybelykh.test.goodsbrowser.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dmitrybelykh.test.goodsbrowser.R
import dmitrybelykh.test.goodsbrowser.entity.ProductEntity
import dmitrybelykh.test.goodsbrowser.services.UriHeaders.urlWithHeaders

class ProductsAdapter() : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private var productList: List<ProductEntity>? = null
    private var loadMoreListener: LoadMoreListener? = null
    private var onItemClickListener: ProductsAdapter.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val holder =
            ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))
        holder.setClickListener(onItemClickListener)
        return holder
    }

    override fun getItemCount(): Int {
        return if (productList != null) productList?.size!! else 0
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val entity = productList?.get(position)
        holder.textView.setText(entity?.name)
        Glide.with(holder.itemView.context)
            .load(
                if (entity?.imageMini != null)
                    urlWithHeaders(entity.imageMini)
                else Color.LTGRAY
            )
            .error(Color.LTGRAY)
            .into(holder.imageView)
        if (position > 0 && position == itemCount.minus(1)) {
            loadMoreListener?.loadMore()
        }
    }

    fun handleOnStart(loadMoreListener: LoadMoreListener, onItemClickListener: OnItemClickListener) {
        this.loadMoreListener = loadMoreListener
        this.onItemClickListener = onItemClickListener
    }

    fun handleOnStop() {
        this.loadMoreListener = null
        onItemClickListener = null
    }

    fun setData(list: List<ProductEntity>) {
        this.productList = list
        notifyDataSetChanged()
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: AppCompatImageView
        val textView: AppCompatTextView
        var listener: OnItemClickListener? = null

        init {
            imageView = itemView.findViewById(R.id.product_image)
            textView = itemView.findViewById(R.id.product_name)
            itemView.setOnClickListener(View.OnClickListener { _ ->
                run {
                    Log.d("dddd", "OnClick")
                    listener?.onItemClick(imageView, layoutPosition)
                }
            })
        }

        fun setClickListener(listener: OnItemClickListener?) {
            this.listener = listener
        }
    }

    interface LoadMoreListener {
        fun loadMore()
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, adapterPosition: Int)
    }
}

