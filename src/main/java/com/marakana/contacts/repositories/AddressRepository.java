package com.marakana.contacts.repositories;

import com.marakana.contacts.entities.Address;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by SERGE on 31.08.2014.
 */
public class AddressRepository {

    private final DataSource dataSource;

    public AddressRepository() {
        try {
            Context context = new InitialContext();
            try {
                dataSource = (DataSource) context.lookup("java:comp/env/jdbc/trainingdb");
            } finally {
                context.close();
            }

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }

    public void init() throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            try {
                statement.execute("id integer generated by default as identity primary key, street varchar(255), city varchar(255), state varchar(255), zip varchar(255))");
            } finally {
                statement.close();
            }
        } finally {
            connection.close();
        }
    }

    public Address find(long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            try {
                ResultSet result = statement.executeQuery("select * from address where id = " + id);
                try {
                    if(!result.next()){
                        return null;
                    } else {
                        return unmarshal(result);
                    }
                } finally {
                    result.close();
                }

            } finally {
                statement.close();
            }
        } finally {
            connection.close();
        }

    }

    public void create(Address address) throws SQLException {

    }

    public void delete(Address address) throws SQLException {

    }

    private static Address unmarshal(ResultSet results) throws SQLException {
        Address address =  new Address();
        address.setId(results.getLong("id"));
        address.setStreet(results.getString("street"));
        address.setCity(results.getString("city"));
        address.setState(results.getString("state"));
        address.setZip(results.getString("zip"));

        return address;
    }


}
