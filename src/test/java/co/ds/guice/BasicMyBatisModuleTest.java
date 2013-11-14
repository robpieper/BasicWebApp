package co.ds.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;

import static org.junit.Assert.assertNotNull;

public class BasicMyBatisModuleTest {

	@Test
	public void should_initialize_successfully() {
		final Injector injector = Guice.createInjector(new BasicMyBatisModule());
		Provider factoryProvider = injector.getProvider(JdbcTransactionFactory.class);
		assertNotNull("Provider for JdbcTransactionFactory should not be null", factoryProvider);
		JdbcTransactionFactory factory = (JdbcTransactionFactory) factoryProvider.get();
		assertNotNull("JdbcTransactionFactory should not be null", factory);
		final Provider datasourceProvider = injector.getProvider(PooledDataSourceProvider.class);
		assertNotNull("Provider for PooledDataSourceProvider should not be null", datasourceProvider);
		final PooledDataSourceProvider dataSourceProvider = (PooledDataSourceProvider) datasourceProvider.get();
		assertNotNull("PooledDataSourceProvider should not be null", dataSourceProvider);
	}

}
