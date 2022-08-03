package com.nusratillo.testtask

import com.nusratillo.testtask.data.model.User
import com.nusratillo.testtask.data.model.UserDevices
import com.nusratillo.testtask.data.preferences.PrefManager
import com.nusratillo.testtask.data.repository.UserDevicesRepository
import com.nusratillo.testtask.domain.UserDevicesInteractor
import com.nusratillo.testtask.domain.UserInteractor
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class UserDeviceInteractorTest {
    @Mock
    private lateinit var userDevicesRepository: UserDevicesRepository

    @Mock
    private lateinit var userInteractor: UserInteractor

    @Mock
    private lateinit var prefManager: PrefManager

    private lateinit var userDevicesInteractor: UserDevicesInteractor

    @Test
    fun testLoadDevicesLogic() {
        whenever(prefManager.isDevicesLoaded).thenReturn(false)
        whenever(userDevicesRepository.getRemoteUserDevices())
            .thenReturn(Observable.just(UserDevices(emptyList(), User())))
        whenever(userDevicesRepository.getUserDevicesByType(emptyList()))
            .thenReturn(Observable.empty())

        userDevicesInteractor =
            UserDevicesInteractor(userDevicesRepository, userInteractor, prefManager)

        assert(userDevicesInteractor.getUserDevicesBy(emptyList()).blockingFirst().isEmpty())
    }
}