package com.top.best.ecommerce.echoexpress.view.dashboard.seller.profile

import android.Manifest
import android.app.Activity
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.top.best.ecommerce.echoexpress.base.BaseFragment
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.core.areAllPermissionGranted
import com.top.best.ecommerce.echoexpress.core.extract
import com.top.best.ecommerce.echoexpress.core.requestPermission
import com.top.best.ecommerce.echoexpress.databinding.FragmentSellerProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SellerProfileFragment : BaseFragment<FragmentSellerProfileBinding>(FragmentSellerProfileBinding::inflate) {

    private val viewModel: SellerProfileViewModel by viewModels()
    private var sellerProfile: Profile? = null

    private var hasLocalImageUri: Boolean = false

    private lateinit var permissionRequest: ActivityResultLauncher<Array<String>>
    companion object{
        private val permissionList = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES
        )
    }
    override fun setListener() {
        permissionRequest = getPermissionRequest()

        FirebaseAuth.getInstance()
            .currentUser?.let {
                viewModel.getUserByUserID(it.uid)
            }

        binding.apply {
            ivProfile.setOnClickListener {
                requestPermission(permissionRequest, permissionList)
            }
            btnUpdate.setOnClickListener {
                loading.show()
                val name = etFullName.extract()
                val email = etEmail.extract()

                sellerProfile.apply {
                    this?.name = name
                    this?.email = email
                }
                updateProfile(sellerProfile)
            }
        }
    }

    private fun updateProfile(sellerProfile: Profile?) {
        sellerProfile?.let { viewModel.updateProfile(it, hasLocalImageUri) }
    }

    override fun allObserver() {
        viewModel._profileUpdateResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    loading.dismiss()
                }
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Success -> {
                    Toast.makeText(requireContext(), it.data, Toast.LENGTH_SHORT).show()
                    loading.dismiss()

                }
            }
        }

        viewModel._getUserDataResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error -> {
                    loading.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Success -> {
                    sellerProfile = it.data
                    setProfileData(sellerProfile)
                    loading.dismiss()
                }
            }
        }
    }

    private fun setProfileData(sellerProfile: Profile?) {
        hasLocalImageUri = sellerProfile?.userImage.isNullOrBlank()
        binding.apply {
            etFullName.setText(sellerProfile?.name)
            etEmail.setText(sellerProfile?.email)
            ivProfile.load(sellerProfile?.userImage)
        }

    }

    private fun getPermissionRequest(): ActivityResultLauncher<Array<String>> {
        return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            if (areAllPermissionGranted(permissionList)){   //permission already granted

                ImagePicker.with(this)
                    .compress(1024)         //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(512, 512)  //Final image resolution will be less than 1080 x 1080(Optional)
                    .createIntent { intent ->
                        startForProfileImageResult.launch(intent)
                    }

            }else{      //permission not granted
                Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
                result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    binding.ivProfile.setImageURI(fileUri)
                    sellerProfile?.userImage = fileUri.toString()

                    hasLocalImageUri = true

                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

}
