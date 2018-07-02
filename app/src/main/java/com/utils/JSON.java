package com.utils;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSON {

    public static JSONObject parseJson(String json) throws Exception {
        return new JSONObject(json);
    }

    public static String CursorToJSONString(String[] tablename, Cursor[] AllRecords) throws Exception {
        String json;

        JSONObject table = new JSONObject();

        for (int k = 0; k < AllRecords.length; k++)      /// Iterating Single Table
        {
            Cursor records = AllRecords[k];

            String[] cols = records.getColumnNames();
            int colCount = records.getColumnCount();

            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < records.getCount(); i++)    /// Iterating each Record from Single Table
            {
                records.moveToNext();

                JSONObject recordsData = new JSONObject();
                for (int j = 0; j < colCount; j++)          /// Iterating each column from single record
                {
                    recordsData.put(cols[j], records.getString(j));
                }
                jsonArray.put(recordsData);
            }
            table.put(tablename[k], jsonArray);
        }
        json = table.toString();
        return json;
    }
}