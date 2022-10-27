package cl.tenpo.evaluation.demo.api.aop;

import cl.tenpo.evaluation.demo.api.model.HttpAuditory;
import cl.tenpo.evaluation.demo.api.service.AuditoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class AuditableAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuditoryService auditoryService;

    @AfterReturning(value = "@annotation(Auditable)", returning = "result")
    public void audit(JoinPoint joinPoint, Object result) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        String arguments = Arrays.stream(joinPoint.getArgs()).map(a -> a.toString()).collect(Collectors.joining(","));

        this.auditoryService.save(HttpAuditory.builder()
                .className(className)
                .methodName(methodName)
                .argument(arguments)
                .uri(request.getRequestURI())
                .status(response.getStatus())
                .request(this.objectMapper.writeValueAsString(this.getRequestBody(joinPoint)))
                .response(this.objectMapper.writeValueAsString(result))
                .build());
    }

    private Object getRequestBody(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] annotationMatrix = methodSignature.getMethod().getParameterAnnotations();
        int index = -1;
        for (Annotation[] annotations : annotationMatrix) {
            index++;
            for (Annotation annotation : annotations) {
                if (annotation instanceof RequestBody)
                return joinPoint.getArgs()[index];
            }
        }
        return null;
    }
}
