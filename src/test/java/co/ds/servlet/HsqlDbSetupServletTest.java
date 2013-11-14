package co.ds.servlet;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class HsqlDbSetupServletTest {

	@Mock
	private Connection connection;
	@Mock
	private Statement statement;
	@Mock
	private SqlSessionManager sqlSessionManager;
	@Mock
	private SqlSession sqlSession;

	@Before
	public void before() {
		initMocks(this);
	}

	@Test
	public void should_init_in_constructor() throws SQLException, IOException {
		when(sqlSessionManager.openSession()).thenReturn(sqlSession);
		when(sqlSession.getConnection()).thenReturn(connection);
		when(connection.createStatement()).thenReturn(statement);
		final HsqlDbSetupServlet servlet = new HsqlDbSetupServlet(sqlSessionManager);
	}

}
