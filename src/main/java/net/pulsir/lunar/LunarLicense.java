package net.pulsir.lunar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LunarLicense {

    private final String licenseKey;

    public LunarLicense(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public boolean validateLicense() throws Exception {
        String endPoint = "https://api.licensegate.io/license/a1dd3/" + this.licenseKey + "/verify";

        StringBuilder builder = new StringBuilder();
        URL url = new URL(endPoint);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                builder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return builder.toString().contains("true");
    }
}
