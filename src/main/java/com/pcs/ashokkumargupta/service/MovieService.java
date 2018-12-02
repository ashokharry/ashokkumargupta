package com.pcs.ashokkumargupta.service;

import com.pcs.ashokkumargupta.exception.TechnicalFailureException;
import com.pcs.ashokkumargupta.exception.TitleNotFoundException;

public interface MovieService {
    String getParentalControlLevel(String movieId) throws TitleNotFoundException, TechnicalFailureException;

}
