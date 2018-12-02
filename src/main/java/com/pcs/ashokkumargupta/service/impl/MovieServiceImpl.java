package com.pcs.ashokkumargupta.service.impl;

import com.pcs.ashokkumargupta.exception.TechnicalFailureException;
import com.pcs.ashokkumargupta.exception.TitleNotFoundException;
import com.pcs.ashokkumargupta.service.MovieService;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service("movieService")
public class MovieServiceImpl implements MovieService {

    private static Map<Integer, String> moviePCLevelMap;

    static {
        moviePCLevelMap = populateDummymoviePCLevelMap();
    }

    //This method will return random Parental Control Level for any movieId
    @Override
    public String getParentalControlLevel(String movieId)
            throws TechnicalFailureException, TitleNotFoundException {

        int randomIndex = new Random().nextInt(moviePCLevelMap.size());

        try {
            if (!moviePCLevelMap.containsKey(randomIndex)) {
                throw new TitleNotFoundException("There is an error for selected movie");
            }
        } catch (TitleNotFoundException e) {
            throw new TitleNotFoundException("There is an error for selected movie");
        } catch (Exception e) {
            throw new TechnicalFailureException("There is some techical issue");
        }

        return moviePCLevelMap.get(randomIndex);
    }

    private static Map<Integer, String> populateDummymoviePCLevelMap() {

        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "U");
        map.put(2, "PG");
        map.put(3, "12");
        map.put(4, "15");
        map.put(5, "18");
        return map;
    }

}
