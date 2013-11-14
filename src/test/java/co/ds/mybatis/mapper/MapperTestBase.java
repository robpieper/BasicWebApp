package co.ds.mybatis.mapper;

import co.ds.guice.BasicMyBatisModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;

public class MapperTestBase {

	private static final Logger log = LoggerFactory.getLogger(MapperTestBase.class);

	protected static Injector injector;

	@BeforeClass
	public static void beforeMapperTestBaseClass() throws IOException {
		if (injector == null) {
			injector = Guice.createInjector(
					new BasicMyBatisModule()
			);
			buildDatabase(injector);
		}
	}

	private static void buildDatabase(final Injector injector) throws IOException {
		final SqlSessionManager sessionManager = injector.getInstance(SqlSessionManager.class);
		final SqlSession sqlSession = sessionManager.openSession();
		try {
			final ScriptRunner scriptRunner = new ScriptRunner(sqlSession.getConnection());
			buildSchema(scriptRunner);
			populateData(scriptRunner);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}

	private static void populateData(final ScriptRunner scriptRunner) throws IOException {
		Reader dataloadReader = null;
		try {
			dataloadReader = Resources.getResourceAsReader("co/ds/database/hsqldb-dataload.sql");
			scriptRunner.runScript(dataloadReader);
		} catch (IOException e) {
			log.error(e.toString(), e);
			throw e;
		} finally {
			IOUtils.closeQuietly(dataloadReader);
		}
	}

	private static void buildSchema(final ScriptRunner scriptRunner) throws IOException {
		Reader schemaReader = null;
		try {
			schemaReader = Resources.getResourceAsReader("co/ds/database/hsqldb-schema.sql");
			scriptRunner.runScript(schemaReader);
		} catch (IOException e) {
			log.error(e.toString(), e);
			throw e;
		} finally {
			IOUtils.closeQuietly(schemaReader);
		}
	}

	// CONSTRUCTOR
	public MapperTestBase() {
		injector.injectMembers(this);
	}

	// PER TEST

	@Inject
	protected SqlSessionManager sqlSessionManager;

	@Before
	public void beforeMapperTestBase() {
		// open session with auto-commit set to false
		sqlSessionManager.startManagedSession();
	}

	@After
	public void afterMapperTestBase() {
		// rollback the transaction
		sqlSessionManager.rollback();
		// close the session
		sqlSessionManager.close();
	}
}