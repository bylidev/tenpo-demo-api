package cl.tenpo.evaluation.demo.api.service;

import cl.tenpo.evaluation.demo.api.dto.OperationRequest;
import cl.tenpo.evaluation.demo.api.dto.OperationResponse;
import cl.tenpo.evaluation.demo.api.factory.AdditionFactory;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class OperationServiceTests {

    @InjectMocks
    private OperationServiceImpl operationService ;

    @Mock
    private AdditionFactory additionFactory;

    @Test
    public void validateOperationWithMockedAddition() {
        //arrange
        Mockito.when(this.additionFactory.getAddition()).thenReturn(BigDecimal.valueOf(1.1));
        //act
        OperationResponse response = this.operationService.getResult(OperationRequest.builder()
                .one(BigDecimal.ONE).two(BigDecimal.ONE).build());
        //assert
        Assertions.assertThat(response.getResult()).isEqualTo(BigDecimal.valueOf(2.2));
    }
}
