package com.example.storyapp.ui.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.remote.respone.AddNewStoryResponse
import com.example.storyapp.utils.DataDummy
import com.example.storyapp.utils.getOrAwaitValue
import com.example.storyapp.data.Result
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var addStoryViewModel: AddStoryViewModel
    private var dataDummyResponse = DataDummy.generateDummySuccesUpload()
    private var dataDummyDesc = "description".toRequestBody("text/plain".toMediaType())
    private var dataDummyLat = null
    private var dataDummyLon = null

    private val file: File = Mockito.mock(File::class.java)
    private val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
    private val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
        "photo",
        file.name,
        requestImageFile
    )

    @Before
    fun setup(){
        addStoryViewModel = AddStoryViewModel(storyRepository)
    }

    @Test
    fun `when Post Create Story Should Not Null and Return Success`() {
        val expected = MutableLiveData<Result<AddNewStoryResponse>>()
        expected.value = Result.Success(dataDummyResponse)
        Mockito.`when`(
            storyRepository.uploadImage(
                imageFile = imageMultipart,
                desc = dataDummyDesc,
                lat = dataDummyLat,
                lon = dataDummyLon
            )
        ).thenReturn(expected)

        val actual= addStoryViewModel.uploadImage(
            imageFile = imageMultipart,
            desc = dataDummyDesc,
            lat = dataDummyLat,
            lon = dataDummyLon
        ).getOrAwaitValue()

        Mockito.verify(storyRepository).uploadImage(
            imageFile = imageMultipart,
            desc = dataDummyDesc,
            lat = dataDummyLat,
            lon = dataDummyLon
        )
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
    }

    @Test
    fun `when Post Create Story Should Not Null and Return Error`() {
        dataDummyResponse = DataDummy.generateDummyErrorUpload()

        val expected = MutableLiveData<Result<AddNewStoryResponse>>()
        expected.value = Result.Error("error")
        Mockito.`when`(
            storyRepository.uploadImage(
                imageFile = imageMultipart,
                desc = dataDummyDesc,
                lat = dataDummyLat,
                lon = dataDummyLon
            )
        ).thenReturn(expected)

        val actual = addStoryViewModel.uploadImage(
            imageFile = imageMultipart,
            desc = dataDummyDesc,
            lat = dataDummyLat,
            lon = dataDummyLon
        ).getOrAwaitValue()

        Mockito.verify(storyRepository).uploadImage(
            imageFile = imageMultipart,
            desc = dataDummyDesc,
            lat = dataDummyLat,
            lon = dataDummyLon
        )
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Error)
    }
}
