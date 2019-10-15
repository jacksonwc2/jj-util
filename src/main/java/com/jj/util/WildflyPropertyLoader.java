package com.jj.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class WildflyPropertyLoader {

    private static WildflyPropertyLoader wildflyPropertyLoader;

    private static Logger logger = Logger.getLogger(WildflyPropertyLoader.class.getName());

    static Connection con = null;
    static DataSource ds = null;

    public static WildflyPropertyLoader from(String jndiName) {

        if (wildflyPropertyLoader == null) {
            wildflyPropertyLoader = new WildflyPropertyLoader();
        }

        try {
            Context initialContext = new InitialContext();

            DataSource datasource = (DataSource) initialContext.lookup(jndiName);
            if (datasource != null) {
                con = datasource.getConnection();
            } else {
                logger.info("Falha ao recuperar o datasource");
            }
        } catch (NamingException ex) {
            logger.info("Cannot get connection: " + ex);
        } catch (SQLException ex) {
            logger.info("Não foi possível obter a conexão: " + ex);
        }

        return wildflyPropertyLoader;

    }

    public String getConnectionUrl() {

        try {
            return con.getMetaData().getURL();
        } catch (SQLException ex) {
            logger.info("Não foi possível recuperar a url de conexão: " + ex);
        }
        return "";

    }

}
