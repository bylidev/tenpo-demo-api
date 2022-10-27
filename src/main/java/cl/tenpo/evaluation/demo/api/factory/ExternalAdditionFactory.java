package cl.tenpo.evaluation.demo.api.factory;

import cl.tenpo.evaluation.demo.api.client.DummyClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(prefix = "addition",name = "factory",havingValue = "external")
@Slf4j
public class ExternalAdditionFactory implements AdditionFactory{

    private final static String CACHE_NAME = "additionCache";
    @Autowired
    private DummyClient dummyClient;
    @Override
    @Cacheable(cacheNames = CACHE_NAME)
    @Retryable(value = FeignException.class,maxAttempts = 3)
    public BigDecimal getAddition() {
        log.info("Calling dummy client");
        return dummyClient.getDummyValue();
    }

    @Scheduled(timeUnit = TimeUnit.MINUTES,fixedDelay = 30l)
    @CacheEvict(value = { CACHE_NAME })
    public void clearCache(){
        log.debug("Cache {} cleared.",CACHE_NAME);
    }
}
