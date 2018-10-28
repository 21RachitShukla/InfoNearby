package rs21.infonearby.Views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import rs21.infonearby.CustomAdapters.MainAdapter
import rs21.infonearby.Model.DataPOJO
import kotlinx.android.synthetic.main.activity_main1.*
import rs21.infonearby.R
import java.util.*

class Home : AppCompatActivity() {

    private val DataList by lazy {
        ArrayList<DataPOJO>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)
        this.setTitle(getResources().getString(R.string.app_name));
        with(DataList) {
            add(DataPOJO(R.drawable.info, getString(R.string.title_label), getString(R.string.info_label), 0))
            add(DataPOJO(R.drawable.landmark, getString(R.string.title_landmark), getString(R.string.description_landmark), 1))
        }

        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = MainAdapter(DataList)
    }
}