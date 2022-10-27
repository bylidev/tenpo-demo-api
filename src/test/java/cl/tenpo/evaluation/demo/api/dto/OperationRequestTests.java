package cl.tenpo.evaluation.demo.api.dto;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class OperationRequestTests {

    private ValidatorFactory validationFactory = Validation.buildDefaultValidatorFactory();

    @Test
    public void validateRequest() {
        //act
        Set<ConstraintViolation<OperationRequest>> validations = validationFactory.getValidator().validate(
                OperationRequest.builder().build());
        //assert
        Assertions.assertThat(validations.stream().filter(v->v.getMessage().equals("must not be null")).count()).isEqualTo(2l);
    }
}
