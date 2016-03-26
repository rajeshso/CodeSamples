package com.rajesh.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpittleRepositoryImpl implements SpittleRepository {

	@Override
	public List<Spittle> findSpittles(long maxId, int count) {
		List<Spittle> spittleList = new ArrayList<Spittle>();
		for (int i=0; i<count; i++) {
			spittleList.add(new Spittle(Double.toString(Math.random()), new Date()));
		}
		return spittleList;
	}

	@Override
	public Spittle findOneSpittle(long maxID) {
		return new Spittle(Long.toString(maxID), new Date());
	}
}
