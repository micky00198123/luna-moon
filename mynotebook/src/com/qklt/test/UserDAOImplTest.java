package com.qklt.test;

import com.qklt.util.C3P0Utils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDAOImplTest {

    @Test
    public void test() throws SQLException {
        Connection connection = C3P0Utils.getConnection();
        System.out.println(connection);
    }

}