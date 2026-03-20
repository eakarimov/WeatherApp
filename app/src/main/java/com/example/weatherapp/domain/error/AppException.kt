package com.example.weatherapp.domain.error

sealed class AppException: Exception() {
    class Network: AppException()
    class Server: AppException()
    class Unknown: AppException()
}