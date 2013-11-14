package co.ds.guice;

import co.ds.mybatis.typehandler.YNBooleanTypeHandler;
import com.google.inject.name.Names;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;

import java.util.HashMap;

public class BasicMyBatisModule extends MyBatisModule {

	@Override
	protected void initialize() {

		Names.bindProperties(binder(), new HashMap<String, String>() {
			{
				/* JDBC and MyBatis */
				put("JDBC.url", "jdbc:hsqldb:basicdb");
				put("JDBC.username", "SA");
				put("JDBC.password", "");
				put("JDBC.driver", "org.hsqldb.jdbc.JDBCDriver");
				put("mybatis.environment.id", "ds");
				// JDBC.autoCommit -- boolean
				// JDBC.loginTimeout -- int
				// JDBC.driverProperties -- java.util.Properties
				// mybatis.pooled.maximumActiveConnections -- int
				// mybatis.pooled.maximumCheckoutTime -- int
				// mybatis.pooled.maximumIdleConnections -- int
				// mybatis.pooled.pingConnectionsNotUsedFor -- int
				// mybatis.pooled.pingEnabled -- boolean
				// mybatis.pooled.pingQuery -- String
				// mybatis.pooled.timeToWait -- int
			}
		});
		bindTransactionFactoryType(JdbcTransactionFactory.class);
		bindDataSourceProviderType(PooledDataSourceProvider.class);
		addMapperClasses("co.ds.mybatis.mapper");
		addSimpleAliases("co.ds.bean");
		handleType(Boolean.class).with(YNBooleanTypeHandler.class);
		handleType(boolean.class).with(YNBooleanTypeHandler.class);
	}

}
