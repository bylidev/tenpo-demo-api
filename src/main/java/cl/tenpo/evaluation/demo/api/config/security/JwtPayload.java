package cl.tenpo.evaluation.demo.api.config.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtPayload {
    String sub;
    List<String> authorities;
}
