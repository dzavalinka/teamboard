package com.dzavalinskii;

import com.dzavalinskii.util_classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class DBUtils {

    static String jdbcURL = "jdbc:h2:./collectivedb";
    static String login = "sa";
    static String password = "";

    /**
     * Метод для добавления нового коллектива в БД
     * @param name - имя нового коллектива
     * @param description - описание нового коллектива
     * @return id нового коллектива
     */
    public static int addCollective(String name, String description) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rsCollision = null;
        PreparedStatement psInsert = null;
        PreparedStatement psId = null;
        ResultSet rsId = null;
        int id = 0;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM collectives WHERE name = ?");
            psCheckNameCollision.setString(1, name);
            rsCollision = psCheckNameCollision.executeQuery();

            if (rsCollision.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Коллектив с таким названием уже существует.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO collectives (name, description) VALUES( ?, ?)");
                psInsert.setString(1, name);
                psInsert.setString(2, description);
                psInsert.executeUpdate();
                psId = connection.prepareStatement("SELECT id FROM collectives WHERE name = ?");
                psId.setString(1,name);
                rsId = psId.executeQuery();
                rsId.next();
                id = rsId.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rsCollision != null) {
                try {
                    rsCollision.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckNameCollision != null) {
                try {
                    psCheckNameCollision.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
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
        return id;
    }

    /**
     * Метод для обновления данных коллектива.
     * @param oldName - старое имя коллектива
     * @param name - обновленное имя коллектива
     * @param description - обновленное описание коллектива
     */
    public static void updateCollective( String oldName, String name, String description) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psUpdate = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM collectives WHERE name = ?");
            psCheckNameCollision.setString(1, name);
            rs = psCheckNameCollision.executeQuery();

            if (oldName.compareTo(name)!=0 && rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Коллектив с таким названием уже существует.");
                alert.show();
            } else {
                psUpdate = connection.prepareStatement("UPDATE collectives SET name = ?, description = ? WHERE name = ?");
                psUpdate.setString(1, name);
                psUpdate.setString(2, description);
                psUpdate.setString(3, oldName);
                psUpdate.executeUpdate();
            }

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
            if (psCheckNameCollision != null) {
                try {
                    psCheckNameCollision.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psUpdate != null) {
                try {
                    psUpdate.close();
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

    /**
     * Метод для удаления коллектива и всех его данных из базы данных.
     * @param id - идентификатор удаляемого коллектива
     */
    public static void deleteCollective(int id) {
        Connection connection = null;
        PreparedStatement psDelete = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psDelete = connection.prepareStatement("SELECT id FROM boards WHERE collectiveId = ?");
            psDelete.setInt(1, id);
            rs = psDelete.executeQuery();
            while (rs.next()) {
                deleteBoard(rs.getInt("id"));
            }
            psDelete = connection.prepareStatement("SELECT id FROM persons WHERE collectiveId = ?");
            psDelete.setInt(1, id);
            rs = psDelete.executeQuery();
            while (rs.next()) {
                deletePerson(rs.getInt("id"));
            }
            psDelete = connection.prepareStatement("SELECT id FROM linkTypes WHERE collectiveId = ?");
            psDelete.setInt(1, id);
            rs = psDelete.executeQuery();
            while (rs.next()) {
                deleteLinkType(rs.getInt("id"));
            }
            psDelete = connection.prepareStatement("SELECT id FROM tags WHERE collectiveId = ?");
            psDelete.setInt(1, id);
            rs = psDelete.executeQuery();
            while (rs.next()) {
                deleteTag(rs.getInt("id"));
            }
            psDelete = connection.prepareStatement("DELETE FROM collectives WHERE id = ?");
            psDelete.setInt(1, id);
            psDelete.executeUpdate();
        } catch (SQLException e) {
            System.out.println("1");
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psDelete != null) {
                try {
                    psDelete.close();
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

    /**
     * Метод для добавления новой доски в БД.
     * @param collectiveId - id коллектива, к которому относится доска
     * @param name - имя доски
     * @param description - описание доски
     * @param timestamp - временная отметка доски
     */
    public static void addBoard( int collectiveId, String name, String description, Date timestamp) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psInsert = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM boards " +
                    "WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, collectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Доска с таким названием уже существует в этом коллективе.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO boards (collectiveId, name, description, timestamp)" +
                        " VALUES( ?, ?, ?, ?)");
                psInsert.setInt(1, collectiveId);
                psInsert.setString(2, name);
                psInsert.setString(3, description);
                psInsert.setTimestamp(4, new Timestamp(timestamp.getTime()));
                psInsert.executeUpdate();
            }
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
            if (psCheckNameCollision != null) {
                try {
                    psCheckNameCollision.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
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

    /**
     * Метод для обновления данных доски
     * @param name - новое название доски
     * @param description - новое описание доски
     * @param timestamp - новая временная отметка доски
     * @param id идентификатор
     */
    public static void updateBoard(String name, String description, long timestamp, int id) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psUpdate = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM boards WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, Main.currentCollectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.next() && rs.getString("name").compareTo(name) == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Доска с таким названием уже существует в этом коллективе.");
                alert.show();
            } else {
                psUpdate = connection.prepareStatement("UPDATE boards SET name = ?, description = ?, timestamp = ? " +
                        "WHERE id = ?");
                psUpdate.setString(1, name);
                psUpdate.setString(2, description);
                psUpdate.setLong(3, timestamp);
                psUpdate.setInt(4, id);
                psUpdate.executeUpdate();
            }

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
            if (psCheckNameCollision != null) {
                try {
                    psCheckNameCollision.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psUpdate != null) {
                try {
                    psUpdate.close();
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

    /**
     * Метод для удаления доски и всего с ней связанного из коллектива
     * @param id - идентификатор доски
     */
    public static void deleteBoard(int id) {
        Connection connection = null;
        PreparedStatement psDelete = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);

            psDelete = connection.prepareStatement("SELECT id FROM links WHERE boardId = ?");
            psDelete.setInt(1, id);
            rs = psDelete.executeQuery();
            while (rs.next()) {
                deleteLink(rs.getInt("id"));
            }

            psDelete = connection.prepareStatement("SELECT id FROM personInfo WHERE boardId = ?");
            psDelete.setInt(1, id);
            rs = psDelete.executeQuery();
            while (rs.next()) {
                deletePersonInfo(rs.getInt("id"));
            }

            psDelete = connection.prepareStatement("DELETE FROM boards WHERE id = ?");
            psDelete.setInt(1, id);
            psDelete.executeUpdate();
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
            if (psDelete != null) {
                try {
                    psDelete.close();
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

    /**
     * Метод для добавления новой персоны в коллектив
     * @param name - имя персоны
     * @param description - описание персоны
     * @param collectiveId - id коллектива, к которому относится персона
     */
    public static void addPerson( String name, String description, int collectiveId) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psInsert = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM persons " +
                    "WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, collectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Персона с таким именем уже существует в этом коллективе.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO persons (name, description, collectiveId)" +
                        " VALUES( ?, ?, ?)");
                psInsert.setString(1, name);
                psInsert.setString(2, description);
                psInsert.setInt(3, collectiveId);
                psInsert.executeUpdate();
            }

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
            if (psCheckNameCollision != null) {
                try {
                    psCheckNameCollision.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
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

    /**
     * Метод для обновления данных персоны
     * @param name - новое имя персоны
     * @param description - новое описание персоны
     * @param id - идентификатор
     */
    public static void updatePerson( String name, String description, int id) {
        Connection connection1 = null;
        Connection connection2 = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psUpdate = null;

        try {
            connection1 = DriverManager.getConnection(jdbcURL, login, password);
            psCheckNameCollision = connection1.prepareStatement("SELECT * FROM persons " +
                    "WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, Main.currentCollectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.next() && rs.getString("name").compareTo(name) == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Персона с таким именем уже существует в этом коллективе.");
                alert.show();
            } else {
                connection2 = DriverManager.getConnection(jdbcURL, login, password);
                psUpdate = connection2.prepareStatement("UPDATE persons SET name = ?, description = ?, collectiveId = ? " +
                        "WHERE id = ?");
                psUpdate.setString(1, name);
                psUpdate.setString(2, description);
                psUpdate.setInt(3, Main.currentCollectiveId);
                psUpdate.setInt(4, id);
                psUpdate.executeUpdate();

            }

            //TODO image
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
            if (psCheckNameCollision != null) {
                try {
                    psCheckNameCollision.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psUpdate != null) {
                try {
                    psUpdate.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection1 != null) {
                try {
                    connection1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection2 != null) {
                try {
                    connection2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void softDeletePerson(int id) {
        Connection connection = null;
        PreparedStatement psDelete = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psDelete = connection.prepareStatement("DELETE FROM persons WHERE id = ?");
            psDelete.setInt(1, id);
            psDelete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        if (psDelete != null) {
            try {
                psDelete.close();
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

    /**
     * Метод для удаления персоны с доски
     * @param id - идентификатор персоны
     */
    public static void deletePerson(int id) {
        Connection connection = null;
        PreparedStatement psDelete = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);

            psDelete = connection.prepareStatement("SELECT id FROM links WHERE person1 = ?");
            psDelete.setInt(1, id);
            rs = psDelete.executeQuery();
            while (rs.next()) {
                deleteLink(rs.getInt("id"));
            }

            psDelete = connection.prepareStatement("SELECT id FROM links WHERE person2 = ?");
            psDelete.setInt(1, id);
            rs = psDelete.executeQuery();
            while (rs.next()) {
                deleteLink(rs.getInt("id"));
            }

            psDelete = connection.prepareStatement("SELECT id FROM personInfo WHERE personId = ?");
            psDelete.setInt(1, id);
            rs = psDelete.executeQuery();
            while (rs.next()) {
                deletePersonInfo(rs.getInt("id"));
            }

            psDelete = connection.prepareStatement("DELETE FROM persons WHERE id = ?");
            psDelete.setInt(1, id);
            psDelete.executeUpdate();

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
            if (psDelete != null) {
                try {
                    psDelete.close();
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

    /**
     * Метод для добавления представления персоны на доску
     * @param boardId - id доски
     * @param personId - id персоны
     */
    public static int addPersonInfo( int boardId, int personId) {
        Connection connection = null;
        PreparedStatement psCheckPersonDuplication = null;
        ResultSet rs = null;
        PreparedStatement psInsert = null;
        int id = 0;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psCheckPersonDuplication = connection.prepareStatement("SELECT * FROM personInfo " +
                    "WHERE boardId = ? AND personId = ?");
            psCheckPersonDuplication.setInt(1, boardId);
            psCheckPersonDuplication.setInt(2, personId);
            rs = psCheckPersonDuplication.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Персона уже на доске.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO personInfo (boardId, personId, x, y)" +
                        " VALUES( ?, ?, 0, 0)");
                psInsert.setInt(1, boardId);
                psInsert.setInt(2, personId);
                psInsert.executeUpdate();
                psInsert = connection.prepareStatement("SELECT id FROM personInfo WHERE boardId = ? AND personId = ?");
                psInsert.setInt(1, boardId);
                psInsert.setInt(2, personId);
                rs = psInsert.executeQuery();
                id = rs.getInt("id");
            }

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
            if (psCheckPersonDuplication != null) {
                try {
                    psCheckPersonDuplication.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
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
        return id;
    }

    /**
     * Метод, обновляющий координаты персоны на доске
     * @param x - координата x представления персоны на координатной сетке доски
     * @param y - координата y представления персоны на координатной сетке доски
     * @param id - идентификатор строки таблицы personInfo
     */
    public static void updatePersonInfo( int x, int y, int id) {
        Connection connection = null;
        PreparedStatement psUpdate = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psUpdate = connection.prepareStatement("UPDATE personInfo SET x = ?, y = ? WHERE id = ?");
            psUpdate.setInt(1, x);
            psUpdate.setInt(2, y);
            psUpdate.setInt(3, id);
            psUpdate.executeUpdate();

            //TODO автообновление отображений линков, прикрутить на onDragDropped
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psUpdate != null) {
                try {
                    psUpdate.close();
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

    /**
     * Метод для удаления представления персоны с доски
     * @param id - id представления персоны
     */
    public static void deletePersonInfo( int id) {
        Connection connection = null;
        Connection conn2 = null;
        PreparedStatement psDelete = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            conn2 = DriverManager.getConnection(jdbcURL, login, password);

            psDelete = connection.prepareStatement("SELECT id FROM tagPerson WHERE personInfoId = ?");
            psDelete.setInt(1, id);
            rs = psDelete.executeQuery();
            while (rs.next()) {
                deleteTagPerson(rs.getInt("id"));
            }

            psDelete = connection.prepareStatement("SELECT id FROM links WHERE person1 = ? OR person2 = ?");
            psDelete.setInt(1, id);
            psDelete.setInt(2, id);
            rs = psDelete.executeQuery();
            while (rs.next()) {
                deleteLink(rs.getInt("id"));
            }

            psDelete = connection.prepareStatement("DELETE FROM personInfo WHERE id = ?");
            psDelete.setInt(1, id);
            psDelete.executeUpdate();
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
            if (psDelete != null) {
                try {
                    psDelete.close();
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
            if (conn2 != null) {
                try {
                    conn2.close();
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

    /**
     * Метод для добавления нового вида связи между персонами
     * @param color - цвет связи в виде [r,g,b]
     * @param name - название связи
     * @param linetype - тип линии связи (сплошная, пунктир, точечная, пунктир-точка)
     * @param twosided - маркер наличия стрелки на связи
     * @param collectiveId - id коллектива
     */
    public static void addLinkType(String color, String name, String linetype, boolean twosided, int collectiveId) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psInsert = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM linktypes " +
                    "WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, collectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Вид связи с таким названием уже существует в этом коллективе.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO linktypes (color, name, linetype, twosided, collectiveId)" +
                        " VALUES( ?, ?, ?, ?, ?)");
                psInsert.setString(1, color);
                psInsert.setString(2, name);
                psInsert.setString(3, linetype);
                psInsert.setBoolean(4, twosided);
                psInsert.setInt(5, collectiveId);
                psInsert.executeUpdate();
            }
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
            if (psCheckNameCollision != null) {
                try {
                    psCheckNameCollision.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
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

    /**
     * Метод для обновления вида связи между двумя персонами
     * @param color - новый цвет связи
     * @param twosided - обновленный маркер наличия стрелки на связи
     * @param name - обновленное имя связи
     * @param linetype - обновленный тип линии связи
     * @param id - идентификатор типа связи
     */
    public static void updateLinkType( String color, boolean twosided, String name, String linetype, int id) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psUpdate = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM linkTypes WHERE id = ?");
            psCheckNameCollision.setInt(1, id);
            rs = psCheckNameCollision.executeQuery();

            if (rs.next() && rs.getString("name").compareTo(name) == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Вид связи с таким названием уже существует в этом коллективе.");
                alert.show();
            } else {
                psUpdate = connection.prepareStatement("UPDATE linktypes SET color = ?, name = ?, linetype = ?, twosided = ? " +
                        "WHERE id = ?");
                psUpdate.setString(1, color);
                psUpdate.setString(2, name);
                psUpdate.setString(3, linetype);
                psUpdate.setBoolean(4, twosided);
                psUpdate.setInt(5, id);
                psUpdate.executeUpdate();
            }

            //TODO возврат к редактору доски с обновлением отображения всех таких линков
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
            if (psCheckNameCollision != null) {
                try {
                    psCheckNameCollision.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psUpdate != null) {
                try {
                    psUpdate.close();
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

    /**
     * Метод для удаления типа связей с доски.
     * @param linkTypeId - идентификатор удаляемого типа связей.
     */
    public static void deleteLinkType(int linkTypeId) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psDelete = connection.prepareStatement("DELETE FROM links WHERE linkTypeId = ?");
            psDelete.setInt(1, linkTypeId);
            psDelete.executeUpdate();
            psDelete = connection.prepareStatement("DELETE FROM linktypes WHERE id = ?");
            psDelete.setInt(1, linkTypeId);
            psDelete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psDelete != null) {
                try {
                    psDelete.close();
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

    /**
     * Метод для добавления новой связи на доску и в БД
     * @param boardId - идентификатор доски
     * @param linkTypeId - идентификатор типа связи
     * @param person1 - идентификатор персоны, от которой идет связь
     * @param person2 - идентификатор персоны, к которой идет связь
     */
    public static void addLink( int boardId, int linkTypeId, int person1, int person2) {
        Connection connection = null;
        PreparedStatement psCheckDuplication = null;
        ResultSet rs = null;
        PreparedStatement psInsert = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psCheckDuplication = connection.prepareStatement("SELECT * FROM links " +
                    "WHERE boardId = ? AND person1 = ? AND person2 = ? AND linkTypeId = ?");
            psCheckDuplication.setInt(1, boardId);
            psCheckDuplication.setInt(2, person1);
            psCheckDuplication.setInt(3, person2);
            psCheckDuplication.setInt(4, linkTypeId);
            rs = psCheckDuplication.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Связь уже существует.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO links (boardId, linkTypeId, person1, person2)" +
                        " VALUES( ?, ?, ?, ?)");
                psInsert.setInt(1, boardId);
                psInsert.setInt(2, linkTypeId);
                psInsert.setInt(3, person1);
                psInsert.setInt(4, person2);
                psInsert.executeUpdate();
            }
            //TODO отрисовка линка
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
            if (psCheckDuplication != null) {
                try {
                    psCheckDuplication.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
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

    /**
     * Метод для удаления связи с доски
     * @param id - идентификатор удаляемой связи
     */
    public static void deleteLink( int id) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psDelete = connection.prepareStatement("DELETE FROM links WHERE id = ?");
            psDelete.setInt(1, id);
            psDelete.executeUpdate();
            //TODO удаление отрисованного линка с доски
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psDelete != null) {
                try {
                    psDelete.close();
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

    /**
     * Метод для добавления тега в список тегов коллектива
     * @param name - название тега
     * @param collectiveId - идентификатор коллектива
     */
    public static void addTag( String name, int collectiveId) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psInsert = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM tags WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, collectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Тег с таким названием уже существует.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO tags (name, collectiveId) " +
                        "VALUES( ?, ?)");
                psInsert.setString(1, name);
                psInsert.setInt(2, collectiveId);
                psInsert.executeUpdate();
            }

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
            if (psCheckNameCollision != null) {
                try {
                    psCheckNameCollision.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
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

    /**
     * Метод, позволяющий изменить тег
     * @param name - новый тег
     * @param oldName - старый тег
     * @param collectiveId - идентификатор коллектива
     */
    public static void updateTag( String name, String oldName, int collectiveId) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psUpdate = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM tags WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, collectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.next() && oldName.compareTo(name) != 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Такой тег уже существует.");
                alert.show();
            } else {
                psUpdate = connection.prepareStatement("UPDATE tags SET name = ? " +
                        "WHERE name = ? AND collectiveId = ?");
                psUpdate.setString(1, name);
                psUpdate.setString(2, oldName);
                psUpdate.setInt(3, collectiveId);
                psUpdate.executeUpdate();
            }

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
            if (psCheckNameCollision != null) {
                try {
                    psCheckNameCollision.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psUpdate != null) {
                try {
                    psUpdate.close();
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

    /**
     * Метод для удаления тега из коллектива
     * @param id - идентификатор тега
     */
    public static void deleteTag(int id) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psDelete = connection.prepareStatement("DELETE FROM tagPerson WHERE tagId = ?");
            psDelete.setInt(1, id);
            psDelete = connection.prepareStatement("DELETE FROM tags WHERE id = ?");
            psDelete.setInt(1, id);
            psDelete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psDelete != null) {
                try {
                    psDelete.close();
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

    /**
     * Метод для присвоения тега представлению персоны
     * @param personInfoId - идентификатор представления персоны на какой-то из досок
     * @param tagId - идентификатор тега
     */
    public static void addTagPerson( int personInfoId, int tagId) {
        Connection connection = null;
        PreparedStatement psCheckDuplication = null;
        ResultSet rs = null;
        PreparedStatement psInsert = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psCheckDuplication = connection.prepareStatement("SELECT * FROM tagPerson WHERE personInfoId = ? AND tagId = ?");
            psCheckDuplication.setInt(1, personInfoId);
            psCheckDuplication.setInt(2, tagId);
            rs = psCheckDuplication.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Такой тег уже есть у персоны.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO tagPerson (personInfoId, tagId)" +
                        " VALUES( ?, ?)");
                psInsert.setInt(1, personInfoId);
                psInsert.setInt(2, tagId);
                psInsert.executeUpdate();
            }

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
            if (psCheckDuplication != null) {
                try {
                    psCheckDuplication.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
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

    /**
     * Метод для удаления тега у представления персоны на доске
     * @param id - идентификатор tagPerson
     */
    public static void deleteTagPerson( int id) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            psDelete = connection.prepareStatement("DELETE FROM tagPerson WHERE id = ?");
            psDelete.setInt(1, id);
            psDelete.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psDelete != null) {
                try {
                    psDelete.close();
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

    public static ObservableList<Collective> loadCollectives() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        ObservableList<Collective> collectives = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM collectives");
            while (rs.next()) {
                collectives.add(new Collective(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
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
            if (statement != null) {
                try {
                    statement.close();
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
        return collectives;
    }

    public static ObservableList<Board> loadBoards() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ObservableList<Board> boards = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            statement = connection.prepareStatement("SELECT * FROM boards WHERE collectiveId = ?");
            statement.setInt(1, Main.currentCollectiveId);
            rs = statement.executeQuery();
            while (rs.next()) {
                boards.add(new Board(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getTimestamp(5), rs.getInt(2)));
            }
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
            if (statement != null) {
                try {
                    statement.close();
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
        return boards;
    }

    public static ObservableList<Person> loadPersons() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ObservableList<Person> persons = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            statement = connection.prepareStatement("SELECT * FROM persons WHERE collectiveId = ?");
            statement.setInt(1, Main.currentCollectiveId);
            rs = statement.executeQuery();
            while (rs.next()) {
                persons.add(new Person(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
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
            if (statement != null) {
                try {
                    statement.close();
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
        return persons;
    }

    public static String loadCollectiveName (int id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String res = "";

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            ps = connection.prepareStatement("SELECT name FROM collectives WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            res = rs.getString("name");
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
        return res;
    }

    public static ObservableList<Tag> loadPersonsTags (int personInfoId) {
        ObservableList<Tag> tags = FXCollections.observableArrayList();
        Connection connection = null;
        Connection conn2 = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            conn2 = DriverManager.getConnection(jdbcURL, login, password);
            statement1 = connection.prepareStatement("SELECT tagId FROM tagPerson WHERE personInfoId = ?");
            statement1.setInt(1,personInfoId);
            rs1 = statement1.executeQuery();
            while (rs1.next()) {
                statement2 = conn2.prepareStatement("SELECT * FROM tags WHERE id = ?");
                rs2 = statement2.executeQuery();
                rs2.next();
                tags.add(new Tag(rs2.getInt("id"), rs2.getString("name"), rs2.getInt("collectiveId")));
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs2 != null) {
                try {
                    rs2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement1 != null) {
                try {
                    statement1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement2 != null) {
                try {
                    statement2.close();
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
            if (conn2 != null) {
                try {
                    conn2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return  tags;
    }

    public static ArrayList<Tag> loadTags() {
        ArrayList<Tag> tags = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            ps = connection.prepareStatement("SELECT * FROM tags WHERE collectiveId = ?");
            ps.setInt(1, Main.currentCollectiveId);
            rs = ps.executeQuery();
            while (rs.next()) {
                tags.add(new Tag(rs.getInt("id"), rs.getString("name"), rs.getInt("collectiveId")));
            }
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

        return tags;
    }

    public static ObservableList<PersonInfo> loadPersonInfos() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ObservableList<PersonInfo> personInfos = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            statement = connection.prepareStatement("SELECT * FROM personInfos WHERE boardId = ?");
            statement.setInt(1, Main.currentBoardId);
            rs = statement.executeQuery();
            while (rs.next()) {
                personInfos.add(new PersonInfo(rs.getInt("id"), rs.getInt("personId"),
                        rs.getInt("boardId"), rs.getInt("x"), rs.getInt("y")));
            }
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
            if (statement != null) {
                try {
                    statement.close();
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
        return personInfos;
    }

    public static ObservableList<LinkType> loadLinkTypes() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ObservableList<LinkType> linkTypes = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(jdbcURL, login, password);
            statement = connection.prepareStatement("SELECT * FROM linktypes WHERE collectiveId = ?");
            statement.setInt(1, Main.currentCollectiveId);
            rs = statement.executeQuery();
            while (rs.next()) {
                linkTypes.add(new LinkType(rs.getInt("id"), LinkLineType.valueOf(rs.getString("linetype")),
                        LinkType.stringToColor(rs.getString("color")), rs.getString("name"),
                        rs.getBoolean("twosided")));
            }
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
            if (statement != null) {
                try {
                    statement.close();
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
        return linkTypes;
    }
}
