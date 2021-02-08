package com.dream.tea

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dream.tea.login.LoginUtils
import com.dream.tea.service.ServiceCreator
import com.dream.tea.utils.LogUtils
import com.dream.tea.utils.PropUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initProps()
        LoginUtils.login(this)
        val navigationView: BottomNavigationView = findViewById(R.id.nav_view)
        val navigationController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_library, R.id.navigation_desk, R.id.navigation_person
            )
        )
        setupActionBarWithNavController(navigationController, appBarConfiguration)
        navigationView.setupWithNavController(navigationController)
    }

    override fun onStart() {
        // 后面就可以往这里加逻辑
        // LoginActivity.actionStart(this)
        super.onStart()
    }

    private fun initProps() {
        try {
            val props = PropUtils.getProps(this)
            val logEnable = props["logging.enable"]
            if (logEnable != null) {
                LogUtils.FLAG = ("true" == logEnable.toString())
            }
            Log.i(TAG, logEnable as String)

        } catch (e: Exception) {
            e.message?.let { Log.e(TAG, it) }
        }
    }

    companion object {
        private const val TAG = "MainActivity";
    }
}
