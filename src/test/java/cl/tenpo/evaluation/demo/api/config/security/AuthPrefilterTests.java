package cl.tenpo.evaluation.demo.api.config.security;

import cl.tenpo.evaluation.demo.api.client.AuthServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RunWith(MockitoJUnitRunner.class)
public class AuthPrefilterTests {

    @InjectMocks
    private AuthPrefilter authPrefilter;
    @Mock
    private AuthServiceClient authServiceClient;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Spy
    private ObjectMapper objectMapper;
    @Mock
    private FilterChain filterChain;

    @Test
    public void withValidTokenShouldAuthenticate() throws Exception {
        //arrange
        String validJwtHeader = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NjY4MzYzNDQsImV4cCI6MTY2NjgzOTk0NH0.1OMtMHERM8XYoprwkTjSWM7qC-Gjxn-aLXgYjDBSgwPTtYD-HVzUTB8QFjV97_Nvo6g7YgCWB935UEN_nmAlbA";
        Mockito.when(this.httpServletRequest.getHeader(Mockito.eq("Authorization"))).thenReturn(validJwtHeader);
        //act
        authPrefilter.doFilterInternal(this.httpServletRequest, this.httpServletResponse, this.filterChain);
        //assert
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assertions.assertThat(authentication.isAuthenticated());
        Assertions.assertThat(authentication.getPrincipal().equals("admin"));
        Assertions.assertThat(authentication.getAuthorities().stream().filter(a -> a.getAuthority().equals("ROLE_USER")).count() > 0);
    }

    @Test
    public void withInvalidTokenShouldNotAuthenticate() throws Exception {
        //arrange
        String validJwtHeader = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NjY4MzYzNDQsImV4cCI6MTY2NjgzOTk0NH0.1OMtMHERM8XYoprwkTjSWM7qC-Gjxn-aLXgYjDBSgwPTtYD-HVzUTB8QFjV97_Nvo6g7YgCWB935UEN_nmAlbA";
        Mockito.when(this.httpServletRequest.getHeader(Mockito.eq("Authorization"))).thenReturn(validJwtHeader);
        Mockito.doThrow(FeignException.class).when(this.authServiceClient).validate(Mockito.anyString());
        //act
        authPrefilter.doFilterInternal(this.httpServletRequest, this.httpServletResponse, this.filterChain);
        //assert
        Assertions.assertThat(Objects.isNull(SecurityContextHolder.getContext().getAuthentication()));
    }
}
