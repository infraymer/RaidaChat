package com.cloudcoin.chat.raidachat.Mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.cloudcoin.chat.raidachat.*
import com.cloudcoin.chat.raidachat.Mvp.Presenters.MainPresenter
import org.jetbrains.anko.*
import ru.terrakok.cicerone.android.SupportFragmentNavigator


class MainActivity : MvpAppCompatActivity(), MvpView {

    /***********************************************************************************************
    * Properties
    ***********************************************************************************************/
    @InjectPresenter
    lateinit var mPresenter: MainPresenter
    private val mNavigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.main_container) {
        override fun createFragment(screenKey: String?, data: Any?): Fragment {
            return when(screenKey) {
                Screens.LOGIN -> LoginFragment.newInstance(data)
                Screens.DIALOGS_LIST -> DialogsListFragment.newInstance(data)
                Screens.DIALOG -> DialogFragment.newInstance(data)
                else -> throw RuntimeException("Unknown screen key!")
            }
        }

        override fun exit() {
            finish()
        }

        override fun showSystemMessage(message: String?) {
            toast(message?: "")
        }
    }

    /***********************************************************************************************
    * Override funs
    ***********************************************************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        App.INSTANCE.getNavigatorHolder().setNavigator(mNavigator)
    }

    override fun onPause() {
        super.onPause()
        App.INSTANCE.getNavigatorHolder().removeNavigator()
    }

    /***********************************************************************************************
    * Private funs
    ***********************************************************************************************/
}
