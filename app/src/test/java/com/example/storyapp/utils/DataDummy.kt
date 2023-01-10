package com.example.storyapp.utils

import com.example.storyapp.remote.respone.*

object DataDummy {
    fun generateDummySuccesLogin(): LoginResponse{
        return LoginResponse(
            error = false,
            message = "success",
            loginResult = LoginResult(
                userId = "userId",
                name = "name",
                token = "token"
            )
        )
    }
    fun generateDummyErrorLogin(): LoginResponse{
        return LoginResponse(
            error = true,
            message = "invalid password"
        )
    }

    fun generateDummySuccesRegister(): RegisterResponse{
        return RegisterResponse(
            error = false,
            message = "success"
        )
    }

    fun generateDummyErrorResgister(): RegisterResponse{
        return RegisterResponse(
            error = true,
            message = "Email is already taken"
        )
    }

    fun generateDummyDataStory(): GetAllStoryResponse {
        return GetAllStoryResponse(
            error = false,
            message = "success",
            listStory = arrayListOf(
                ListStoryItem(
                    id = "id",
                    name = "name",
                    description = "description",
                    photoUrl = "photoUrl",
                    createdAt = "createdAt",
                    lat = 0.12,
                    lon = 0.11
                )
            )
        )
    }

    fun generateDummyErrorDataStory(): GetAllStoryResponse{
        return GetAllStoryResponse(
            error = true,
            message = "error",
            listStory = arrayListOf(
                ListStoryItem(
                    id = "id",
                    name = "name",
                    description = "description",
                    photoUrl = "photoUrl",
                    createdAt = "createdAt",
                    lat = 0.12,
                    lon = 0.11
                )
            )
        )
    }

    fun generateDummySuccesUpload(): AddNewStoryResponse{
        return AddNewStoryResponse(
            error = false,
            message = "success"
        )
    }

    fun generateDummyErrorUpload(): AddNewStoryResponse{
        return AddNewStoryResponse(
            error = true,
            message = "error"
        )
    }
}