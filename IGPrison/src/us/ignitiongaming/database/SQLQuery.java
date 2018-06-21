package us.ignitiongaming.database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
/**
 * Create an acceptable SQLQuery dynamically.
 */
public class SQLQuery {
	
	//Required -- Constructor
	private QueryType type;
	private String table;
	//Built-in -- Query accessibility
	private List<String> grabColumns = new ArrayList<>();
	private List<String> whereColumns = new ArrayList<>();
	private List<Object> whereValues = new ArrayList<>();
	private List<CombinationType> whereTypes = new ArrayList<>();
	private List<Object> valueItems = new ArrayList<>();
	private List<String> setColumns = new ArrayList<>();
	private List<Object> setValues = new ArrayList<>();
	private String rightTable = "";
	private String joinColumn = "";
	//Used privately only.
	private String query = "";
	
	/**
	 * Create an acceptable SQLQuery dynamically.
	 * @param type - Type of query to run
	 * @param table - Table of Database to reference
	 */
	public SQLQuery(QueryType type, String table) {
		this.type = type;
		this.table = table;
	}
	
	/* Addition Methods */
	/**
	 * This is specifically for SELECT statements.
	 * @param column
	 */
	public void addGrabColumn(String column) { grabColumns.add(column); }
	public void addGrabColumns(String... columns) { for (String column : columns) grabColumns.add(column); }
	
	/**
	 * Add a specific condition column
	 * @param column
	 * @param condition
	 */
	public void addWhere(String column, Object condition) { addWhere(column, condition, CombinationType.AND); }
	public void addWheres(String[] columns, Object[] conditions) { 
		for (int i = 0; i < columns.length; i++) { 
			addWhere(columns[i], conditions[i], CombinationType.AND);
		}
	}
	public void addWhere(String column, Object condition, CombinationType type) {
		whereColumns.add(column); 
		whereValues.add(condition);
		whereTypes.add(type);
	}
	/**
	 * This should work for only VALUES clause.
	 * @param column
	 * @param value
	 */
	public void addValue(Object value) { valueItems.add(value); }
	public void addValues(Object... values) { for (Object value : values) valueItems.add(value); }
	/**
	 * This should work for only SET clause.
	 * @param column
	 * @param value
	 */
	public void addSet(String column, Object value) { setColumns.add(column); setValues.add(value); }
	public void addJoinTable(String tableName) { rightTable = tableName; }
	public void addJoinColumn(String column) { joinColumn = column; }
	
	/* Handy Methods */
	/**
	 * Returns the generated query in current state.
	 * @return
	 */
	public String getQuery() {
		generate();
		return query;
	}
	public QueryType getType() {
		return type;
	}
	/**
	 * Execute the query using getSafeQuery(). Return result if query type is SELECT. Also closes connection afterwards.
	 * @return
	 */
	public void execute() {
		generate();
		if (type == QueryType.SELECT || type == QueryType.INNER_JOIN || type == QueryType.JOIN) {
			getResults();
		} else {
			hasSuccess();
		}
	}
	/**
	 * Determine if the query ran. This is meant for anything BUT a SELECT or JOIN.
	 * @return
	 */
	public boolean hasSuccess() {
		try {
			generate();
			if ( type == QueryType.SELECT || type == QueryType.INNER_JOIN || type == QueryType.JOIN ) return false;
			return Database.GetStatement().execute(getQuery());
	    } catch (Exception ex) {
			return false;
		}
	}
	/**
	 * Return the results if SELECT, otherwise return null.
	 * @return
	 */	
	public ResultSet getResults() {
		generate();
		try {
			return Database.GetStatement().executeQuery(getQuery());
		} catch (Exception ex) {
			return null;
		}
	}
	
	public void generateQuery() {
		generate();
	}
	
