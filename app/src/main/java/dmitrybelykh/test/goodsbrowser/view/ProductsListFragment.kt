package dmitrybelykh.test.goodsbrowser.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import butterknife.Unbinder
import dmitrybelykh.test.goodsbrowser.App
import dmitrybelykh.test.goodsbrowser.R
import dmitrybelykh.test.goodsbrowser.adapters.ProductsAdapter
import dmitrybelykh.test.goodsbrowser.entity.ProductEntity
import dmitrybelykh.test.goodsbrowser.presenter.ProductsPresenter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProductsListFragment : Fragment(), ProductsListView {

    lateinit var recyclerView: RecyclerView

    private lateinit var unbinder: Unbinder

    lateinit var adapter: ProductsAdapter

    var presenter: ProductsPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_products_list, container, false)
        recyclerView = rootView.findViewById(R.id.recycler_view)
        setupRecyclerView()
        presenter = (activity?.application as App).initProductsPresenter()
        adapter.handleOnStart(presenter as ProductsAdapter.LoadMoreListener,
            onItemClickListener = object : ProductsAdapter.OnItemClickListener {
                override fun onItemClick(view: View, adapterPosition: Int) {
                    openItemPreview(view, presenter?.getProductAt(adapterPosition)!!)
                }
            })
        return rootView
    }

    fun setupRecyclerView() {
        adapter = ProductsAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()
        presenter?.handleOnStart(this)
        adapter.handleOnStart(presenter as ProductsAdapter.LoadMoreListener,
            onItemClickListener = object : ProductsAdapter.OnItemClickListener {
                override fun onItemClick(view: View, adapterPosition: Int) {
                    openItemPreview(view, presenter?.getProductAt(adapterPosition)!!)
                }
            })
    }

    override fun onStop() {
        adapter.handleOnStop()
        presenter?.handleOnStop()
        super.onStop()
    }

    override fun onDestroyView() {
        presenter = null
        super.onDestroyView()
    }

    override fun setProductsList(list: List<ProductEntity>) {
        adapter.setData(list)
    }

    override fun notifyUsersChanged(position: Int, length: Int) {
        adapter.notifyItemRangeInserted(position, length)
    }

    override fun onError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun openItemPreview(view: View, entity: ProductEntity) {
        val newFragment: ProductsPreviewFragment = ProductsPreviewFragment()
        newFragment.enterTransition = Slide()
        val bundle = Bundle()
        bundle.putString(IMAGE_URI, entity.imageMini)
        newFragment.arguments = bundle
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, newFragment, ProductsPreviewFragment::class.java.name)
            ?.addToBackStack(null)
            ?.commit()
    }
}
