package br.com.tecdev.btctrade.feature.home

import android.app.Activity
import android.content.*
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import br.com.tecdev.btctrade.R
import br.com.tecdev.btctrade.feature.coins.AllCoinsActivity
import br.com.tecdev.btctrade.feature.credits.CreditsActivity
import br.com.tecdev.btctrade.feature.guide.GuideActivity
import br.com.tecdev.btctrade.model.MbtcResponse
import br.com.tecdev.btctrade.util.formatNumber
import br.com.tecdev.btctrade.util.setTransparentStatusBar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import org.koin.android.ext.android.inject
import pub.devrel.easypermissions.EasyPermissions

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by inject()
    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setTransparentStatusBar(false)
        setBottomHomeFragment()
        setDrawerLayout()
        setViewModel()
        setOnClick()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            buyPriceText.text =  formatNumber(0.0)
            sellPriceText.text = formatNumber(0.0)
            highPriceText.text = formatNumber(0.0)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView)
        } else {
            moveTaskToBack(true)
        }
    }

    /** Init & set methods **/

    private fun setBottomHomeFragment() {
        bottomSheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)
    }

    private fun setDrawerLayout() {
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_quotations -> {
                    goToAllCoins()
                }
                R.id.nav_transactions -> {
                    goToTransactions()
                }
                R.id.nav_guide -> {
                    goToGuide()
                }
                R.id.nav_share -> {
                    goToShareApp()
                }
                R.id.nav_credits -> {
                    goToCredits()
                }
            }
            drawerLayout.closeDrawer(navigationView)
            true
        }
    }

    private fun setViewModel() {
        viewModel.showMbtcLiveData.observe(this, observerMbtc())
        viewModel.getMbtc()
    }

    private fun setOnClick() {
        menuButton.setOnClickListener {
            drawerLayout.openDrawer(navigationView)
        }
        knowMoreButton.setOnClickListener {
            goToTransactions()
        }
        allCoinsView.setOnClickListener {
            goToAllCoins()
        }
    }

    /** Go to **/

    private fun goToTransactions() {
        startActivity(Intent(this, GuideActivity::class.java))
    }

    private fun goToGuide() {
        startActivity(Intent(this, GuideActivity::class.java))
    }

    private fun goToShareApp() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
        shareIntent.type = "text/plain"
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_app)))
    }

    private fun goToCredits() {
        startActivity(Intent(this, CreditsActivity::class.java))
    }

    private fun goToAllCoins() {
        startActivity(Intent(this, AllCoinsActivity::class.java))
    }

    /** Observers **/

    private fun observerMbtc() = Observer<MbtcResponse> {
        titleCasesText.text = getString(R.string.home_bottom_dialog_title)
        buyPriceText.text = resources.getString(R.string.currency, formatNumber(it.ticker.buy))
        sellPriceText.text = resources.getString(R.string.currency, formatNumber(it.ticker.sell))
        highPriceText.text = resources.getString(R.string.currency, formatNumber(it.ticker.high))
    }

    companion object {
        private const val RQ_STORAGE = 2
    }
}
