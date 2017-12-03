package viram.heady.base

/**
 * Created by viram on 12/3/2017.
 */
class BaseCategory {
    interface Presenter<in T>{
        fun subscribe()
        fun unSubscribe()
        fun attachView(view : T)
    }

    interface View{
        
    }
}