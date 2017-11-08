package com.bolyartech.java.telosys;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;


public interface TestTableDbh {
    TestTable createNew(Connection dbc,

                        final String myText,
                        final String myString,
                        final boolean myBool,
                        final int myInt,
                        final int myLong,
                        final float myFloat,
                        final double myDouble,
                        final LocalDateTime tsDt) throws SQLException;


                                
    Optional<TestTable> update(Connection dbc,
                               final long id,
                               final String myText,
                               final String myString,
                               final boolean myBool,
                               final int myInt,
                               final int myLong,
                               final float myFloat,
                               final double myDouble,
                               final LocalDateTime tsDt) throws SQLException;
 
    Optional<TestTable> loadById(Connection dbc, final long id) throws SQLException;
    boolean delete(Connection dbc, long id) throws SQLException;
    int count(Connection dbc) throws SQLException;
}
