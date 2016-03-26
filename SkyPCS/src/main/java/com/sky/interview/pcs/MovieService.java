package com.sky.interview.pcs;

/**
 * The Movie Meta Data team implements the MovieService
 * getParentalControlLevel call that accepts the movie id as an input and
 * returns the parental control level for that movie as a string.
 * 
 * @author Rajesh
 *
 */
public interface MovieService {
	public String getParentalControlLevel(String movieId) throws TitleNotFoundException, TechnicalFailureException;
}
