package com.codemobiles.scbauthen

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codemobiles.scbauthen.database.AppDatabase
import com.codemobiles.scbauthen.database.UserEntity
import com.codemobiles.scbauthen.utilities.CMWorkerThread
import com.codemobiles.scbauthen.utilities.showToast
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mDatabaseAdapter: AppDatabase
    lateinit var mCMWorkerThread: CMWorkerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // setup preference
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
        mUsernameEditText.setText(Prefs.getString("username", ""))
        mPasswordEditText.setText(Prefs.getString("password", ""))

        mLoginBtn.setOnClickListener {
            val _username = mUsernameEditText.text.toString()
            val _pass = mPasswordEditText.text.toString()


            val user = UserEntity(null,_username,_pass,0,"scb01")

        val Task = Runnable {
            var result = mDatabaseAdapter.userDao().getUser(_username)
            //--------------------------------------งาน--------------------------------//
            if(result == null){
                //insert
                mDatabaseAdapter.userDao().insert(user)
                showToast("insert success")

            }else if(result.password.equals(_pass)){
                //successfully

                Prefs.putString("username", _username)
                Prefs.putString("password", _pass)
                startActivity(Intent(applicationContext, SuccessActivity::class.java))

            }else{
                //update

                result.password = _pass
                mDatabaseAdapter.userDao().update(result)
                showToast("update success")
            }
        //------------------------------------------------------------------------------//
        }
            mCMWorkerThread.postTask(Task)






        }
        setupDatabase()

        setupWorkerThread()
    }

    private fun setupWorkerThread() {
        mCMWorkerThread = CMWorkerThread("MO_ROOM_DATABASE")
        mCMWorkerThread.start()
    }

    private fun setupDatabase() {
        mDatabaseAdapter = AppDatabase.getInstance(this).also {
            // Instance does not create the database.
            // It will do so once call writableDatabase or readableDatabase
            it.openHelper.readableDatabase
        }
    }
}
