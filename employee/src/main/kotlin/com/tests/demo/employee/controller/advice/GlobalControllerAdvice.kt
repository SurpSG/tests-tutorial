package com.tests.demo.employee.controller.advice

import com.tests.demo.employee.exception.PetroNonGrataException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler
    fun handlePetro(e: PetroNonGrataException): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Reason: ${e.message}")
}
