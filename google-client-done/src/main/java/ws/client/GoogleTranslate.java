package ws.client;


import ws.client.model.Output;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class GoogleTranslate {

    private final String API_KEY;

    public GoogleTranslate(String api_key) {
        API_KEY = api_key;
    }

    public String translationFor(String input)  {
        Client client = ClientBuilder.newClient();

        Form paramsForm = new Form();
        paramsForm.param("source", "en");
        paramsForm.param("target", "pl");
        paramsForm.param("key", API_KEY);
        paramsForm.param("q", input);

        WebTarget target = client.target("https://translation.googleapis.com/language/translate/v2");

        Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(paramsForm));
        String data = response.readEntity(String.class);
        response.close();

        return data;
    }


}


