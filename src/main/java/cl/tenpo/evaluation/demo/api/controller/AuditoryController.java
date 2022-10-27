package cl.tenpo.evaluation.demo.api.controller;

import cl.tenpo.evaluation.demo.api.model.Auditory;
import cl.tenpo.evaluation.demo.api.model.HttpAuditory;
import cl.tenpo.evaluation.demo.api.service.AuditoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;

@RestController
@RequestMapping("/api/v1/auditory")
@Validated
@OpenAPIDefinition(servers = {
        @Server(url = "https://api.byli.dev/demo-api", description = "byli.dev")
})
@SecurityScheme(name ="Bearer Authentication", in = HEADER, type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class AuditoryController {
    @Autowired
    private AuditoryService auditoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping()
    @SecurityRequirement(name = "Bearer Authentication")
    public Page<HttpAuditory> getHttpHistory(Pageable pageable) {
       return this.auditoryService.findHttpAuditory(pageable);
    }

}
