package com.sky.interview.pcs;

import org.apache.commons.configuration.CompositeConfiguration;
/**
 * A simple implementation of the Movie Service
 * 
 * @author Rajesh
 *
 */
public class SimpleMovieService implements MovieService {

	private CompositeConfiguration movieDataRepository;

	SimpleMovieService(String movieMetaDataFileName) throws TechnicalFailureException {
		movieDataRepository = InitializationUtil.getRepository(movieMetaDataFileName);
		validateMetaDataRepository();
	}
	
	/**
	 * Returns the Parental level for the movie.
	 * @throws Exception if the title is not available
	 */
	@Override
	public String getParentalControlLevel(String movieId) throws TitleNotFoundException, TechnicalFailureException {
		validateInputMovieID(movieId);
		String key = movieId.replace(' ', '/');
		String parentalLevel = movieDataRepository.getString(key);
		if (parentalLevel == null) {
			throw new TitleNotFoundException(movieId + " not available");
		}
		return parentalLevel;
	}

	/**
	 * @throws TechnicalFailureException
	 */
	private void validateMetaDataRepository() throws TechnicalFailureException {
		if (movieDataRepository == null || movieDataRepository.isEmpty()) {
			throw new TechnicalFailureException("Movie Meta Data configuration is incorrect");
		}
	}

	/**
	 * @param movieId
	 * @throws TitleNotFoundException
	 */
	private void validateInputMovieID(String movieId) throws TitleNotFoundException {
		if (movieId == null || movieId.trim().isEmpty()) {
			throw new TitleNotFoundException("Movie is incorrectly specified");
		}
	}

}
