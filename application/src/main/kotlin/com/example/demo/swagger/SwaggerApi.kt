package com.example.demo.swagger

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/swagger/api")
class SwaggerApi {
    @Operation(summary = "Show swagger index api", description = "swagger index api")
    @ApiResponse(responseCode = "200", description = "swagger index api")
    @GetMapping("/index")
    fun index(): String {
        return "swagger index api"
    }
}
