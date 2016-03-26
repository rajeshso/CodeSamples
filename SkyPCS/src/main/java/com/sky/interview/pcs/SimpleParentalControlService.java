package com.sky.interview.pcs;

import org.apache.commons.configuration.CompositeConfiguration;

/**
 * Sky is developing a next generation Video on Demand platform. The Candidate is part of
 * a software engineering team, developing services for the platform and working
 * on the given story .
 * 
 * SimpleParentalControlService is the implemention of the ParentalControlService API.
 * 
 * @author Rajesh
 *
 */
public class SimpleParentalControlService implements ParentalControlService {
	private CompositeConfiguration parentalControlDataRepository;
	private MovieService movieService;

	public SimpleParentalControlService(String parentalDataFileName, String movieMetaDataFileName)
			throws TechnicalFailureException {
		parentalControlDataRepository = InitializationUtil.getRepository(parentalDataFileName);
		movieService = new SimpleMovieService(movieMetaDataFileName);
	}

	/**
	 * Returns true if the movie can be watched for a given parental preference.
	 * @Throws Exceptions if the input is incorrect or the movie is not available
	 */
	@Override
	public boolean isViewPermitted(String parentalControlLevelPreference, String movieId)
			throws IncorrectInputException, TitleNotFoundException, TechnicalFailureException {
		int pcPrefLevel;
		int pcActualLevel;
		System.out.println(
				"parentalControlLevelPreference is " + parentalControlLevelPreference + " and movie is " + movieId);
		try {
			validateParentalControlPreference(parentalControlLevelPreference);
			String parentalControl = movieService.getParentalControlLevel(movieId);
			pcPrefLevel = parentalControlDataRepository.getInt(parentalControlLevelPreference);
			pcActualLevel = parentalControlDataRepository.getInt(parentalControl);
		} catch (IncorrectInputException | TitleNotFoundException | TechnicalFailureException e) {
			System.err.println(e.getMessage());
			throw e;
		} catch (Exception e) {
			TechnicalFailureException tfe = new TechnicalFailureException(
					"System error. Customer cannot watch this movie");
			tfe.initCause(e);
			throw tfe;
		}
		return pcPrefLevel >= pcActualLevel;
	}

	/**
	 * @param parentalControlLevelPreference
	 * @throws IncorrectInputException
	 */
	private void validateParentalControlPreference(String parentalControlLevelPreference)
			throws IncorrectInputException {
		if (parentalControlLevelPreference == null || parentalControlLevelPreference.trim().isEmpty()) {
			throw new IncorrectInputException("Parental ControlLevel Preference has to be specified");
		}
		if (!parentalControlDataRepository.containsKey(parentalControlLevelPreference)) {
			throw new IncorrectInputException(parentalControlLevelPreference + " is not supported");
		}
	}

}
