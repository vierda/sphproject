package com.sph.demo.newspaper

import com.sph.demo.newspaper.common.data.model.Field
import com.sph.demo.newspaper.common.data.model.Link
import com.sph.demo.newspaper.common.data.model.ResponseReport
import com.sph.demo.newspaper.common.data.model.ResultReport
import com.sph.demo.newspaper.common.network.WebApiService
import com.sph.demo.newspaper.main.data.MainNetworkRepository
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Call


class MainNetworkRepositoryTest {

    lateinit var mockWebServiceApi: WebApiService
    lateinit var mockedCall: Call<ResponseReport>
    lateinit var networkRepository: MainNetworkRepository

    inline fun <reified T : Any> mockClass() = Mockito.mock(T::class.java)

    @Before
    fun setup() {

        mockWebServiceApi = mock(WebApiService::class.java)
        mockedCall = mockClass()
        networkRepository = mock(MainNetworkRepository::class.java)
    }

    @Test
    fun testReturnsResponseReport() {

        val helpStr = "https://data.gov.sg/api/3/action/help_show?name=datastore_search"
        val start = "/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f"
        val next = "/api/action/datastore_search?offset=100&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f"
        val resourceId = "a807b7ab-6cad-4aa6-87d0-e283a7353a0f"

        val links = Link()
        links.start = start
        links.next = next

        val field = Field()
        field.id = "text"
        field.type = "quarter"

        val resultReport = ResultReport()
        resultReport.links = links
        resultReport.total = 100
        resultReport.resourceId = resourceId
        resultReport.fields = listOf(field)

        val response = ResponseReport()
        response.success = true
        response.help = helpStr
        response.result = resultReport

        `when`(mockWebServiceApi.loadAllReport()).thenReturn(mockedCall)

        `when`(networkRepository.loadAllReport()).thenReturn(Observable.just(response))

        networkRepository.loadAllReport()
                .test()
                .assertValue { it.success.equals(true) }
                .assertValue { it.help.equals(helpStr) }
                .assertValue { it.result.equals(resultReport) }
                .assertValue { it.result.resourceId.equals(resourceId) }
                .assertValue { it.result.links.equals(links) }
                .assertValue { it.result.links.start.equals(start) }
                .assertValue { it.result.links.next.equals(next) }
                .assertValue { it.result.total.equals(100) }
                .assertValue { it.result.fields.size == 1 }
                .assertComplete()

    }


    @Test
    fun testReturnEmpty() {
        `when`(mockWebServiceApi.loadAllReport()).thenReturn(mockedCall)

        `when`(networkRepository.loadAllReport()).thenReturn(Observable.empty())

        networkRepository.loadAllReport()
                .test()
                .assertNoValues()
                .assertComplete()
    }

    @Test
    fun testReturnError() {

        val message = "Server Error"
        val serverError = Error(message)
        val throwable = Throwable(serverError)

        `when`(mockWebServiceApi.loadAllReport()).thenReturn(mockedCall)

        `when`(networkRepository.loadAllReport()).thenReturn(Observable.error(throwable))

        networkRepository.loadAllReport()
                .test()
                .assertError(throwable)
                .assertErrorMessage(throwable.message)

    }
}