	/* Important Private Functions */
	private void generate() {
		//Make sure these are filled in.
		if (table.equalsIgnoreCase("")) return;
		if (type == null) return;
		
		//Reset query always.
		query = "";
		
		//Determine the type of query based on the type.
		if (type == QueryType.SELECT) {
			//Part 1: Add the SELECT.
			query += "SELECT ";
			//Part 2: Organized the columns the query will grab.
			if (grabColumns.size() == 0) query += "* "; else {
				query += "(";
				for (int i = 0; i < grabColumns.size(); i++) {
					query += grabColumns.get(i) + (ConvertUtils.isLastIndex(i, grabColumns.size()) ? "" : " ");
				}
				query += ") ";
			}
			//Part 3: Add the table.
			query += "FROM " + table;
			//Part 4: Add the WHERE clause if able.
			if (whereColumns.size() != whereValues.size()) return;
			if (whereColumns.size() > 0) {
				query += " WHERE ";
				for (int i = 0; i < whereColumns.size(); i++) {
					query += whereColumns.get(i) + (whereValues.get(i) != null ? "='" + whereValues.get(i) + "'" : " IS NULL");
					if (!ConvertUtils.isLastIndex(i, whereColumns.size())) {
						query += (whereTypes.get(i) == CombinationType.AND ? " AND " : " OR ");
					}
				}
			}
			//Done.			
		} else if (type == QueryType.UPDATE) {
			//Part 1: Add the UPDATE and table
			query += "UPDATE " + table + " SET ";
			//Part 2: Add the SET columns and values
			for (int i = 0; i < setColumns.size(); i++) {
				query += setColumns.get(i) + "='" + setValues.get(i) + "'";
				if (!ConvertUtils.isLastIndex(i, setColumns.size())) {
					query += ", ";
				}
			}
			//Part 3: Add the WHERE. Make sure we're adding a condition else...
			if (whereColumns.size() == 0 || whereColumns.size() != whereValues.size()) return;
			query += " WHERE ";
			for (int i = 0; i < whereColumns.size(); i++) {
				query += whereColumns.get(i) + (whereValues.get(i) != null ? "='" + whereValues.get(i) + "'" : " IS NULL");
				if (!ConvertUtils.isLastIndex(i, whereColumns.size())) {
					query += (whereTypes.get(i) == CombinationType.AND ? " AND " : " OR ");
				}
			}
		} else if (type == QueryType.INSERT) {
			//Part 1: Add the INSERT
			query += "INSERT INTO " + table;
			//Part 2: Get the specific columns to grab.
			if (grabColumns.size() > 0) {
				query += " (";
				for (int i = 0; i < grabColumns.size(); i++) {
					query += grabColumns.get(i) + (ConvertUtils.isLastIndex(i, grabColumns.size()) ? "" : ", ");
				}
				query += ") ";
			}
			//Part 3: Get the values.
			if (valueItems.size() == 0) return;
			query += "VALUES (";
			for (int i = 0; i < valueItems.size(); i++) {
				query += "'" + valueItems.get(i) + "'";
				if (!ConvertUtils.isLastIndex(i, valueItems.size())) {
					query += ",";
				}
			}
			query += ")";
		//INNER JOINS AND STUFF
		} else if (type == QueryType.INNER_JOIN || type == QueryType.JOIN) {
			query += "SELECT FROM " + table + " JOIN " + rightTable + " ON " + table + "." + joinColumn + "=" + rightTable + "." + joinColumn;
		} else {
			//Part 1: Add the DELETE
			query += "DELETE FROM " + table;
			//Part 2: Add the WHERE
			if (whereColumns.size() == 0 || whereColumns.size() != whereValues.size()) return;
			query += " WHERE ";
			for (int i = 0; i < whereColumns.size(); i++) {
				query += whereColumns.get(i) + (whereValues.get(i) != null ? "='" + whereValues.get(i) + "'" : " IS NULL");
				if (!ConvertUtils.isLastIndex(i, whereColumns.size())) {
					query += (whereTypes.get(i) == CombinationType.AND ? " AND " : " OR ");
				}
			}
		}		
	}
	
	/**
	 * Add "where ID = ID" to the query. 
	 * @param ID
	 */
	public void addID(int ID) {
		addWhere("ID", ID);
	}
	
	public void logQuery() {
		generateQuery();
		Bukkit.getLogger().log(Level.INFO, "Query: " + query);
	}
	
	public void broadcastQuery() {
		generateQuery();
		Bukkit.broadcastMessage(query);
	}

	public enum CombinationType {
		AND, OR;
	}
}
