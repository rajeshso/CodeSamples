package com.sky.interview.pcs;

public interface ParentalControlService {
	public boolean isViewPermitted(String parentalControlLevelPreference, String movieId)
			throws TitleNotFoundException, TechnicalFailureException, IncorrectInputException;
}
