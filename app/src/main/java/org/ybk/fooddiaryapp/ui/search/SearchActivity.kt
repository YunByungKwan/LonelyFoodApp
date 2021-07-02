package org.ybk.fooddiaryapp.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.maps.geometry.Tm128
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.android.synthetic.main.search_act.*
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.databinding.SearchActBinding
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.MyApplication
import timber.log.Timber
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var searchViewModel: SearchViewModel

    private lateinit var binding: SearchActBinding
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApplication)
            .appComponent.searchComponent().create().inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.search_act)
        binding.activity = this

        naver_map_view.onCreate(savedInstanceState)
        naver_map_view.getMapAsync(this)

        searchViewModel.placeRes.observe(this, androidx.lifecycle.Observer { result ->
            Timber.d(">>>>>>>>>>>>>>>>> ViewModel > placeList > observe()")

            val adapter = SearchResultAdapter(result.items)
            adapter.setOnItemClickListener(object: SearchResultAdapter.OnItemClickListener{
                override fun onItemClick(v: View, pos: Int) {
                    Timber.d(">>>>>>>>>>>>>>>>> onItemClick()")
                    place_recycler_view.visibility = View.GONE
                    val place = result.items[pos]
                    val title = getPlaceTitle(place.title)
                    val address = place.address
                    val latLng = Tm128(place.mapx.toDouble(), place.mapy.toDouble()).toLatLng()
                    Timber.d(">>>>>>>>>>>>>>>>> mapx: ${place.mapx}, mapy: ${place.mapy}")
                    Timber.d(">>>>>>>>>>>>>>>>> 변환된 lat: ${latLng.latitude}, lng: ${latLng.longitude}")

                    val marker = Marker().apply {
                        position = latLng
                        map = naverMap
                        icon = OverlayImage.fromResource(R.drawable.ic_marker)
                    }

                    val infoWindow = InfoWindow()
                    infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this@SearchActivity) {
                        override fun getText(infoWindow: InfoWindow): CharSequence {
                            return getMarkerInfoText(title, address)
                        }
                    }
                    infoWindow.open(marker)

                    // 카메라 시점 이동
                    val cameraUpdate = CameraUpdate.scrollAndZoomTo(latLng, 15.0)
                    cameraUpdate.animate(CameraAnimation.Fly, 2000)
                    naverMap.moveCamera(cameraUpdate)

                    // 마커 클릭시 이벤트
                    marker.setOnClickListener {
                        val intent = Intent().apply {
                            putExtra(Constants.SEL_MARKER_TITLE, title)
                            putExtra(Constants.SEL_MARKER_ADDRESS, address)
                            putExtra(Constants.SEL_MARKER_LAT, latLng.latitude)
                            putExtra(Constants.SEL_MARKER_LNG, latLng.longitude)
                        }
                        setResult(Activity.RESULT_OK, intent)
                        this@SearchActivity.finish()
                        true
                    }
                }
            })
            place_recycler_view.adapter = adapter
            place_recycler_view.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
            place_recycler_view.bringToFront()
        })

        searchview.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!! == "") {
                    place_recycler_view.visibility = View.GONE
                    return true
                }
                place_recycler_view.visibility = View.VISIBLE
                searchViewModel.searchPlace(newText)
                return true
            }
        })
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
    }

    /**
     * 장소 이름에서 <b></b> 태그를 제거한다
     */
    fun getPlaceTitle(str: String?): String {
        return if(str == "" || str == null) {
            ""
        } else {
            str.replace("<b>", "").replace("</b>", "")
        }
    }

    /**
     * 마커 클릭시 나오는 정보창 텍스트를 반환한다
     */
    fun getMarkerInfoText(title: String, address: String)
            = "맛집명: $title\n주소: ${address}\n\n검색한 장소가 맞다면 클릭!"
}