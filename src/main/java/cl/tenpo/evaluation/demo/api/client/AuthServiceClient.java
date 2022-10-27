package cl.tenpo.evaluation.demo.api.client;

import cl.tenpo.evaluation.demo.api.config.security.AuthPrefilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "authServiceClient",url = "${auth.client.url}")
public interface AuthServiceClient {
    @PostMapping("/api/v1/auth/validate")
    void validate(@RequestHeader(AuthPrefilter.AUTH_HEADER) String authHeader);
}
