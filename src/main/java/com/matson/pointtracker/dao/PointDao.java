package com.matson.pointtracker.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Singleton
public class PointDao 
{
    public final NamedParameterJdbcTemplate jt;

    @Inject
    public PointDao(DataSource ds) {
        this.jt = new NamedParameterJdbcTemplate(ds);
    }
    
    public List<Map<String,Object>> getPoints()
    {
        String sql = "SELECT * FROM dbo.points";
        return jt.queryForList(sql, Collections.EMPTY_MAP);
    }
    
}
