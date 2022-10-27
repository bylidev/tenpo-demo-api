package cl.tenpo.evaluation.demo.api.service;

import cl.tenpo.evaluation.demo.api.dto.OperationRequest;
import cl.tenpo.evaluation.demo.api.dto.OperationResponse;
import cl.tenpo.evaluation.demo.api.factory.AdditionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private AdditionFactory additionFactory;

    @Override
    public OperationResponse getResult(OperationRequest operationRequest) {
        return OperationResponse.builder()
                .result(operationRequest.getOne()
                        .add(operationRequest.getTwo())
                        .multiply(additionFactory.getAddition()))
                .build();
    }


}
