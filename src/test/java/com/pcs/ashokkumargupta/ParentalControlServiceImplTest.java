package com.pcs.ashokkumargupta;

import com.pcs.ashokkumargupta.exception.TechnicalFailureException;
import com.pcs.ashokkumargupta.exception.TitleNotFoundException;
import com.pcs.ashokkumargupta.service.MovieService;
import com.pcs.ashokkumargupta.service.ParentalControlService;
import com.pcs.ashokkumargupta.service.impl.ParentalControlServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ParentalControlServiceImplTest {

    @Mock
    private MovieService movieService;

    private ParentalControlService underTest;

    @Before
    public void Setup() {
        movieService = mock(MovieService.class);
        underTest = new ParentalControlServiceImpl(movieService);
    }

    @Test
    public void sameLevelShouldReturnTrue() throws Exception{
        when(movieService.getParentalControlLevel("1")).thenReturn("PG");
        assertTrue(underTest.isMovieViewableToCustomer("1", "PG"));
    }

    @Test
    public void higherMovieShouldReturnFalse() throws Exception{
        when(movieService.getParentalControlLevel("1")).thenReturn("PG");
        assertFalse(underTest.isMovieViewableToCustomer("1", "U"));
    }

    @Test
    public void lowerMovieShouldReturnTrue() throws Exception{
        when(movieService.getParentalControlLevel("1")).thenReturn("15");
        assertTrue(underTest.isMovieViewableToCustomer("1", "18"));
    }

    @Test(expected = TechnicalFailureException.class)
    public void movieLevelShouldBeCaseSensitive() throws Exception{
        when(movieService.getParentalControlLevel("1")).thenReturn("15");
        underTest.isMovieViewableToCustomer("1", "u");
    }

    @Test(expected = TechnicalFailureException.class)
    public void invalidLevelShouldThrowTechnicalFailureException() throws Exception{
        when(movieService.getParentalControlLevel("1")).thenReturn("PG");
        underTest.isMovieViewableToCustomer("1", "fish");
    }

    @Test(expected = TitleNotFoundException.class)
    public void unknownTitleShouldThrowTitleNotFoundException() throws Exception{
        when(movieService.getParentalControlLevel(anyString())).thenThrow(TitleNotFoundException.class);
        underTest.isMovieViewableToCustomer("2", "PG");
    }

    @Test(expected = TechnicalFailureException.class)
    public void movieServiceThrowsRuntimeException() throws Exception{
        when(movieService.getParentalControlLevel(anyString())).thenThrow(RuntimeException.class);
        underTest.isMovieViewableToCustomer("2", "PG");
    }

    @Test(expected = TechnicalFailureException.class)
    public void invalidReturnFromMovieServiceShouldThrowTechnicalFault() throws Exception{
        when(movieService.getParentalControlLevel("1")).thenReturn("fish");
        underTest.isMovieViewableToCustomer("1", "PG");
    }

    @Test(expected = TitleNotFoundException.class)
    public void nullReturnFromMovieServiceShouldThrowTechnicalFault() throws Exception{
        when(movieService.getParentalControlLevel("1")).thenReturn(null);
        underTest.isMovieViewableToCustomer("1", "PG");
    }
}
