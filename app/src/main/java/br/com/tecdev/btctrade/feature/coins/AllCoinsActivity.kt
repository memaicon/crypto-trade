package br.com.tecdev.btctrade.feature.coins

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import br.com.tecdev.btctrade.R
import br.com.tecdev.btctrade.model.AllCoinsResponse
import br.com.tecdev.btctrade.model.MbtcResponse
import br.com.tecdev.btctrade.util.formatNumber
import kotlinx.android.synthetic.main.activity_all_coins.*
import org.koin.android.ext.android.inject

class AllCoinsActivity : AppCompatActivity() {

    private val viewModel: AllCoinsViewModel by inject()
    private val allCoinsAdapter: AllCoinsAdapter by inject()
    private var coin: MbtcResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_coins)
        setViewModel()
        setOnClick()
        setRecycler()
        setCoinSelected()
}

    private fun setViewModel() {
        viewModel.showMbtcLiveData.observe(this, observerMbtc())
        viewModel.getCoinsLiveData.observe(this, observerGetCoins())
        viewModel.getLastUpdateLiveData.observe(this, observerLastUpdate())
        viewModel.getAllCois()
    }

    private fun setOnClick() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setRecycler() {
        globalCasesRecycler.adapter = allCoinsAdapter
    }

    private fun setCoinSelected() {
        intent.extras?.run {
            coin = getParcelable(COIN_SELECTED)
        }
    }

    /** Observer **/

    private fun observerMbtc() = Observer<MbtcResponse> {
        totalCaseView.visibility = View.VISIBLE
        buyPriceText.text = resources.getString(R.string.currency, formatNumber(it.ticker.buy))
        sellPriceText.text = resources.getString(R.string.currency, formatNumber(it.ticker.sell))
        highPriceText.text = resources.getString(R.string.currency, formatNumber(it.ticker.high))
    }

    private fun observerLastUpdate() = Observer<String> {
        updateText.visibility = View.VISIBLE
        updateText.text = getString(R.string.update_at, it)
    }

    private fun observerGetCoins() = Observer<MutableList<AllCoinsResponse>> {
        allCoinsAdapter.setData(it)
        if (coin != null) {
            val position = allCoinsAdapter.getPositionFromCoin(coin!!.ticker.toString())
            position?.run {
                allCoinsAdapter.listSelected?.set(this, true)
                globalCasesRecycler.scrollToPosition(this)
            }
        } else {
            allCoinsAdapter.notifyDataSetChanged()
        }
        loadView.visibility = View.GONE
    }

    companion object {
        const val COIN_SELECTED = "coin_selected"
    }
}
