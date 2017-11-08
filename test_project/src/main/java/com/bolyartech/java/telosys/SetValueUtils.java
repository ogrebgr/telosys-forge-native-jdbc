package com.bolyartech.java.telosys;


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SetValueUtils {
    public static void setValue(PreparedStatement ps, int position, boolean value) throws SQLException {
        ps.setBoolean(position, value);
    }


    public static void setValue(PreparedStatement ps, int position, String value) throws SQLException {
        checkParameters(ps, position);
        ps.setString(position, value);
    }


    public static void setValue(PreparedStatement ps, int position, java.util.Date value) throws SQLException {
        checkParameters(ps, position);

        if (value != null) {
            ps.setDate(position,  new java.sql.Date(value.getTime())); // Convert util.Date to sql.Date
        } else {
            ps.setNull(position,  java.sql.Types.DATE);
        }
    }


    public static void setValue(PreparedStatement ps, int position, java.sql.Date value) throws SQLException {
        checkParameters(ps, position);

        ps.setDate(position,  value);
    }


    public static void setValue(PreparedStatement ps, int position, java.sql.Time value) throws SQLException {
        checkParameters(ps, position);

        ps.setTime(position,  value);
    }


    public static void setValue(PreparedStatement ps, int position, java.sql.Timestamp value) throws SQLException {
        checkParameters(ps, position);

        ps.setTimestamp(position,  value);
    }


    public static void setValue(PreparedStatement ps, int position, Byte value) throws SQLException {
        checkParameters(ps, position);

        if (value != null) {
            ps.setByte(position,  value);
        } else {
            ps.setNull(position,  java.sql.Types.TINYINT); // JDBC : "TINYINT" => getByte/setByte
        }
    }


    public static void setValue(PreparedStatement ps, int position, Short value) throws SQLException {
        checkParameters(ps, position);

        if (value != null) {
            ps.setShort(position,  value);
        } else {
            ps.setNull(position,  java.sql.Types.SMALLINT);
        }
    }


    public static void setValue(PreparedStatement ps, int position, Integer value) throws SQLException {
        checkParameters(ps, position);

        if (value != null) {
            ps.setInt(position,  value);
        } else {
            ps.setNull(position,  java.sql.Types.INTEGER);
        }
    }


    public static void setValue(PreparedStatement ps, int position, Long value) throws SQLException {
        checkParameters(ps, position);

        if (value != null) {
            ps.setLong(position,  value);
        } else {
            ps.setNull(position,  java.sql.Types.BIGINT); // JDBC : "BIGINT" => getLong/setLong
        }
    }


    public static void setValue(PreparedStatement ps, int position, Float value) throws SQLException {
        checkParameters(ps, position);

        if (value != null) {
            ps.setFloat(position,  value);
        } else {
            ps.setNull(position,  java.sql.Types.FLOAT);
        }
    }


    public static void setValue(PreparedStatement ps, int position, Double value) throws SQLException {
        checkParameters(ps, position);

        if (value != null) {
            ps.setDouble(position,  value);
        } else {
            ps.setNull(position,  java.sql.Types.DOUBLE);
        }
    }


    public static void setValue(PreparedStatement ps, int position, BigDecimal value) throws SQLException {
        checkParameters(ps, position);

        ps.setBigDecimal(position,  value);
    }


    public static void setValue(PreparedStatement ps, int position, byte[] value) throws SQLException {
        checkParameters(ps, position);

        ps.setBytes(position,  value);
    }


    private static void checkParameters(PreparedStatement ps, int position) {
        if (ps == null) {
            throw new IllegalArgumentException("ps is null");
        }

        if (position <= 0) {
            throw new IllegalArgumentException("invalid position = " + position);
        }
    }


    private SetValueUtils() {
        throw new AssertionError("Non-instantiable utility class");
    }
}