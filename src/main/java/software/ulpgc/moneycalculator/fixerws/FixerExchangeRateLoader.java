package software.ulpgc.moneycalculator.fixerws;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import software.ulpgc.moneycalculator.ExchangeRateLoader;
import software.ulpgc.moneycalculator.model.Currency;
import software.ulpgc.moneycalculator.model.ExchangeRate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;

public class FixerExchangeRateLoader implements ExchangeRateLoader {
    @Override
    public ExchangeRate load(Currency from, Currency to) {
        try {
            System.out.println(rateOf(from, to));
            return new ExchangeRate(from, to, LocalDate.now(), rateOf(from, to));
        } catch (IOException e) {
            return new ExchangeRate(from, to, LocalDate.now(), 1.2312);
        }
    }

    private double rateOf(Currency from, Currency to) throws IOException {
        String json = loadJson(from, to);
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        return toDouble(jsonObject);
    }

    private double toDouble(JsonObject jsonObject) {
        return jsonObject.get("conversion_rate").getAsDouble();
    }


    private String loadJson(Currency from, Currency to) throws IOException {
        URL url = new URL("https://v6.exchangerate-api.com/v6" + FixerApi.exchangeKey + "/pair/"
        + from.code() + "/" + to.code());
        try (InputStream is = url.openStream()){
            return new String(is.readAllBytes());
        }
    }

}
