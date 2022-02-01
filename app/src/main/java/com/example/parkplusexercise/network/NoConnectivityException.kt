package com.example.parkplusexercise.network

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "Please check your internet connection."
}