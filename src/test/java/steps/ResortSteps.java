package steps;

import util.InMemoryStorage;
import io.cucumber.java8.En;
import org.testng.Assert;

public class ResortSteps implements En {

    public ResortSteps() {
        And("^the response list is not empty$", () -> {
            Assert.assertFalse(InMemoryStorage.getInstance().getListResponse().getBody().getContent().isEmpty());
        });
    }
}
