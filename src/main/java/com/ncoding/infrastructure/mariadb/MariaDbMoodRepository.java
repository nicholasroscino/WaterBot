package com.ncoding.infrastructure.mariadb;

import com.ncoding.core.models.DayPhases;
import com.ncoding.core.ports.MoodRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MariaDbMoodRepository implements MoodRepository {

    private final DataSource dataSource;

    public MariaDbMoodRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<String> getMood(DayPhases dayPhases) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<String> strings = new ArrayList<>();
        try {
            conn = this.dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Mood WHERE dayPhase = ?");

            stmt.setString(1, dayPhases.toString());

            rs = stmt.executeQuery();

            while (rs.next()) {
                strings.add(rs.getString("phrase"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return strings;
    }
}
