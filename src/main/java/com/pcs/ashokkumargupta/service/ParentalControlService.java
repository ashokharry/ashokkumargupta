package com.pcs.ashokkumargupta.service;

import com.pcs.ashokkumargupta.exception.TechnicalFailureException;
import com.pcs.ashokkumargupta.exception.TitleNotFoundException;

public interface ParentalControlService {
    boolean isMovieViewableToCustomer(String movieId, String parentalControlLevelPreference) throws TitleNotFoundException, TechnicalFailureException;
}
