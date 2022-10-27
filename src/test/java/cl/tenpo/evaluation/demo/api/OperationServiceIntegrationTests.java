package cl.tenpo.evaluation.demo.api;

import cl.tenpo.evaluation.demo.api.dto.OperationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
class OperationServiceIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void purgeDb(){
        this.userRepository.deleteAll();
    }

	@Test
    void signin_without_authentication_header_should_throw_401() throws Exception{
        this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(OperationRequest.builder().username("user").password("1234").build())))
            .andExpect(MockMvcResultMatchers.status().is(401));
	}

    @Test
    void signin_without_bad_username_should_throw_401() throws Exception{
        this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(OperationRequest.builder().username("user").password("1234").build())))
            .andExpect(MockMvcResultMatchers.status().is(401));
    }

    @Test
    void signin_should_return_access_token() throws Exception{
        //arrange
        String username = "admin";
        String password = "admin";
        this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(SignupRequest.builder()
                        .email("test@tempo.com")
                        .username(username)
                        .password(password)
                        .roleList(Arrays.asList(Role.ROLE_USER))
                        .build())))
            .andExpect(MockMvcResultMatchers.status().is(200));
        //act - assert
        this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(OperationRequest.builder().username(username).password(password).build())))
            .andExpect(MockMvcResultMatchers.status().is(200))
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(jsonPath("$.token").exists())
        .andExpect(jsonPath("$.username").exists())
        .andExpect(jsonPath("$.email").exists())
        .andExpect(jsonPath("$.roles").isArray())
        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void should_validate_access_token() throws Exception{
        //arrange
        String username = "admin";
        String password = "admin";
        this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(SignupRequest.builder()
                        .email("test@tempo.com")
                        .username(username)
                        .password(password)
                        .roleList(Arrays.asList(Role.ROLE_USER))
                        .build())))
            .andExpect(MockMvcResultMatchers.status().is(200));
        //act
        String jsonResponse = this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(OperationRequest.builder().username(username).password(password).build())))
            .andExpect(MockMvcResultMatchers.status().is(200))
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(jsonPath("$.token").exists())
            .andExpect(jsonPath("$.username").exists())
            .andExpect(jsonPath("$.email").exists())
            .andExpect(jsonPath("$.roles").isArray())
            .andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();

        //assert
        this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization",this.objectMapper.readValue(jsonResponse, JwtResponse.class).getToken()))
            .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    void validate_token_should_return_401() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization",""))
            .andExpect(MockMvcResultMatchers.status().is(401));

    }

    @Test
    void null_token_should_return_401() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/validate")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().is(401));
    }

    @Test
    void expired_jwt_signature_should_return_401() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY2NjYyMzk1MywiZXhwIjoxNjY2NjI0MDEzfQ.35XquKz7ZQDATRakX-YnXzNtFWx2CDCNmRRpdi-IIRYpY6F9MNz95ZxVfcq29BakSD4bBpHtHNCxa-rjlGH2Xw"))
            .andExpect(MockMvcResultMatchers.status().is(401));
    }

    @Test
    void invalid_jwt_signature_should_return_401() throws Exception {
        this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE2NjY2NDExOTcsImV4cCI6MTY5ODE3NzE5NywiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX0.P-qVqcdPsLQL0yv-MagU-wlv7ekACiIHPV36kf-ZBOE"))
            .andExpect(MockMvcResultMatchers.status().is(401));
    }

    @Test
    void signup_should_be_ok() throws Exception{
        this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(SignupRequest.builder()
                        .email("test@tempo.com")
                        .username("admin")
                        .password("admin")
                        .roleList(Arrays.asList(Role.ROLE_USER))
                        .build())))
            .andExpect(MockMvcResultMatchers.status().is(200));
    }
    @Test
    void signup_should_validate_body_400() throws Exception{
        Assertions.assertTrue(
        this.mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(SignupRequest.builder()
                        .email("")
                        .username("")
                        .password("")
                        .roleList(Arrays.asList())
                        .build())))
            .andExpect(MockMvcResultMatchers.status().is(400)).andReturn().getResponse().getContentAsString().contains(
                "cause"
            ));
    }
}
