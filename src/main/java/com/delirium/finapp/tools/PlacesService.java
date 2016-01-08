package com.delirium.finapp.tools;

import com.delirium.finapp.finance.domain.Location;
import com.delirium.finapp.finance.domain.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.GooglePlacesInterface;
import se.walkercrou.places.Place;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by style on 03/01/2016.
 */
@Service
public class PlacesService {

    private static final double RADIUS = 50;
    @Autowired
    private LocationRepository locationRepository;
    @Value("${finapp.places.key}")
    private String key;

//    private GooglePlaces client;

    public PlacesService() {
    }

//    public PlacesService(String key) {
//        this.key = key;
//        init();
//    }

//    @PostConstruct
//    public void init() {
//        client = new GooglePlaces(key);
//    }

    public List<Location> getNearbyPlaces(double lat, double lng) {
        return locationRepository.findNearBy(lat,lng);
//        List<Place> places = client.getNearbyPlaces(lat, lng, RADIUS, GooglePlacesInterface.MAXIMUM_PAGE_RESULTS);
//        List<Location> locations = new LinkedList<>();
//        for (Place place : places) {
//            Location location = new Location(place);
//            locations.add(location);
//        }
//        return locations;
    }

    public List<Location> getPlacesByQuery(String q) {
        return locationRepository.queryLocations(q);
//        return client.getPlacesByQuery(q, GooglePlacesInterface.MAXIMUM_PAGE_RESULTS);
    }

    public void saveNearByLocations(Double latitude, Double longitude) {
        List<Location> locations = getNearbyPlaces(latitude, longitude);
        for (Location location : locations) {
            if (null == locationRepository.findByGoogleId(location.getGoogleId())) {
                locationRepository.save(location);
            }
        }
        locationRepository.flush();
    }
}