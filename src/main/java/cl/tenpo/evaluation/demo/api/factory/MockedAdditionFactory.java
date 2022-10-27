package cl.tenpo.evaluation.demo.api.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConditionalOnProperty(prefix = "addition",name = "factory",havingValue = "mock")
public class MockedAdditionFactory implements AdditionFactory{

    @Value("${addition.mocked.value}")
    private BigDecimal percentage;
    @Override
    public BigDecimal getAddition() {
        return percentage;
    }
}
