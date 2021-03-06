package com.mycompany.fant;

import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import static com.mycompany.fant.DatasourceProducer.JNDI_NAME;


/**
 *
 * @author mikael
 */
@Singleton
@DataSourceDefinition(
    name = JNDI_NAME,
    className = "org.postgresql.ds.PGSimpleDataSource",
    serverName      = "${MPCONFIG=dataSource.serverName}",
    portNumber      = 5433,
    databaseName    = "${MPCONFIG=dataSource.databaseName}",
    user            = "${MPCONFIG=dataSource.user}",
    password        = "${MPCONFIG=dataSource.password}",
    minPoolSize     = 10,
    maxPoolSize     = 50)
public class DatasourceProducer {
    public static final String JNDI_NAME =  "java:app/jdbc/postgres";

    @Resource(lookup=JNDI_NAME)
    DataSource ds;

    @Produces
    public DataSource getDatasource() {
        return ds;
    }
}
