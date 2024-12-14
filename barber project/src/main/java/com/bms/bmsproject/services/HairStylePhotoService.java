package com.bms.bmsproject.services;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.bms.bmsproject.models.HairStylePhoto;



import java.util.List;
import java.util.Map;

@Service
public class HairStylePhotoService {

    private static final String UNSPLASH_API_URL = "https://api.unsplash.com/search/photos?";
    private static final String UNSPLASH_API_KEY = "sAPJdj3WH4RZ94yfx749ktIsrNEbO1TRbRlSy44XYD8";
    private static final String HAIR_STYLE_QUERY = "hair+styles";

    @Autowired
    private RestTemplate restTemplate;

    public HairStylePhoto[] getHairStylePhotos() {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(UNSPLASH_API_URL + "client_id=" + UNSPLASH_API_KEY + "&query=" + HAIR_STYLE_QUERY, Map.class);
            List<Map<String, Object>> photos = (List<Map<String, Object>>) response.getBody().get("results");

            if (photos == null || photos.isEmpty()) {
                System.out.println("No photos found");
                return new HairStylePhoto[0];
            }

            return photos.stream()
                    .map(photo -> {
                        Map<String, Object> urls = (Map<String, Object>) photo.get("urls");
                        String photoUrl = (String) urls.get("regular");
                        String description = (String) photo.get("alt_description");
                        return new HairStylePhoto(photoUrl, description);
                    })
                    .toArray(HairStylePhoto[]::new);
        } catch (Exception e) {
            System.err.println("Error fetching hair style photos: " + e.getMessage());
            return new HairStylePhoto[0];
        }
    }
}




