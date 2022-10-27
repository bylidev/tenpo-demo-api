package cl.tenpo.evaluation.demo.api.service;

import cl.tenpo.evaluation.demo.api.dto.OperationRequest;
import cl.tenpo.evaluation.demo.api.dto.OperationResponse;

public interface OperationService {
    OperationResponse getResult(OperationRequest operationRequest);
}

