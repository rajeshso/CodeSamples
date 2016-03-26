package com.rajesh.domain;

public interface SpitterRepository {
	Spitter save(Spitter spitter);

	Spitter findByUsername(String username);
}
