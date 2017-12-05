package viram.heady

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import com.example.viram.heady_test.ui.category.CategoryFragment
import viram.heady.db.AppDatabase
import viram.heady.inject.component.DaggerActivityComponent
import viram.heady.inject.module.ActivityModule
import viram.heady.util.ActivityUtil
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {


    lateinit var mDb: RoomDatabase // Room Database instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependency()

        initDatabase()

        ActivityUtil().addCategoryFragmentToActivity(supportFragmentManager,
                CategoryFragment().newInstance(), R.id.frame, "CategoryFragment")

        getdatabase()
    }

    private fun getdatabase() {
        try {
            val sd = Environment.getExternalStorageDirectory()

            if (sd.canWrite()) {
                val currentDBPath = "/data/data/$packageName/databases/todo_database"
                val backupDBPath = "backupname.db"
                val currentDB = File(currentDBPath)
                val backupDB = File(sd, backupDBPath)

                if (currentDB.exists()) {
                    val src = FileInputStream(currentDB).channel
                    val dst = FileOutputStream(backupDB).channel
                    dst.transferFrom(src, 0, src.size())
                    src.close()
                    dst.close()
                }
            }
        } catch (e: Exception) {

            e.printStackTrace()
        }

    }
    fun initDatabase(){

        mDb =  Room.databaseBuilder(this,
                AppDatabase::class.java, "todo_database").build()
    }
    override fun onDestroy() {
        super.onDestroy()
    }
    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()
        activityComponent.inject(this)
    }

//    override fun onBackPressed() {
//        if (fragmentManager.backStackEntryCount > 0) {
//            fragmentManager.popBackStack();
//        } else {
//            super.onBackPressed();
//        }
//    }
}
