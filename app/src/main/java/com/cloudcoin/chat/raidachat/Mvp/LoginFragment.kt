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
import com.cloudcoin.chat.raidachat.Mvp.Presenters.LoginPresenter
import com.cloudcoin.chat.raidachat.R
import kotlinx.android.synthetic.main.fragment_dialog.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.appcompat.v7.titleResource

/**
 * Created by infraymer on 29.11.17.
 */
class LoginFragment : MvpAppCompatFragment(), MvpView {

    companion object {
        fun newInstance(data: Any?): LoginFragment {
            val fragment = LoginFragment()
            return fragment
        }
    }

    /***********************************************************************************************
    * Properties
    ***********************************************************************************************/
    @InjectPresenter
    lateinit var mPresenter: LoginPresenter

    /***********************************************************************************************
    * Override funs
    ***********************************************************************************************/
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val appCompatActivity = activity as AppCompatActivity
//        appCompatActivity.setSupportActionBar(dialog_toolbar)
//        appCompatActivity.supportActionBar?.setTitle(R.string.login_title)

        login_toolbar.titleResource = R.string.login_title

        login_sign_in_btn.setOnClickListener { mPresenter.onSignInButtonClicked(getLogin(), getPassword()) }
    }

    private fun getLogin() = login_username.text.toString()

    private fun getPassword() = login_password.text.toString()
}