package com.example.storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.remote.respone.LoginResponse
import com.example.storyapp.utils.DataDummy
import com.example.storyapp.data.Result
import com.example.storyapp.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRUle = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var loginViewModel: LoginViewModel
    private var dummyLoginResponse = DataDummy.generateDummySuccesLogin()
    private val dataDummyEmail = "email"
    private val dataDummyPassword = "password"

    @Before
    fun setup(){
        loginViewModel = LoginViewModel(storyRepository)
    }

    @Test
    fun `when Post Login Should Not Null and Return Success`(){
        val expectedLogin = MutableLiveData<Result<LoginResponse>>()
        expectedLogin.value = Result.Success(dummyLoginResponse)
        Mockito.`when`(storyRepository.postLogin(dataDummyEmail, dataDummyPassword)).thenReturn(expectedLogin)
        val actualResponse = loginViewModel.postLogin(dataDummyEmail, dataDummyPassword).getOrAwaitValue()
        Mockito.verify(storyRepository).postLogin(dataDummyEmail, dataDummyPassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Success)
    }

    @Test
    fun `when Post Login Should Not Null and Return Error`(){
        dummyLoginResponse = DataDummy.generateDummyErrorLogin()
        val expectedLogin = MutableLiveData<Result<LoginResponse>>()
        expectedLogin.value = Result.Error("password invalid")
        Mockito.`when`(storyRepository.postLogin(dataDummyEmail, dataDummyPassword)).thenReturn(expectedLogin)
        val actualResponse = loginViewModel.postLogin(dataDummyEmail,dataDummyPassword).getOrAwaitValue()
        Mockito.verify(storyRepository).postLogin(dataDummyEmail,dataDummyPassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Error)
    }
}