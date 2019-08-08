server.port=9009
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
spring.datasource.url=jdbc:mysql://localhost:3306/${db_schemaname}
spring.datasource.username=${db_username}
spring.datasource.password=${db_password}