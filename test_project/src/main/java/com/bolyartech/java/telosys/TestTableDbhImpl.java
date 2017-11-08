package com.bolyartech.java.telosys;

import com.bolyartech.forge.server.db.DbUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Optional;


public class TestTableDbhImpl implements TestTableDbh {
    private final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true)
            .toFormatter();

    private final static String SQL_SELECT =
            "select id, my_text, my_string, my_bool, my_int, my_long, my_float, my_double, ts_dt from test_table where id = ?";

    private final static String SQL_INSERT =
            "insert into test_table ( my_text, my_string, my_bool, my_int, my_long, my_float, my_double, ts_dt ) values ( ?, ?, ?, ?, ?, ?, ?, ? )";

    private final static String SQL_UPDATE =
            "update test_table set my_text = ?, my_string = ?, my_bool = ?, my_int = ?, my_long = ?, my_float = ?, my_double = ?, ts_dt = ? where id = ?";

    private final static String SQL_DELETE =
            "delete from test_table where id = ?";

    private final static String SQL_COUNT_ALL =
            "select count(*) from test_table";


    public TestTableDbhImpl() {
    }


    @Override
    public TestTable createNew(Connection dbc,

                               final String myText,
                               final String myString,
                               final boolean myBool,
                               final int myInt,
                               final int myLong,
                               final float myFloat,
                               final double myDouble,
                               final LocalDateTime tsDt) throws SQLException {

        DbUtils.ensureOperationalDbc(dbc);

        try (PreparedStatement psInsert = dbc.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            SetValueUtils.setValue(psInsert, i, myText);
            i++;
            SetValueUtils.setValue(psInsert, i, myString);
            i++;
            SetValueUtils.setValue(psInsert, i, myBool);
            i++;
            SetValueUtils.setValue(psInsert, i, myInt);
            i++;
            SetValueUtils.setValue(psInsert, i, myLong);
            i++;
            SetValueUtils.setValue(psInsert, i, myFloat);
            i++;
            SetValueUtils.setValue(psInsert, i, myDouble);
            i++;
            SetValueUtils.setValue(psInsert, i, tsDt.format(dateTimeFormatter));
            psInsert.executeUpdate();

            try (ResultSet rs = psInsert.getGeneratedKeys()) {
                rs.next();
                return new TestTableImpl(rs.getLong(1),

                        myText,
                        myString,
                        myBool,
                        myInt,
                        myLong,
                        myFloat,
                        myDouble,
                        tsDt);

            }
        }
    }


    @Override
    public Optional<TestTable> update(Connection dbc,
                                      final long id,
                                      final String myText,
                                      final String myString,
                                      final boolean myBool,
                                      final int myInt,
                                      final int myLong,
                                      final float myFloat,
                                      final double myDouble,
                                      final LocalDateTime tsDt) throws SQLException {


        DbUtils.ensureOperationalDbc(dbc);

        try (PreparedStatement psUpdate = dbc.prepareStatement(SQL_UPDATE)) {
            int i = 1;
            SetValueUtils.setValue(psUpdate, i, myText);
            i++;
            SetValueUtils.setValue(psUpdate, i, myString);
            i++;
            SetValueUtils.setValue(psUpdate, i, myBool);
            i++;
            SetValueUtils.setValue(psUpdate, i, myInt);
            i++;
            SetValueUtils.setValue(psUpdate, i, myLong);
            i++;
            SetValueUtils.setValue(psUpdate, i, myFloat);
            i++;
            SetValueUtils.setValue(psUpdate, i, myDouble);
            i++;
            SetValueUtils.setValue(psUpdate, i, tsDt.format(dateTimeFormatter));
            i++;
            psUpdate.setLong(i, id);
            if (psUpdate.executeUpdate() > 0) {
                return Optional.of(new TestTableImpl(id,
                        myText,
                        myString,
                        myBool,
                        myInt,
                        myLong,
                        myFloat,
                        myDouble,
                        tsDt));

            } else {
                return Optional.empty();
            }
        }
    }


    @Override
    public Optional<TestTable> loadById(Connection dbc, final long id) throws SQLException {
        DbUtils.ensureOperationalDbc(dbc);

        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }

        try (PreparedStatement psLoad = dbc.prepareStatement(SQL_SELECT)) {
            psLoad.setLong(1, id);

            try (ResultSet rs = psLoad.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new TestTableImpl(
                            rs.getLong("id"),
                            rs.getString("my_text"),
                            rs.getString("my_string"),
                            rs.getBoolean("my_bool"),
                            rs.getInt("my_int"),
                            rs.getInt("my_long"),
                            rs.getFloat("my_float"),
                            rs.getDouble("my_double"),
                            LocalDateTime.parse(rs.getString("ts_dt"), dateTimeFormatter)));

                } else {
                    return Optional.empty();
                }
            }
        }
    }


    @Override
    public boolean delete(Connection dbc, long id) throws SQLException {
        DbUtils.ensureOperationalDbc(dbc);
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }

        try (PreparedStatement psDelete = dbc.prepareStatement(SQL_DELETE)) {
            psDelete.setLong(1, id);
            return psDelete.executeUpdate() == 1;
        }
    }


    public int count(Connection dbc) throws SQLException {
        DbUtils.ensureOperationalDbc(dbc);

        try (PreparedStatement psCount = dbc.prepareStatement(SQL_COUNT_ALL)) {
            try (ResultSet rs = psCount.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return -1;
    }
}