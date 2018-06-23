package ws.client.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Translation {

    @JsonProperty("translatedText")
    private String translatedText;

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}
