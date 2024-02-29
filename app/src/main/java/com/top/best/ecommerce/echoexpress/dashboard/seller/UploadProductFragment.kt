package com.top.best.ecommerce.echoexpress.dashboard.seller

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.github.dhaval2404.imagepicker.ImagePicker
import com.top.best.ecommerce.echoexpress.R
import com.top.best.ecommerce.echoexpress.base.BaseFragment
import com.top.best.ecommerce.echoexpress.core.areAllPermissionGranted
import com.top.best.ecommerce.echoexpress.core.extract
import com.top.best.ecommerce.echoexpress.core.requestPermission
import com.top.best.ecommerce.echoexpress.data.Product
import com.top.best.ecommerce.echoexpress.databinding.FragmentUploadProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadProductFragment : BaseFragment<FragmentUploadProductBinding>(FragmentUploadProductBinding::inflate) {

    private lateinit var product: Product

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

                product = Product(
                    name = name,
                    description = description,
                    price = price.toDouble(),
                    amount = amount.toInt()
                )
                uploadProduct(product)
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

    }
    override fun allObserver() {
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
