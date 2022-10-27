package cl.tenpo.evaluation.demo.api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class OperationResponse {
    private BigDecimal result;
}
