package com.baiyi.watch.dao;

import android.content.ContentValues;

import com.baiyi.watch.model.Person;

import java.util.List;

public interface PersonDaoInface {
	
	public boolean addPerson(Person person);

	public boolean deletePerson(String whereClause, String[] whereArgs);

	public boolean updatePerson(ContentValues values, String whereClause, String[] whereArgs);

	public Person viewPerson(String selection, String[] selectionArgs);

	public List<Person> listPerson(String selection, String[] selectionArgs);

	public void clearPersonTable();
}
