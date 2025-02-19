package ru.pachan.reader_kotlin.exception.data

import org.springframework.http.HttpStatus

data class RequestException(
    override val message: String,
    val httpStatus: HttpStatus,
) : Exception()