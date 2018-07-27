package com.kotlin.livedata.viewmodel

import android.arch.lifecycle.*
import com.kotlin.livedata.api.APIRepo
import com.kotlin.livedata.util.AbsentLiveData
import com.kotlin.livedata.util.Resource
import okhttp3.RequestBody


open class MultiAPIViewModel:ViewModel() {

    var _requestBody= MutableLiveData<ArrayList<APIRequestData>>()

    var APIRepo: APIRepo = APIRepo()



    /**
     * function return the user login details
     *
     * @return LiveData<Resource<LoginResponse>>
     *
     */
    inline fun <reified T> getResponses(): MediatorLiveData<Resource<T>> {

        val liveDataMerger = MediatorLiveData<Resource<T>>()
        /*lateinit var reqData:MutableLiveData<APIRequestData>
        reqData.value=request*/

        for(request:APIRequestData in _requestBody.value!!) {

            MutableLiveData<APIRequestData>().apply { value=request }.let { liveDataMerger.addSource(getResponse(it), Observer<Resource<T>> { t -> liveDataMerger.value=t }) }
        }

        return  liveDataMerger
    }





    fun <A, B> zipTwoLiveData(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A, B>> {

        return MediatorLiveData<Pair<A, B>>().apply {
            var lastA: A? = null
            var lastB: B? = null

            fun update() {
                val localLastA = lastA
                val localLastB = lastB
                if (localLastA != null && localLastB != null)
                    this.value = Pair(localLastA, localLastB)
            }

            addSource(a) {
                lastA = it
                update()
            }
            addSource(b) {
                lastB = it
                update()
            }
        }
    }

    inline fun <reified A,reified B> getTwiceResponse():LiveData<Pair<Resource<A>, Resource<B>>>{

        var muteAData=MutableLiveData<APIRequestData>()
        muteAData.value=_requestBody.value?.get(0)
        var muteBData=MutableLiveData<APIRequestData>()
        muteBData.value=_requestBody.value?.get(1)
        return zipTwoLiveData(getResponse<A>( muteAData),getResponse<B>(muteBData))


    }


    inline fun <reified A,reified B,reified C> getTripleResponse():LiveData<Triple<Resource<A>, Resource<B>,Resource<C>>>{
        lateinit var liveTwiceData:LiveData<Triple<Resource<A>, Resource<B>,Resource<C>>>
        for(request:APIRequestData in _requestBody.value!!) {
            MutableLiveData<APIRequestData>().apply { value=request }.let {
                liveTwiceData= zipTrippleLiveData(getResponse<A>(it),getResponse<B>(it),getResponse<C>(it))
            }
        }
        return liveTwiceData
    }




    fun <A, B,C> zipTrippleLiveData(a: LiveData<A>, b: LiveData<B>,c: LiveData<C>): LiveData<Triple<A, B,C>> {

        return MediatorLiveData<Triple<A, B,C>>().apply {
            var lastA: A? = null
            var lastB: B? = null
            var lastC :C? =null

            fun update() {
                val localLastA = lastA
                val localLastB = lastB
                val localLastC = lastC
                if (localLastA != null && localLastB != null&&localLastC != null)
                    this.value = Triple(localLastA, localLastB,localLastC)
            }

            addSource(a) {
                lastA = it
                update()
            }
            addSource(b) {
                lastB = it
                update()
            }

            addSource(c) {
                lastC = it
                update()
            }
        }
    }


    inline fun <reified T> getResponse(requestBody: MutableLiveData<APIRequestData>): LiveData<Resource<T>> {

        return Transformations.switchMap(requestBody) { _requestBody ->
            if (_requestBody == null) {
                AbsentLiveData.create()
            } else {
                APIRepo.postAPIRepo<T>(_requestBody)
            }
        }

    }


    inline fun <reified T> getSingleResponse(): LiveData<Resource<T>> {
        return Transformations.switchMap(_requestBody) { requestBody ->
            if (_requestBody == null) {
                AbsentLiveData.create()
            } else {
                APIRepo.postAPIRepo<T>(requestBody.get(0))
            }
        }

    }



    /**
     * set Request Body
     *
     */
    fun setRequestBody(requestBody: ArrayList<APIRequestData>){
            _requestBody.value=requestBody
    }



    /**
     * Retry option to call api again.
     *
     */

    fun retry() {

        _requestBody.value?.let {
            _requestBody.value = it
        }

    }

}