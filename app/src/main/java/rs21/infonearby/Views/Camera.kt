package rs21.infonearby.Views

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.activity_main2.*
import rs21.infonearby.R

abstract class Camera : AppCompatActivity(), View.OnClickListener {

    lateinit var sheetBehavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        Bretry.setOnClickListener {
            if (imageCam.visibility == View.VISIBLE) showPreview() else hidePreview()
        }
        fab_take_photo.setOnClickListener(this)
    }

    fun setupBottomSheet(@LayoutRes id : Int){
        stubView.layoutResource = id
        val inflatedView = stubView.inflate()
        val lparam = inflatedView.layoutParams as CoordinatorLayout.LayoutParams
        lparam.behavior = BottomSheetBehavior<View>()
        inflatedView.layoutParams = lparam
        sheetBehavior = BottomSheetBehavior.from(inflatedView)
        sheetBehavior.peekHeight = 220
        val lp = fabProgressCircle.layoutParams as CoordinatorLayout.LayoutParams
        lp.anchorId = inflatedView.id
        lp.anchorGravity = Gravity.END
        fabProgressCircle.layoutParams = lp
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                fab_take_photo.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        imageCam.start()
    }

    override fun onPause() {
        imageCam.stop()
        super.onPause()
    }

    protected fun showPreview() {
        Pframe.visibility = View.VISIBLE
        imageCam.visibility = View.GONE
    }

    protected fun hidePreview() {
        Pframe.visibility = View.GONE
        imageCam.visibility = View.VISIBLE
    }
}