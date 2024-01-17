package software.ulpgc.moneycalculator.fixerws;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import software.ulpgc.moneycalculator.ExchangeRateLoader;
import software.ulpgc.moneycalculator.model.Currency;
import software.ulpgc.moneycalculator.model.ExchangeRate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FixerExchangeRateLoader implements ExchangeRateLoader {
    @Override
    public ExchangeRate load(Currency from, Currency to) {
        return new ExchangeRate(from, to, LocalDate.now(), rateOf(from, to));
    }

    private double rateOf(Currency from, Currency to) {
        return 0;
    }



    private String loadJson(Currency from) throws IOException {
        URL url = new URL("https://open.er-api.com/v6/latest?symbols=AWG&base=" + from);
        try (InputStream is = url.openStream()){
            return new String(is.readAllBytes());
        }
    }

}
