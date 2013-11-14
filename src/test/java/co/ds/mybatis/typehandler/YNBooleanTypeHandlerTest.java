package co.ds.mybatis.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class YNBooleanTypeHandlerTest {

	private static final String TRUE_STRING = "Y";
	private static final String FALSE_STRING = "N";

	private YNBooleanTypeHandler typeHandler;

	@Before
	public void beforeYNBooleanTypeHandlerTest() {
		typeHandler = new YNBooleanTypeHandler();
	}

	@Test
	public void should_set_parameter_true() throws SQLException {
		PreparedStatement ps = mock(PreparedStatement.class);
		typeHandler.setParameter(ps, 1, true, JdbcType.BOOLEAN);
		typeHandler.setParameter(ps, 1, Boolean.TRUE, JdbcType.BOOLEAN);

		verify(ps, times(2)).setString(1, TRUE_STRING);
	}

	@Test
	public void should_set_parameter_false() throws SQLException {
		PreparedStatement ps = mock(PreparedStatement.class);
		typeHandler.setParameter(ps, 1, false, JdbcType.BOOLEAN);
		typeHandler.setParameter(ps, 1, Boolean.FALSE, JdbcType.BOOLEAN);

		verify(ps, times(2)).setString(1, FALSE_STRING);
	}

	@Test
	public void should_set_parameter_null() throws SQLException {
		PreparedStatement ps = mock(PreparedStatement.class);
		typeHandler.setParameter(ps, 1, null, JdbcType.BOOLEAN);

		verify(ps).setNull(1, Types.BOOLEAN);
	}

	@Test
	public void should_get_result_set_true() throws SQLException {
		ResultSet rs = mock(ResultSet.class);
		when(rs.getString("column")).thenReturn("Y");

		assertTrue("result should be of boolean type TRUE", typeHandler.getResult(rs, "column"));
	}

	@Test
	public void should_get_result_set_false() throws SQLException {
		ResultSet rs = mock(ResultSet.class);
		when(rs.getString("column")).thenReturn("N");

		assertFalse("result should be of boolean type FALSE", typeHandler.getResult(rs, "column"));
	}

	@Test
	public void should_get_result_set_null() throws SQLException {
		ResultSet rs = mock(ResultSet.class);
		when(rs.getString("column")).thenReturn(null);

		assertNull("result should be null", typeHandler.getResult(rs, "column"));
	}

	@Test(expected = SQLException.class)
	public void should_fail_get_result_set() throws SQLException {
		ResultSet rs = mock(ResultSet.class);
		when(rs.getString("column")).thenReturn("String");
		typeHandler.getResult(rs, "column");
	}

	@Test
	public void should_get_result_set_true_with_index() throws SQLException {
		ResultSet rs = mock(ResultSet.class);
		when(rs.getString(1)).thenReturn("Y");

		assertTrue("result should be of boolean type TRUE", typeHandler.getResult(rs, 1));
	}

	@Test
	public void should_get_result_set_false_with_index() throws SQLException {
		ResultSet rs = mock(ResultSet.class);
		when(rs.getString(1)).thenReturn("N");

		assertFalse("result should be of boolean type FALSE", typeHandler.getResult(rs, 1));
	}

	@Test
	public void should_get_result_set_null_with_index() throws SQLException {
		ResultSet rs = mock(ResultSet.class);
		when(rs.getString(1)).thenReturn(null);

		assertNull("result should be null", typeHandler.getResult(rs, 1));
	}

	@Test(expected = SQLException.class)
	public void should_fail_get_result_set_with_index() throws SQLException {
		ResultSet rs = mock(ResultSet.class);
		when(rs.getString(1)).thenReturn("String");
		typeHandler.getResult(rs, 1);
	}

	@Test
	public void should_get_result_callable_statement_true() throws SQLException {
		CallableStatement cs = mock(CallableStatement.class);
		when(cs.getString(1)).thenReturn("Y");

		assertTrue("result should be of boolean type TRUE", typeHandler.getResult(cs, 1));
	}

	@Test
	public void should_get_result_callable_statement_false() throws SQLException {
		CallableStatement cs = mock(CallableStatement.class);
		when(cs.getString(1)).thenReturn("N");

		assertFalse("result should be of boolean type FALSE", typeHandler.getResult(cs, 1));
	}

	@Test
	public void should_get_result_callable_statement_null() throws SQLException {
		CallableStatement cs = mock(CallableStatement.class);
		when(cs.getString(1)).thenReturn(null);

		assertNull("result should be null", typeHandler.getResult(cs, 1));
	}

	@Test(expected = SQLException.class)
	public void should_fail_get_result_callable_statement() throws SQLException {
		CallableStatement cs = mock(CallableStatement.class);
		when(cs.getString(1)).thenReturn("String");
		typeHandler.getResult(cs, 1);
	}

	@Test
	public void should_trim_and_upcase() {
		assertEquals("result should be \"\"", "", typeHandler.trimAndUpcase("   "));
		assertEquals("result should be \"\"", "", typeHandler.trimAndUpcase(""));
		assertEquals("result should be null", null, typeHandler.trimAndUpcase(null));
		assertEquals("result should be \"Y\"", "Y", typeHandler.trimAndUpcase(" y   "));
		assertEquals("result should be \"N\"", "N", typeHandler.trimAndUpcase(" n   "));
		assertEquals("result should be \"YES\"", "YES", typeHandler.trimAndUpcase(" yes   "));
		assertEquals("result should be \"NO\"", "NO", typeHandler.trimAndUpcase(" no   "));
	}

}