package cl.tenpo.evaluation.demo.api.config.security;

import cl.tenpo.evaluation.demo.api.client.AuthServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AuthPrefilter extends OncePerRequestFilter {

    public final static String AUTH_HEADER = "Authorization";
    @Autowired
    private AuthServiceClient authServiceClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String auth = request.getHeader(AUTH_HEADER);
            authServiceClient.validate(auth);
            String jsonPayload = new String(Base64.getDecoder().decode(auth.replace("Bearer ", "").split("\\.")[1]));
            this.addAuthenticationContext(this.objectMapper.readValue(jsonPayload,JwtPayload.class));
        }catch (FeignException e){
            log.warn("Authentication filter denial ({})",e.status());
        }finally {
            filterChain.doFilter(request, response);
        }
    }

    private void addAuthenticationContext(JwtPayload payload) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                payload.getSub(), null, payload.getAuthorities()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r))
                .collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
