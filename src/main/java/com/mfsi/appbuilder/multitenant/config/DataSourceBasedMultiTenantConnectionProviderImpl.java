package com.mfsi.appbuilder.multitenant.config;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.mfsi.appbuilder.master.document.Project;
import com.mfsi.appbuilder.master.repository.ProjectRepository;
import com.mfsi.appbuilder.util.DataSourceUtil;

@Configuration
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7037060288412099387L;

	@Autowired
	private ProjectRepository projectRepository;

	private Map<String, DataSource> dataSourcesMtApp = new TreeMap<>();

	@Override
	public DataSource selectAnyDataSource() {
		if (dataSourcesMtApp.isEmpty()) {
			List<Project> projects=projectRepository.findAll();

			for (Project project : projects) {
				dataSourcesMtApp.put(project.getProjectName(),
						DataSourceUtil.createAndConfigureDataSource(project));
			}
		}
		if(this.dataSourcesMtApp.size()>0)
			return this.dataSourcesMtApp.values().iterator().next();
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		
		return builder.setType(EmbeddedDatabaseType.DERBY).build();
	}

	@Override
	public DataSource selectDataSource(String tenantIdentifier) {

		if (!this.dataSourcesMtApp.containsKey(tenantIdentifier)) {
			List<Project> projects = projectRepository.findAll();
			for (Project project : projects) {
				if(!dataSourcesMtApp.containsKey(project.getProjectName()))
					dataSourcesMtApp.put(project.getProjectName(),
							DataSourceUtil.createAndConfigureDataSource(project));
			}
		}
		return this.dataSourcesMtApp.get(tenantIdentifier);
	}

}