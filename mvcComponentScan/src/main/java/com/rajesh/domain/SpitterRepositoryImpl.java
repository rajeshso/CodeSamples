package com.rajesh.domain;

public class SpitterRepositoryImpl implements SpitterRepository {

	@Override
	public Spitter save(Spitter spitter) {
		return spitter;
	}

	@Override
	public Spitter findByUsername(String username) {
		return new Spitter("Raj","Soma", "rajsoma", "rajesh");
	}

}
