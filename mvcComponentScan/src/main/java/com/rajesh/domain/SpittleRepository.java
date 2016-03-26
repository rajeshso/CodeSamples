package com.rajesh.domain;

import java.util.List;

public interface SpittleRepository {
	List<Spittle> findSpittles(long maxId, int count);

	Spittle findOneSpittle(long spittleID);
}
