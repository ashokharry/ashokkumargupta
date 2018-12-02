package com.pcs.ashokkumargupta.service.impl;

import com.pcs.ashokkumargupta.exception.TechnicalFailureException;
import com.pcs.ashokkumargupta.exception.TitleNotFoundException;
import com.pcs.ashokkumargupta.service.MovieService;
import com.pcs.ashokkumargupta.service.ParentalControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("parentalControlService")
public class ParentalControlServiceImpl implements ParentalControlService {

    final private MovieService movieService;

    public ParentalControlServiceImpl(MovieService movieService) {
        this.movieService = movieService;
    }

    public boolean isMovieViewableToCustomer(String movieId, String customerParentalControlLevelPreference) throws TechnicalFailureException, TitleNotFoundException {
        final String movieParentalControlLevel = getParentalControlLevel(movieId);
        return convertParentalControlLevelToNumeric(movieParentalControlLevel) <= convertParentalControlLevelToNumeric(customerParentalControlLevelPreference);
    }

    private String getParentalControlLevel(String movieId) throws TechnicalFailureException, TitleNotFoundException {
        try {
            String controlLevel = movieService.getParentalControlLevel(movieId);
            if (controlLevel != null) {
                return controlLevel;
            } else {
                throw new TitleNotFoundException("The movie\n" +
                        "service could not\n" +
                        "find the given\n" +
                        "movie");
            }
        } catch (TechnicalFailureException | TitleNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            //Loggers
            throw new TechnicalFailureException("The customer cannot\n" +
                    "\n" +
                    "watch this movie");
        }
    }

    private int convertParentalControlLevelToNumeric(String parentalControlLevel) throws TechnicalFailureException {
        switch (parentalControlLevel) {
            case "U":
                return 0;
            case "PG":
                return 1;
            case "12":
                return 2;
            case "15":
                return 3;
            case "18":
                return 4;
            default:
                throw new TechnicalFailureException("Unknown parental control level " + parentalControlLevel);
        }
    }
}
