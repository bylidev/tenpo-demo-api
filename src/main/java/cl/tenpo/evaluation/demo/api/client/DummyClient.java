package cl.tenpo.evaluation.demo.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@FeignClient(value = "dummyClient",url = "${dummy.client.url}")
public interface DummyClient {
    @GetMapping("/dummy")
    BigDecimal getDummyValue();
}
