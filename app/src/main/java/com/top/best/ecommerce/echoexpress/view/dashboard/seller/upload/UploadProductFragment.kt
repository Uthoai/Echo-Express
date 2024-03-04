package com.top.best.ecommerce.echoexpress.view.dashboard.seller.upload

import android.Manifest
import android.app.Activity
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.top.best.ecommerce.echoexpress.base.BaseFragment
import com.top.best.ecommerce.echoexpress.core.DataState
import com.top.best.ecommerce.echoexpress.core.areAllPermissionGranted
import com.top.best.ecommerce.echoexpress.core.extract
import com.top.best.ecommerce.echoexpress.core.requestPermission
import com.top.best.ecommerce.echoexpress.data.Product
import com.top.best.ecommerce.echoexpress.databinding.FragmentUploadProductBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class UploadProductFragment : BaseFragment<FragmentUploadProductBinding>(FragmentUploadProductBinding::inflate) {

    private val viewModel: ProductUploadViewModel by viewModels()

    private val product: Product by lazy{
        Product()
    }

    private lateinit var permissionRequest: ActivityResultLauncher<Array<String>>
    companion object{
        private val permissionList = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES
        )
    }

    override fun setListener() {
        permissionRequest = getPermissionRequest()

        binding.apply {

            ivProduct.setOnClickListener {
                requestPermission(permissionRequest, permissionList)
            }

            btnUploadProduct.setOnClickListener {
                loading.show()
                val name = etProductName.extract()
                val description = etProductDescription.extract()
                val price = etProductPrice.extract()
                val amount = etProductAmount.extract()

                FirebaseAuth.getInstance().currentUser?.let {
                    product.apply {
                        this.productID = UUID.randomUUID().toString()
                        this.name = name
                        this.description = description
                        this.price = price.toDouble()
                        this.amount = amount.toInt()
                        this.sellerID = it.uid
                    }
                    uploadProduct(product)
                }
            }
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
                Toast.makeText(requireContext(), "not", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadProduct(product: Product) {
        viewModel.productUpload(product)
    }
    override fun allObserver() {

        viewModel._productUploadResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error -> {
                    loading.dismiss()
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Success -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                    loading.dismiss()
                }
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
                    binding.ivProduct.setImageURI(fileUri)
                    product.imageLink = fileUri.toString()
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
