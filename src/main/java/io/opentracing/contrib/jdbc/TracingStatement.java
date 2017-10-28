/*
 * Copyright 2017 The OpenTracing Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.opentracing.contrib.jdbc;


import static io.opentracing.contrib.jdbc.JdbcTracingUtils.buildSpan;

import io.opentracing.ActiveSpan;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

public class TracingStatement implements Statement {

  private final Statement statement;
  private final String query;
  private final ArrayList<String> batchCommands = new ArrayList<>();
  private final String dbType;
  private final String dbUser;
  private final boolean withActiveSpanOnly;

  TracingStatement(Statement statement, String dbType, String dbUser, boolean withActiveSpanOnly) {
    this(statement, null, dbType, dbUser, withActiveSpanOnly);
  }

  TracingStatement(Statement statement, String query, String dbType, String dbUser,
      boolean withActiveSpanOnly) {
    this.statement = statement;
    this.query = query;
    this.dbType = dbType;
    this.dbUser = dbUser;
    this.withActiveSpanOnly = withActiveSpanOnly;
  }

  @Override
  public ResultSet executeQuery(String sql) throws SQLException {
    try (ActiveSpan ignored = buildSpan("Query", sql, dbType, dbUser, withActiveSpanOnly)) {
      return statement.executeQuery(sql);
    }
  }

  @Override
  public int executeUpdate(String sql) throws SQLException {
    try (ActiveSpan ignored = buildSpan("Update", sql, dbType, dbUser, withActiveSpanOnly)) {
      return statement.executeUpdate(sql);
    }
  }

  @Override
  public void close() throws SQLException {
    statement.close();
  }

  @Override
  public int getMaxFieldSize() throws SQLException {
    return statement.getMaxFieldSize();
  }

  @Override
  public void setMaxFieldSize(int max) throws SQLException {
    statement.setMaxFieldSize(max);
  }

  @Override
  public int getMaxRows() throws SQLException {
    return statement.getMaxRows();
  }

  @Override
  public void setMaxRows(int max) throws SQLException {
    statement.setMaxRows(max);
  }

  @Override
  public void setEscapeProcessing(boolean enable) throws SQLException {
    statement.setEscapeProcessing(enable);
  }

  @Override
  public int getQueryTimeout() throws SQLException {
    return statement.getQueryTimeout();
  }

  @Override
  public void setQueryTimeout(int seconds) throws SQLException {
    statement.setQueryTimeout(seconds);
  }

  @Override
  public void cancel() throws SQLException {
    statement.cancel();
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return statement.getWarnings();
  }

  @Override
  public void clearWarnings() throws SQLException {
    statement.clearWarnings();
  }

  @Override
  public void setCursorName(String name) throws SQLException {
    statement.setCursorName(name);
  }

  @Override
  public boolean execute(String sql) throws SQLException {
    try (ActiveSpan ignored = buildSpan("Execute", sql, dbType, dbUser, withActiveSpanOnly)) {
      return statement.execute(sql);
    }
  }

  @Override
  public ResultSet getResultSet() throws SQLException {
    return statement.getResultSet();
  }

  @Override
  public int getUpdateCount() throws SQLException {
    return statement.getUpdateCount();
  }

  @Override
  public boolean getMoreResults() throws SQLException {
    return statement.getMoreResults();
  }

  @Override
  public void setFetchDirection(int direction) throws SQLException {
    statement.setFetchDirection(direction);
  }

  @Override
  public int getFetchDirection() throws SQLException {
    return statement.getFetchDirection();
  }

  @Override
  public void setFetchSize(int rows) throws SQLException {
    statement.setFetchSize(rows);
  }

  @Override
  public int getFetchSize() throws SQLException {
    return statement.getFetchSize();
  }

  @Override
  public int getResultSetConcurrency() throws SQLException {
    return statement.getResultSetConcurrency();
  }

  @Override
  public int getResultSetType() throws SQLException {
    return statement.getResultSetType();
  }

  @Override
  public void addBatch(String sql) throws SQLException {
    statement.addBatch(sql);
    batchCommands.add(sql);
  }

  @Override
  public void clearBatch() throws SQLException {
    statement.clearBatch();
    batchCommands.clear();
  }

  @Override
  public int[] executeBatch() throws SQLException {
    try (ActiveSpan ignored = buildSpanForBatch()) {
      return statement.executeBatch();
    }
  }

  @Override
  public Connection getConnection() throws SQLException {
    return statement.getConnection();
  }

  @Override
  public boolean getMoreResults(int current) throws SQLException {
    return statement.getMoreResults(current);
  }

  @Override
  public ResultSet getGeneratedKeys() throws SQLException {
    return statement.getGeneratedKeys();
  }

  @Override
  public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    try (ActiveSpan ignored = buildSpan("Update", sql, dbType, dbUser, withActiveSpanOnly)) {
      return statement.executeUpdate(sql, autoGeneratedKeys);
    }
  }

  @Override
  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    try (ActiveSpan ignored = buildSpan("Update", sql, dbType, dbUser, withActiveSpanOnly)) {
      return statement.executeUpdate(sql, columnIndexes);
    }
  }

  @Override
  public int executeUpdate(String sql, String[] columnNames) throws SQLException {
    try (ActiveSpan ignored = buildSpan("Update", sql, dbType, dbUser, withActiveSpanOnly)) {
      return statement.executeUpdate(sql, columnNames);
    }
  }

  @Override
  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    try (ActiveSpan ignored = buildSpan("Execute", sql, dbType, dbUser, withActiveSpanOnly)) {
      return statement.execute(sql, autoGeneratedKeys);
    }
  }

  @Override
  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    try (ActiveSpan ignored = buildSpan("Execute", sql, dbType, dbUser, withActiveSpanOnly)) {
      return statement.execute(sql, columnIndexes);
    }
  }

  @Override
  public boolean execute(String sql, String[] columnNames) throws SQLException {
    try (ActiveSpan ignored = buildSpan("Execute", sql, dbType, dbUser, withActiveSpanOnly)) {
      return statement.execute(sql, columnNames);
    }
  }

  @Override
  public int getResultSetHoldability() throws SQLException {
    return statement.getResultSetHoldability();
  }

  @Override
  public boolean isClosed() throws SQLException {
    return statement.isClosed();
  }

  @Override
  public void setPoolable(boolean poolable) throws SQLException {
    statement.setPoolable(poolable);
  }

  @Override
  public boolean isPoolable() throws SQLException {
    return statement.isPoolable();
  }

  @Override
  public void closeOnCompletion() throws SQLException {
    statement.closeOnCompletion();
  }

  @Override
  public boolean isCloseOnCompletion() throws SQLException {
    return statement.isCloseOnCompletion();
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return statement.unwrap(iface);
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return statement.isWrapperFor(iface);
  }

  private ActiveSpan buildSpanForBatch() {
    StringBuilder sqlBuilder = new StringBuilder();
    if (query != null) {
      sqlBuilder.append(query);
    }

    for (String batchCommand : batchCommands) {
      sqlBuilder.append(batchCommand);
    }

    return buildSpan("Batch", sqlBuilder.toString(), dbType, dbUser, withActiveSpanOnly);
  }
}
