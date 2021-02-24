package com.firstpackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DBQuery {

    //query a full sql command
    public static ArrayList<HashMap<String,Object>> SQLtoArray(Connection con, String sql) {
        try {

            //create statement
            Statement stm;
            stm = con.createStatement();

            //query
            ResultSet result;
            boolean returningRows = stm.execute(sql);
            if (returningRows)
                result = stm.getResultSet();
            else
                return new ArrayList<>();

            //get metadata
            ResultSetMetaData meta;
            meta = result.getMetaData();

            //get column names
            int colCount = meta.getColumnCount();
            ArrayList<String> cols = new ArrayList<>();
            for (int index=1; index<=colCount; index++)
                cols.add(meta.getColumnName(index));

            //fetch out rows
            ArrayList<HashMap<String,Object>> rows = new ArrayList<>();

            while (result.next()) {
                HashMap<String,Object> row = new HashMap<>();
                for (String colName:cols) {
                    Object val = result.getObject(colName);
                    row.put(colName,val);
                }
                rows.add(row);
            }

            //close statement
            stm.close();

            //pass back rows
            return rows;
        }
        catch (Exception ex) {
            System.out.print(ex.getMessage());
            return new ArrayList<>();
        }
    }//raw_query

}
