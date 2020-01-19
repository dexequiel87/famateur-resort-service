package steps;

import com.degg.famateur.model.Resort;
import io.cucumber.java8.En;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import util.FamateurApiTestConfiguration;
import util.InMemoryStorage;

@ContextConfiguration(classes = {FamateurApiTestConfiguration.class})
public class Common implements En {

    private final InMemoryStorage ims = InMemoryStorage.getInstance();

    @Autowired
    private RestTemplate restTemplate;

    public Common() {
        Given("^Api url is set$", () -> {
            InMemoryStorage.getInstance().setApiUrl("http://localhost:8090/api/");
        });
        And("^a GET request is done against the \"([^\"]*)\" endpoint$", (String endpoint) -> {
            try {
                ResponseEntity<PagedResources<Resort>> response = restTemplate.exchange(
                        InMemoryStorage.getInstance().getApiUrl() + endpoint,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PagedResources<Resort>>(){});
                ims.setListResponse(response);
            } catch (HttpClientErrorException e) {
            }
        });
        Then("^a valid status code (\\d+) is received$", (Integer statusCode) -> {
            Assert.assertEquals((Integer) ims.getListResponse().getStatusCodeValue(), statusCode);
        });
    }

}
