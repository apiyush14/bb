1. Import this spring boot project in STS (Spring Tool Suite) or spring boot supported IDE as maven project.
2. Make sure you have pre-installed MySql and Android Studio.(If not get it installed)
3. Make following changes in project's application.properties located under src/main/resources
   spring.jpa.hibernate.ddl-auto=update									// For auto creation of your tables in MySql if not already created.
   spring.profiles.active=default  										// default - enable logging, Prod - Disable logging
   server.port=7001                										// Listening port of your application for Rest calls
   spring.datasource.url=jdbc:mysql://localhost:3306/fitlers_db			// 3306 is port where mySql is hosted, and fitlers_db is the database, to get your port run this query on mySql- SHOW VARIABLES WHERE Variable_name = 'port';
   spring.datasource.username=root										// mySql username
   spring.datasource.password=rootroot									// mySql password
   whitelistedPaths=auth,swagger..										// add your URI here in order to execute without security token for testing purpose
   file.upload-dir=/Users/souravchandan/fitlersapp/images/				// Change according to your system path
   logging.file.name=/Users/souravchandan/fitlersapp/fitlers_server.log // Change according to your system path
4. Update all file paths in logback-spring.xml located under src/main/resources according to your system path.