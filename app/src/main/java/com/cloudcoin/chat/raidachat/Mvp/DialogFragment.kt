package com.cloudcoin.chat.raidachat.Mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.cloudcoin.chat.raidachat.Mvp.Presenters.DialogPresenter
import com.cloudcoin.chat.raidachat.R
import kotlinx.android.synthetic.main.fragment_dialog.*
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import org.jetbrains.anko.appcompat.v7.titleResource

/**
 * Created by infraymer on 29.11.17.
 */
class DialogFragment : MvpAppCompatFragment(), MvpView {

    companion object {
        fun newInstance(data: Any?): DialogFragment {
            val fragment = DialogFragment()
            return fragment
        }
    }

    /***********************************************************************************************
    * Properties
    ***********************************************************************************************/
    @InjectPresenter
    lateinit var mPresenter: DialogPresenter

    /***********************************************************************************************
    * Override funs
    ***********************************************************************************************/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_toolbar.title = "Dialog"
        dialog_toolbar.navigationIconResource = R.drawable.ic_arrow_back_white
        dialog_toolbar.setNavigationOnClickListener { mPresenter.onBackButtonClicked() }
    }
}