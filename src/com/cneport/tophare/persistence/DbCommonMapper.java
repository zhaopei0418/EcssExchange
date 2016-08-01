package com.cneport.tophare.persistence;

import java.util.Date;

public interface DbCommonMapper {
	Integer getSequenceNextval(String sequenceName);

	Date getSysdateFromDB();
}
