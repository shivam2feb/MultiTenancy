package com.mfsi.appbuilder.util;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mfsi.appbuilder.dto.DBDetailsDTO;
import com.mfsi.appbuilder.master.document.Project;
import com.zaxxer.hikari.HikariDataSource;

public final class DataSourceUtil {

    private static final Logger LOG = LoggerFactory
            .getLogger(DataSourceUtil.class);

    public static DataSource createAndConfigureDataSource(
            Project project) {
        HikariDataSource ds = new HikariDataSource();
        DBDetailsDTO dbDetails = project.getDbDetailsDTO();
        ds.setUsername(dbDetails.getDbUsername());
        ds.setPassword(dbDetails.getDbPassword());
        ds.setJdbcUrl(dbDetails.getDbURL());
        ds.setDriverClassName("com.mysql.jdbc.Driver");

        ds.setConnectionTimeout(20000);

        ds.setMinimumIdle(10);
        ds.setMaximumPoolSize(20);
        ds.setIdleTimeout(300000);
        ds.setConnectionTimeout(20000);

        String tenantId = project.getProjectName();
        String tenantConnectionPoolName = tenantId + "-connection-pool";
        ds.setPoolName(tenantConnectionPoolName);
        LOG.info("Configured datasource: {} . Connection poolname:{}" , project.getProjectName(), tenantConnectionPoolName);
        return ds;
    }
}
