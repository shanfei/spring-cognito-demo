package com.example.demo.controller

import com.example.demo.model.CognitoJWT
import com.example.demo.service.AuthService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Auth endpoints
 */
@RestController
@RequestMapping("/auth")
class AuthController(val authService: AuthService) {
    @Value("\${endpoints.authorize}")
    private val authorizeUrl: String = ""

    @Value("\${endpoints.authorizeSaml}")
    private val authorizeUrlSaml:String = ""

    /**
     * Redirect user to correct url for authorization code
     */
    @GetMapping("/login")
    fun login(): ResponseEntity<Any> =
        ResponseEntity
            .status(HttpStatus.SEE_OTHER)
            .header(HttpHeaders.LOCATION, authorizeUrl)
            .build()

    @GetMapping("/login_saml")
    fun loginSaml(): ResponseEntity<Any> =
        ResponseEntity
            .status(HttpStatus.SEE_OTHER)
            .header(HttpHeaders.LOCATION, authorizeUrlSaml)
            .build()

    /**
     * Get aws tokens with authorization code
     */
    @GetMapping("/token")
    fun token(@RequestParam("code") code: String): CognitoJWT? =
        authService.getToken(code)

    @GetMapping("/token_saml")
    fun tokenSaml(@RequestParam("code") code: String): CognitoJWT? =
        authService.getToken(code)
}
