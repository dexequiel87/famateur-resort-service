package util;

//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.hateoas.MediaTypes;
//import org.springframework.hateoas.hal.Jackson2HalModule;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.client.RestTemplate;

//@Configuration
public class FamateurApiTestConfiguration {

//    @Bean
//    public RestTemplate restTemplate() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
//                false);
//        mapper.registerModule(new Jackson2HalModule());
//
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
//        converter.setObjectMapper(mapper);
//
//        RestTemplateBuilder builder = new RestTemplateBuilder();
//        return builder.messageConverters(converter).build();
//    }
}
