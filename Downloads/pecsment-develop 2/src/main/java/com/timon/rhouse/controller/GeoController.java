package com.timon.rhouse.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/geo")
public class GeoController {

    @RequestMapping(path = "/geocode", method = RequestMethod.GET )
    public String getGeocode(@RequestParam String address) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String encodedAddress = URLEncoder.encode(address, "UTF-8");
        Request request = new Request.Builder()
                .url("https://google-maps-geocoding.p.rapidapi.com/geocode/json?language=en&address=" + encodedAddress)
                .get()
                .addHeader("x-rapidapi-host", "google-maps-geocoding.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "635d298425mshfaa1da580828997p161c02jsnf0dcce3d9dfa")
                .build();
        ResponseBody responseBody = client.newCall(request).execute().body();
        return responseBody.string();
    }
}
