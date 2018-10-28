package rs21.infonearby.Views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_main2.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import rs21.infonearby.R
import java.io.File


class Landmark : Camera() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBottomSheet(R.layout.layout_landmark)
        imageCam.visibility = View.GONE
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, Array<String>(1) { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 12345)
            fab_take_photo.setOnClickListener(null)
        } else {
            fab_take_photo.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        startIntentForPicker()
    }

    private fun startIntentForPicker() {
        EasyImage.openGallery(this, 0)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 12345) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                fab_take_photo.setOnClickListener(this)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {

                override fun onImagePicked(imageFile: File?, source: EasyImage.ImageSource?, type: Int) {
                    val bitmap = BitmapFactory.decodeFile(imageFile?.path)
                    getLandmarkFromCloud(bitmap)
                    Pimage.setImageBitmap(bitmap)
                    Pframe.visibility = View.VISIBLE
                    Bretry.visibility = View.GONE
                }

                override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                }
            })
        }
    }

    private fun getLandmarkFromCloud(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance()
                .visionCloudLandmarkDetector
        //Can only be done using Cloud API
        detector.detectInImage(image)
                .addOnCompleteListener {
                    //                    for (firebaseVisionLandmarks in it.result) {
//                        val landmark = firebaseVisionLandmarks.landmark
//                        tvLocationName.text = landmark
//                        for (location in firebaseVisionLandmarks.locations) {
//                            val lat = location.latitude
//                            val long = location.longitude
//                            tvLatitude.text = lat.toString()
//                            tvLongitude.text = long.toString()
//                        }
//                        tvPrecision.text = (firebaseVisionLandmarks.confidence * 100).toInt().toString()
//                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Can't fetch data from cloud API!", Toast.LENGTH_SHORT).show()
                }
                .addOnCompleteListener {
                    sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
    }

}