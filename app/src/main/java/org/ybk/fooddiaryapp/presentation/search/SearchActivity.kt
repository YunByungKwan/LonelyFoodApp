package org.ybk.fooddiaryapp.presentation.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.Tm128
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_act.*
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.databinding.SearchActBinding
import org.ybk.fooddiaryapp.util.*

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: SearchActBinding

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var naverMap: NaverMap
    private val mMarker = Marker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.search_act)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        naver_map_view.onCreate(savedInstanceState)
        naver_map_view.getMapAsync(this)

        searchview.setOnQueryTextListener(onQueryTextListener)
        observingData()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
    }

    private fun observingData() {
        viewModel.placeResponse.observe(this, { result ->
            val adapter = binding.placeRecyclerView.adapter as SearchResultAdapter
            adapter.setOnItemClickListener(object: SearchResultAdapter.OnItemClickListener {
                override fun onItemClick(v: View, pos: Int) {
                    binding.placeRecyclerView.hide()

                    // 이전 마커 제거
                    mMarker.map = null

                    val place = result.items[pos]
                    val title = place.title
                    val address = place.address
                    val latLng = Tm128(
                        place.mapx.toDouble(),
                        place.mapy.toDouble()
                    ).toLatLng()

                    mMarker.apply {
                        position = latLng
                        map = naverMap
                        icon = OverlayImage.fromResource(R.drawable.ic_marker)
                        setOnClickListener {
                            val intent = Intent().apply {
                                putExtra(Const.M_TITLE, title)
                                putExtra(Const.M_ADDRESS, address)
                                putExtra(Const.M_LAT, latLng.latitude)
                                putExtra(Const.M_LNG, latLng.longitude)
                            }
                            setResult(Activity.RESULT_OK, intent)
                            this@SearchActivity.finish()
                            true
                        }
                    }

                    // 마커 위 말풍선에 데이터 셋팅, 오픈
                    InfoWindow().setDataAndOpen(
                        this@SearchActivity,
                        title, address, mMarker)

                    // 마커 위치로 카메라 이동
                    naverMap.moveCamera(
                        CameraUpdate
                            .scrollAndZoomTo(latLng, MARKER_CAMERA_ZOOM)
                            .animate(CameraAnimation.Fly, MARKER_CAMERA_MOVE_DURATION)
                    )
                }
            })
        })
    }

    /** 검색뷰 리스너 */
    private val onQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if(newText == null) {
                return true
            }
            if(newText.isEmpty()) {
                binding.placeRecyclerView.hide()
                return true
            }
            binding.placeRecyclerView.show()
            viewModel.searchPlace(newText)
            return true
        }
    }

    companion object {
        const val MARKER_CAMERA_ZOOM = 15.0
        const val MARKER_CAMERA_MOVE_DURATION = 2000L
    }
}