package Assignment_2.individual_assignment_2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class APIController {

    @GetMapping("/quote")
    public Object getQuote() {
            try{
                String url = "https://strangerthings-quotes.vercel.app/api/quotes";
                RestTemplate restTemplate = new RestTemplate();
                ObjectMapper mapper = new ObjectMapper();

                String jsonListResponse = restTemplate.getForObject(url, String.class);
                JsonNode root = mapper.readTree(jsonListResponse);

                List<StrangerThingsQuotes> quoteList = new ArrayList<>();
                for (JsonNode rt : root) {
                    //Extract relevant info from the response and use it for what you want, in this case build a Brewery object
                    String author = rt.get("author").asText();
                    String quote = rt.get("quote").asText();

                    StrangerThingsQuotes quoteMe = new StrangerThingsQuotes(author, quote);
                    quoteList.add(quoteMe);
                }
                return quoteList;
            } catch (JsonProcessingException e) {
                Logger.getLogger(APIController.class.getName()).log(Level.SEVERE,
                        null, e);
                return "error in /quote";
            }
    }

}
