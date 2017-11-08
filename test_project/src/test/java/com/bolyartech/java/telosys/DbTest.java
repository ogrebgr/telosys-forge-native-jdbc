package com.bolyartech.java.telosys;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class DbTest {
    private static final String[] INSERTS = {
            "INSERT INTO `telosys_test`.`test_table` (`my_text`, `my_string`, `my_bool`, `my_int`, `my_long`, `my_float`, `my_double`, `ts_dt`, `ts_ts`) VALUES ('my text1', 'my string1', 1, 1, 1, 0.25, 0.25, '2017-10-10 10:10:10', '2017-10-10 10:10:10');",
            "INSERT INTO `telosys_test`.`test_table` (`my_text`, `my_string`, `my_bool`, `my_int`, `my_long`, `my_float`, `my_double`, `ts_dt`, `ts_ts`) VALUES ('my text2', 'my string2', 0, 2, 2, 0.26, 0.25, '2017-10-10 10:10:10', '2017-10-10 10:10:10');",
            "INSERT INTO `telosys_test`.`test_table` (`my_text`, `my_string`, `my_bool`, `my_int`, `my_long`, `my_float`, `my_double`, `ts_dt`, `ts_ts`) VALUES ('my text3', 'my string3', 1, 3, 3, 0.27, 0.25, '2017-10-10 10:10:10', '2017-10-10 10:10:10');"
    };

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private Connection dbc;

    

    @Before
    public void setup() {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "telosys_test");
        connectionProps.put("password", "telosys_test");

        try {
            dbc = DriverManager.getConnection("jdbc:mysql://localhost/telosys_test", connectionProps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test1() {
//        for(int i = 0; i < INSERTS.length; i++) {
//            Statement st = null;
//            try {
//                st = dbc.createStatement();
//                st.execute(INSERTS[i]);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }

        LocalDateTime ldt = LocalDateTime.of(2019, 1, 1, 1, 1);
        TestTableDbh ttDbh = new TestTableDbhImpl();
        try {
            TestTable tt = ttDbh.createNew(dbc, "presni", "chudesni", true, 123, 1234, 12.12f, 21.21, ldt);
            assertTrue("invalid text", tt.getMyText().equals("presni"));
            assertTrue("invalid string", tt.getMyString().equals("chudesni"));
            assertTrue("invalid bool", tt.getMyBool());

            assertTrue("invalid int", tt.getMyInt() == 123);
            assertTrue("invalid long", tt.getMyLong() == 1234);
            assertTrue("invalid float", (tt.getMyFloat() - 12.12f) < 0.00001);
            assertTrue("invalid double", (tt.getMyDouble() - 21.21) < 0.00001);
            assertTrue("invalid datetime", tt.getTsDt().equals(ldt));

            Optional<TestTable> tto = ttDbh.loadById(dbc, tt.getId());
            assertTrue("not found", tto.isPresent());

            tt = tto.get();
            assertTrue("invalid text", tt.getMyText().equals("presni"));
            assertTrue("invalid string", tt.getMyString().equals("chudesni"));
            assertTrue("invalid bool", tt.getMyBool());
            assertTrue("invalid int", tt.getMyInt() == 123);
            assertTrue("invalid long", tt.getMyLong() == 1234);
            assertTrue("invalid float", (tt.getMyFloat() - 12.12f) < 0.00001);
            assertTrue("invalid double", (tt.getMyDouble() - 21.21) < 0.00001);
            assertTrue("invalid datetime", tt.getTsDt().equals(ldt));

            ldt = LocalDateTime.of(2019, 2, 1, 1, 1);
            ttDbh.update(dbc, tt.getId(), "presni2", "chudesni2", false, 1234, 12345, 12.123f, 21.2121, ldt);

            tto = ttDbh.loadById(dbc, tt.getId());
            tt = tto.get();
            assertTrue("invalid text", tt.getMyText().equals("presni2"));
            assertTrue("invalid string", tt.getMyString().equals("chudesni2"));
            assertTrue("invalid bool", !tt.getMyBool());
            assertTrue("invalid int", tt.getMyInt() == 1234);
            assertTrue("invalid long", tt.getMyLong() == 12345);
            assertTrue("invalid float", (tt.getMyFloat() - 12.123f) < 0.00001);
            assertTrue("invalid double", (tt.getMyDouble() - 21.2121) < 0.00001);
            assertTrue("invalid datetime", tt.getTsDt().equals(ldt));

            int count = ttDbh.count(dbc);
            assertTrue("wrong count", count == 1);

            ttDbh.delete(dbc, tt.getId());
            tto = ttDbh.loadById(dbc, tt.getId());
            assertFalse("not deleted", tto.isPresent());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @After
    public void teardown() {
        try {
            String deleteSql = "delete from test_table";
            Statement st = dbc.createStatement();
            st.execute(deleteSql);

            dbc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
