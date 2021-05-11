package yoonho.demo.reactive.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import yoonho.demo.reactive.config.repository.CustomerReadingConverter;
import yoonho.demo.reactive.config.repository.CustomerWritingConverter;
import yoonho.demo.reactive.config.repository.UserReadingConverter;
import yoonho.demo.reactive.config.repository.UserrWritingConverter;

@Configuration
@EnableTransactionManagement
public class PersistanceConfig extends AbstractR2dbcConfiguration{
	@Value("${spring.r2dbc.url}")
	private String database_url;
	
	@Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        return initializer;
    }
	
	@Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
	
	
	@Override
    protected List<Object> getCustomConverters() {
        List<Object> converterList = new ArrayList<>();
        converterList.add(new CustomerReadingConverter());
        converterList.add(new CustomerWritingConverter());
        converterList.add(new UserReadingConverter());
        converterList.add(new UserrWritingConverter());
        return converterList;
    }
	
	
	@Override
	public ConnectionFactory connectionFactory() {
		return ConnectionFactories.get(database_url);
	}
}
