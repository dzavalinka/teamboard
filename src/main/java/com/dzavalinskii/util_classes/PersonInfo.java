package com.dzavalinskii.util_classes;

import com.dzavalinskii.DBUtils;

import java.sql.*;

public class PersonInfo {
    private final long id;
    private long personId;
    private long boardId;
    private long x;
    private long y;

    private String boardPerson;

    public long getId() {
        return id;
    }
    public long getPersonId() {
        return personId;
    }

    public long getBoardId() {
        return boardId;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public PersonInfo(long id, long personId, long boardId, long x, long y) {
        this.id = id;
        this.personId = personId;
        this.boardId = boardId;
        this.x = x;
        this.y = y;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String str = "";
        try {
            connection = DriverManager.getConnection(DBUtils.jdbcURL, DBUtils.login, DBUtils.password);
            ps = connection.prepareStatement("SELECT name FROM persons WHERE id = ?");
            ps.setDouble(1, personId);
            rs = ps.executeQuery();
            rs.next();
            str += "Персона " + rs.getString("name") + " на доске ";
            ps = connection.prepareStatement("SELECT name FROM boards WHERE id = ?");
            ps.setDouble(1, boardId);
            rs = ps.executeQuery();
            rs.next();
            str += rs.getString("name");
            this.boardPerson = str;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        return boardPerson;
    }
}
