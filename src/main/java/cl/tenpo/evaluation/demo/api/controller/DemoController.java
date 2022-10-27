package cl.tenpo.evaluation.demo.api.controller;

import cl.tenpo.evaluation.demo.api.aop.Auditable;
import cl.tenpo.evaluation.demo.api.dto.OperationRequest;
import cl.tenpo.evaluation.demo.api.dto.OperationResponse;
import cl.tenpo.evaluation.demo.api.service.OperationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;

@RestController
@RequestMapping("/api/v1/demo")
@Validated
@OpenAPIDefinition(servers = {
        @Server(url = "https://api.byli.dev/demo-api", description = "byli.dev")
})
@Slf4j
@SecurityScheme(name ="Bearer Authentication", in = HEADER, type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class DemoController {
    @Autowired
    private OperationService operationService;

    @Auditable
    @PostMapping("/operation")
    @SecurityRequirement(name = "Bearer Authentication")
    public OperationResponse authenticateUser(@Valid @RequestBody OperationRequest operationRequest) {
       return this.operationService.getResult(operationRequest);
    }
}
