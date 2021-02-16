package com.epam.esm.model.util;

import java.util.HashMap;
import java.util.Map;

public class SqlJpqlUtil {
    public static final Map<String,String> SQL_MAP;

    static {
        SQL_MAP = new HashMap<>();
        SQL_MAP.put("price", "g.price");
        SQL_MAP.put("name", "g.name");
        SQL_MAP.put("description", "g.description");
        SQL_MAP.put("create_date", "g.createDate");
        SQL_MAP.put("last_update_date", "g.lastUpdateDate");
        SQL_MAP.put("duration", "g.duration");
    }

}
