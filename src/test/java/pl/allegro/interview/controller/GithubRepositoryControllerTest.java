package pl.allegro.interview.controller;

import org.apache.commons.io.IOUtils;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import pl.allegro.interview.model.remote.GithubRepositoryResourceExternalDto;

import java.net.ConnectException;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class GithubRepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @After
    public void tearDown() {
        mockServer.verify();
    }

    @Test
    public void shouldReturnRepository() throws Exception {

        // given
        String repoJson = IOUtils.toString(getClass().getResourceAsStream("/integration/json/repo.json"));

        // when
        mockServer.expect(requestTo("https://api.github.com/repos/test-owner/test-repo"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(repoJson, MediaType.APPLICATION_JSON));

        // then
        this.mockMvc.perform(get("/repositories/test-owner/test-repo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", is("octocat/Hello-World")))
                .andExpect(jsonPath("$.description", is("This your first repo!")))
                .andExpect(jsonPath("$.cloneUrl", is("https://github.com/octocat/Hello-World.git")))
                .andExpect(jsonPath("$.stars", is(80)))
                .andExpect(jsonPath("$.createdAt", is("2011-01-26T19:01:12Z")));
    }

    @Test
    public void shouldReturnRepositoryWhenNullFieldValues() throws Exception {

        // given
        String repoJson = IOUtils.toString(getClass().getResourceAsStream("/integration/json/null-values-repo.json"));

        // when
        mockServer.expect(requestTo("https://api.github.com/repos/test-owner/test-repo"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(repoJson, MediaType.APPLICATION_JSON));

        // then
        this.mockMvc.perform(get("/repositories/test-owner/test-repo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.description").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.cloneUrl").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.stars").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.createdAt").value(IsNull.nullValue()));
    }

    @Test
    public void shouldReturnNotFoundCodeAndMessageWhenWrongRepoDataProvided() throws Exception {

        // given
        String notFoundJson = IOUtils.toString(getClass().getResourceAsStream("/integration/json/not-found.json"));

        // when
        mockServer.expect(requestTo("https://api.github.com/repos/test-owner/not-found"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND).body(notFoundJson));

        // then
        this.mockMvc.perform(get("/repositories/test-owner/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is("EXC_001")))
                .andExpect(jsonPath("$.message", is("Github repository with this owner and name could not be found.")));
    }

    @Test
    public void shouldReturnUnavailableCodeWhenGithubApiIsNotResponding() throws Exception {

        // when
        doThrow(new ResourceAccessException("", new ConnectException()))
                .when(restTemplate)
                .getForEntity("https://api.github.com/repos/test-owner/test-repo", GithubRepositoryResourceExternalDto.class);

        // then
        this.mockMvc.perform(get("/repositories/test-owner/test-repo"))
                .andExpect(status().is(HttpStatus.SERVICE_UNAVAILABLE.value()))
                .andExpect(jsonPath("$.code", is("EXC_002")))
                .andExpect(jsonPath("$.message", is("Github API is currently unavailable. Try later.")));
    }

    @Test
    public void shouldReturnGenericErrorWhenUnhandledHttpCode() throws Exception {

        // when
        doThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN))
                .when(restTemplate)
                .getForEntity(anyString(), any(Class.class));

        // then
        this.mockMvc.perform(get("/repositories/test-owner/test-repo"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code", is("EXC_000")))
                .andExpect(jsonPath("$.message", is("The application has encountered an unknown error.")));
    }
}
