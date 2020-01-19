package util;

import com.degg.famateur.model.Resort;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
public class InMemoryStorage {

    private static InMemoryStorage instance = null;
    private String apiUrl;
    private ResponseEntity<PagedResources<Resort>> listResponse;

    public static InMemoryStorage getInstance() {
        if (instance == null) {
            instance = new InMemoryStorage();
        }
        return instance;
    }
}
