package cl.tenpo.evaluation.demo.api.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
public class OperationRequest {
    @NotNull
    private BigDecimal one;
    @NotNull
    private BigDecimal two;
}
