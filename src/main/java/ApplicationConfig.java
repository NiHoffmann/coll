import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:persistence.properties")
//@ComponentScan("org.hbrs.se2.project.repository")
@EnableTransactionManagement
public class ApplicationConfig {

    @Autowired
    private Environment env;

    public ApplicationConfig() {
        super();
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName( env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl( env.getProperty("jdbc.url"));
        dataSource.setUsername( env.getProperty("jdbc.user"));
        dataSource.setPassword( env.getProperty("jdbc.pass"));

        return dataSource;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();

        return hibernateProperties;
    }
}