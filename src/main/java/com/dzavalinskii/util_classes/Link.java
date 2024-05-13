package com.dzavalinskii.util_classes;

import com.dzavalinskii.DBUtils;

import java.sql.*;

public class Link {
    private long linkTypeId;
    private long person1;
    private long person2;
    private final long boardId;
    public final long id;

    public long getLinkTypeId() {
        return linkTypeId;
    }

    public void setLinkTypeId(long linkTypeId) {
        this.linkTypeId = linkTypeId;
    }

    public long getPerson1() {
        return person1;
    }

    public void setPerson1(long person1) {
        this.person1 = person1;
    }

    public long getPerson2() {
        return person2;
    }

    public void setPerson2(long person2) {
        this.person2 = person2;
    }

    public Link(long linkTypeId, long person1, long person2, long boardId, long id) {
        this.linkTypeId = linkTypeId;
        this.person1 = person1;
        this.person2 = person2;
        this.boardId = boardId;
        this.id = id;

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String str = "Связь между ";
        try {
            connection = DriverManager.getConnection(DBUtils.jdbcURL, DBUtils.login, DBUtils.password);
            ps = connection.prepareStatement("SELECT * FROM persons WHERE id = ?");
            ps.setLong(1, person1);
            rs = ps.executeQuery();
            rs.next();
            str += rs.getString("name");
            ps = connection.prepareStatement("SELECT * FROM persons WHERE id = ?");
            ps.setLong(1, person2);
            rs = ps.executeQuery();
            rs.next();
            str += " и " + rs.getString("name") + " на доске ";
            ps = connection.prepareStatement("SELECT * FROM boards WHERE id = ?");
            ps.setLong(1, boardId);
            rs = ps.executeQuery();
            rs.next();
            str += rs.getString("name");
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
        this.str = str;
    }

    public String str;

    @Override
    public String toString() {
        return str;
    }
}
