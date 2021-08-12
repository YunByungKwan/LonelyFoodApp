package org.ybk.fooddiaryapp.presentation.map

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.map_frag.*
import org.ybk.fooddiaryapp.R
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.databinding.MapFragBinding
import org.ybk.fooddiaryapp.presentation.login.LoginViewModel
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.MyApplication
import org.ybk.fooddiaryapp.util.Utils
import org.ybk.fooddiaryapp.util.compat.ImageCompat
import org.ybk.fooddiaryapp.util.compat.PermissionCompat
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MapFragment : Fragment(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: MapViewModelFactory

    lateinit var mapViewModel: MapViewModel

    private lateinit var binding: MapFragBinding
    private lateinit var naverMap: NaverMap
    private lateinit var mLocationSource: FusedLocationSource
    private val email = FirebaseAuth.getInstance().currentUser?.email

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().applicationContext as MyApplication)
            .appComponent.mapComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapViewModel = ViewModelProvider(
            this, viewModelFactory).get(MapViewModel::class.java)

        binding = MapFragBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@MapFragment
            viewmodel = mapViewModel
        }

        mapViewModel.getDiaryAll2(email!!)

        mLocationSource = FusedLocationSource(this, Constants.PERM_LOCATION_CODE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        naver_map_view.onCreate(savedInstanceState)
        naver_map_view.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        Timber.d(">>>>>>>>>>>>>>>>> onMapReady()")
        this.naverMap = naverMap
        this.naverMap.locationSource = mLocationSource
        this.naverMap.mapType = NaverMap.MapType.Navi

        mapViewModel.diaryList.observe(viewLifecycleOwner, Observer { diaryList ->
            drawMarker(diaryList)
        })

        val locationPerms = arrayOf(Constants.PERM_ACC_COARSE_LOC, Constants.PERM_ACC_FINE_LOC)
        if(PermissionCompat.existAllPermission(locationPerms)) {
            Timber.d(">>>>>>>>>>>>>>>>> Location permission is OK.")
            this.naverMap.locationTrackingMode = LocationTrackingMode.Follow
        } else {
            if(!PermissionCompat.existsDenialPermission(requireActivity(), locationPerms)) {
                requestLocationPermission.launch(
                    arrayOf(Constants.PERM_ACC_FINE_LOC, Constants.PERM_ACC_COARSE_LOC))
            } else {
                Utils.showShortToast(requireContext(), getString(R.string.need_location))
            }
        }
    }

    fun onClickLocationIcon() {
        val locationPerms = arrayOf(Constants.PERM_ACC_COARSE_LOC, Constants.PERM_ACC_FINE_LOC)
        if(PermissionCompat.existAllPermission(locationPerms)) {
            this.naverMap.locationTrackingMode = LocationTrackingMode.Follow
        } else {
            if(!PermissionCompat.existsDenialPermission(requireActivity(), locationPerms)) {
                requestLocationPermission.launch(
                    arrayOf(Constants.PERM_ACC_FINE_LOC, Constants.PERM_ACC_COARSE_LOC))
            } else {
                Utils.showShortToast(requireContext(), getString(R.string.need_location))
            }
        }
    }

    private fun drawMarker(diaryList: ArrayList<Diary>) {
        diaryList.forEach { diary ->
            val marker = Marker().apply {
                position = LatLng(diary.mapx.toDouble(), diary.mapy.toDouble())
                map = naverMap
                icon = OverlayImage.fromResource(R.drawable.ic_marker)
            }
            var open = false

            // 말풍선
            val infoWindow = InfoWindow()
            infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(requireContext()) {
                override fun getText(infoWindow: InfoWindow): CharSequence {
                    return "${diary.name}\n${getMarkerComment(diary.registerTime)}"
                }
            }

            marker.setOnClickListener {
                open = if(!open) {
                    infoWindow.open(marker)
                    true
                } else {
                    infoWindow.close()
                    false
                }
                true
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getMarkerComment(timeMillis: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = Date(timeMillis.toLong())
        val dateFormat = sdf.format(date)

        return dateFormat + getString(R.string.marker_suffix)
    }

    private val requestLocationPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultMap ->
        if(resultMap[Constants.PERM_ACC_COARSE_LOC]!!
            && resultMap[Constants.PERM_ACC_FINE_LOC]!!
        ) {
            this.naverMap.locationTrackingMode = LocationTrackingMode.Follow
        } else {
            ImageCompat.showSettingDialog(requireContext(), Constants.PERM_ACC_FINE_LOC)
        }
    }
}