package rs21.infonearby.Views

import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import rs21.infonearby.CustomAdapters.ImageAdapter
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.layout_image.*
import rs21.infonearby.R
import java.util.*

class ImageLabel : Camera() {

    private var itemsList: ArrayList<Any> = ArrayList()
    private lateinit var itemAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBottomSheet(R.layout.layout_image)
        rvLabel.layoutManager = LinearLayoutManager(this)
    }

    //Can be done using Cloud API also for better accuracy

    private fun getLabelsFromDevice(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance().visionLabelDetector
        itemsList.clear()
        detector.detectInImage(image)
                .addOnSuccessListener {
                    fabProgressCircle.hide()
                    itemsList.addAll(it)
                    itemAdapter = ImageAdapter(itemsList, false)
                    rvLabel.adapter = itemAdapter
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                }
                .addOnFailureListener {
                    fabProgressCircle.hide()
                    Toast.makeText(baseContext,"Please Retry!",Toast.LENGTH_SHORT).show()
                }
    }

    override fun onClick(v: View?) {
        fabProgressCircle.show()
        imageCam.captureImage { cameraKitImage ->
            getLabelsFromDevice(cameraKitImage.bitmap)
            runOnUiThread {
                showPreview()
                Pimage.setImageBitmap(cameraKitImage.bitmap)
            }
        }
    }

}