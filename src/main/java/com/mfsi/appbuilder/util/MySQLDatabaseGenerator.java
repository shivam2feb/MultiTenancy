package com.mfsi.appbuilder.util;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import com.mfsi.appbuilder.dto.DBDetailsDTO;

 
public class MySQLDatabaseGenerator {
 
    public static void createSchemaMetatdata(DBDetailsDTO dbDetails) {
        Map<String, String> settings = new HashMap<>();
        settings.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        settings.put("hibernate.connection.url", dbDetails.getDbURL());
        settings.put("hibernate.connection.username", dbDetails.getDbUsername());
        settings.put("hibernate.connection.password", dbDetails.getDbPassword());
        settings.put("hibernate.hbm2ddl.auto", "update");
        settings.put("show_sql", "true");
 
        MetadataSources metadata = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySettings(settings)
                        .build());
        CustomUtil.getClasses("com.mfsi.appbuilder.tenant.entity").forEach(metadata::addAnnotatedClass);
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setHaltOnError(true);
        schemaExport.setFormat(true);
        schemaExport.setDelimiter(";");
        schemaExport.setOutputFile("db-schema.sql");
        schemaExport.createOnly(EnumSet.of(TargetType.DATABASE),metadata.buildMetadata() );
        
    }
}