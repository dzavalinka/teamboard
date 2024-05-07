package com.dzavalinskii;

import com.dzavalinskii.board_controller.Collective;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class DBUtils {

    static String jdbcURL = "jdbc:derby:collectivedb";

    /**
     * Метод для добавления нового коллектива в БД
     * @param name - имя нового коллектива
     * @param description - описание нового коллектива
     */
    public static void addCollective(String name, String description) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psInsert = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM collectives WHERE name = ?");
            psCheckNameCollision.setString(1, name);
            rs = psCheckNameCollision.executeQuery();

            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Коллектив с таким названием уже существует.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO collectives (name, description) VALUES( ?, ?)");
                psInsert.setString(1, name);
                psInsert.setString(2, description);
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
            connection = DriverManager.getConnection(jdbcURL);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM collectives WHERE name = ?");
            psCheckNameCollision.setString(1, name);
            rs = psCheckNameCollision.executeQuery();

            if (oldName.compareTo(name)!=0 && rs.isBeforeFirst()) {
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

            //TODO возврат к списку коллективов и его обновление, event
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
     * Метод для удаления коллектива из базы данных.
     * @param name - название удаляемого коллектива
     */
    public static void deleteCollective( String name) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psDelete = connection.prepareStatement("DELETE FROM collectives WHERE name = ?");
            psDelete.setString(1, name);
            psDelete.executeUpdate();
            //TODO возврат к списку коллективов и его обновление, event, удаление борд, персон и всего связанного с коллективом
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
     * Метод для добавления новой доски в БД.
     * @param collectiveId - id коллектива, к которому относится доска
     * @param name - имя доски
     * @param description - описание доски
     * @param timestamp - временная отметка доски
     */
    public static void addBoard( int collectiveId, String name, String description, long timestamp) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psInsert = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM boards " +
                    "WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, collectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Доска с таким названием уже существует в этом коллективе.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO boards VALUES( ?, ?, ?, ?)");
                psInsert.setInt(1, collectiveId);
                psInsert.setString(2, name);
                psInsert.setString(3, description);
                psInsert.setLong(4, timestamp);
                psInsert.executeUpdate();
            }
            //TODO переход на экран редактора новой доски, event
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
     * @param oldName - прошлое имя доски
     * @param collectiveId - id коллектива, содержащего доску
     */
    public static void updateBoard(String name, String description, long timestamp, String oldName, int collectiveId) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psUpdate = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM boards WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, collectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.isBeforeFirst() && oldName.compareTo(name) != 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Доска с таким названием уже существует.");
                alert.show();
            } else {
                psUpdate = connection.prepareStatement("UPDATE boards SET name = ?, description = ?, timestamp = ? " +
                        "WHERE name = ? AND collectiveId = ?");
                psUpdate.setString(1, name);
                psUpdate.setString(2, description);
                psUpdate.setLong(3, timestamp);
                psUpdate.setString(4, oldName);
                psUpdate.setInt(5, collectiveId);
                psUpdate.executeUpdate();
            }

            //TODO возврат к редактору доски с обновлением текущих данных, event
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
     * Метод для удаления доски из коллектива
     * @param name - название удаляемой доски
     * @param collectiveId - id коллектива
     */
    public static void deleteBoard(String name, int collectiveId) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psDelete = connection.prepareStatement("DELETE FROM boards WHERE name = ? AND collectiveId = ?");
            psDelete.setString(1, name);
            psDelete.setInt(2, collectiveId);
            psDelete.executeUpdate();
            //TODO возврат к редактору борд, удаление линков этой борды из бд, удаление personinfo борды из бд, PersonTag борды
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
            connection = DriverManager.getConnection(jdbcURL);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM persons " +
                    "WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, collectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Персона с таким именем уже существует в этом коллективе.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO persons VALUES( ?, ?, ?)");
                psInsert.setString(1, name);
                psInsert.setString(2, description);
                psInsert.setInt(3, collectiveId);
                psInsert.executeUpdate();
            }
            //TODO переход на экран редактора доски с обновлением списка персон, event
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
     * @param oldName - старое имя персоны
     * @param collectiveId - id коллектива персоны
     */
    public static void updatePerson( String name, String description, String oldName, int collectiveId) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psUpdate = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM persons WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, collectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.isBeforeFirst() && oldName.compareTo(name) != 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Персона с таким именем уже существует.");
                alert.show();
            } else {
                psUpdate = connection.prepareStatement("UPDATE persons SET name = ?, description = ? " +
                        "WHERE name = ? AND collectiveId = ?");
                psUpdate.setString(1, name);
                psUpdate.setString(2, description);
                psUpdate.setString(3, oldName);
                psUpdate.setInt(4, collectiveId);
                psUpdate.executeUpdate();
            }

            //TODO возврат к редактору доски с обновлением текущих данных, event, прикрутить image
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
     * Метод для удаления персоны с доски
     * @param name - имя удаляемой персоны
     * @param collectiveId - id коллектива удаляемой персоны
     */
    public static void deletePerson( String name, int collectiveId) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psDelete = connection.prepareStatement("DELETE FROM persons WHERE name = ? AND collectiveId = ?");
            psDelete.setString(1, name);
            psDelete.setInt(2, collectiveId);
            psDelete.executeUpdate();
            //TODO возврат к редактору борд, удаление линков этой персоны из бд, удаление personinfo персоны из бд, PersonTag борды
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
     * Метод для добавления представления персоны на доску
     * @param boardId - id доски
     * @param personId - id персоны
     */
    public static void addPersonInfo( int boardId, int personId) {
        Connection connection = null;
        PreparedStatement psCheckPersonDuplication = null;
        ResultSet rs = null;
        PreparedStatement psInsert = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psCheckPersonDuplication = connection.prepareStatement("SELECT * FROM personInfo " +
                    "WHERE boardId = ? AND personId = ?");
            psCheckPersonDuplication.setInt(1, boardId);
            psCheckPersonDuplication.setInt(2, personId);
            rs = psCheckPersonDuplication.executeQuery();

            if (rs.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Персона уже на доске.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO personInfo VALUES( ?, ?, 0, 0)");
                psInsert.setInt(1, boardId);
                psInsert.setInt(2, personId);
                psInsert.executeUpdate();
            }
            //TODO добавление нода персоны на доску в (0;0)
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
            connection = DriverManager.getConnection(jdbcURL);
            psUpdate = connection.prepareStatement("UPDATE personInfo SET x = ?, y = ? WHERE id = ?");
            psUpdate.setInt(1, x);
            psUpdate.setInt(2, y);
            psUpdate.setInt(3, id);
            psUpdate.executeUpdate();

            //TODO автообновление отображений линков
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
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psDelete = connection.prepareStatement("DELETE FROM personInfo WHERE id = ?");
            psDelete.setInt(1, id);
            psDelete.executeUpdate();
            //TODO удаление линков с доски и из бд, очистка TagPerson
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
     * Метод для добавления нового вида связи между персонами
     * @param color - цвет связи в виде [r,g,b]
     * @param name - название связи
     * @param linetype - тип линии связи (сплошная, пунктир, точечная, пунктир-точка)
     * @param twosided - маркер наличия стрелки на связи
     * @param collectiveId - id коллектива
     */
    public static void addLinkType( String color, String name, String linetype, boolean twosided, int collectiveId) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psInsert = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM linktypes " +
                    "WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, collectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Вид связи с таким названием уже существует в этом коллективе.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO linktypes VALUES( ?, ?, ?, ?, ?)");
                psInsert.setString(1, color);
                psInsert.setString(2, name);
                psInsert.setString(3, linetype);
                psInsert.setBoolean(4, twosided);
                psInsert.setInt(5, collectiveId);
                psInsert.executeUpdate();
            }
            //TODO переход на экран редактора доски, закрытие экрана нового вида связи, event
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
     * @param oldName - старое имя связи
     * @param collectiveId - id коллектива
     */
    public static void updateLinkType( String color, boolean twosided, String name, String linetype, String oldName, int collectiveId) {
        Connection connection = null;
        PreparedStatement psCheckNameCollision = null;
        ResultSet rs = null;
        PreparedStatement psUpdate = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM linkTypes WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, collectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.isBeforeFirst() && oldName.compareTo(name) != 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Тип связи с таким названием уже существует.");
                alert.show();
            } else {
                psUpdate = connection.prepareStatement("UPDATE linktypes SET color = ?, name = ?, linetype = ?, twosided = ? " +
                        "WHERE name = ? AND collectiveId = ?");
                psUpdate.setString(1, color);
                psUpdate.setString(2, name);
                psUpdate.setString(3, linetype);
                psUpdate.setBoolean(4, twosided);
                psUpdate.setString(5, oldName);
                psUpdate.setInt(6, collectiveId);
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
     * Метод для удаления типа связей с доски
     * @param name - название типа связей
     * @param collectiveId - id коллектива
     */
    public static void deleteLinkType( String name, int collectiveId) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psDelete = connection.prepareStatement("DELETE FROM linktypes WHERE name = ? AND collectiveId = ?");
            psDelete.setString(1, name);
            psDelete.setInt(2, collectiveId);
            psDelete.executeUpdate();
            //TODO удаление всех линков такого вида на доске
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
            connection = DriverManager.getConnection(jdbcURL);
            psCheckDuplication = connection.prepareStatement("SELECT * FROM links " +
                    "WHERE boardId = ? AND person1 = ? AND person2 = ? AND linkTypeId = ?");
            psCheckDuplication.setInt(1, boardId);
            psCheckDuplication.setInt(2, person1);
            psCheckDuplication.setInt(3, person2);
            psCheckDuplication.setInt(4, linkTypeId);
            rs = psCheckDuplication.executeQuery();

            if (rs.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Связь уже существует.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO links VALUES( ?, ?, ?, ?)");
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
            connection = DriverManager.getConnection(jdbcURL);
            psDelete = connection.prepareStatement("DELETE FROM links WHERE id = ?");
            psDelete.setInt(1, id);
            psDelete.executeUpdate();
            //TODO удаление линка с доски
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
            connection = DriverManager.getConnection(jdbcURL);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM tags WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, collectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Тег с таким названием уже существует.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO tags VALUES( ?, ?)");
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
            connection = DriverManager.getConnection(jdbcURL);
            psCheckNameCollision = connection.prepareStatement("SELECT * FROM tags WHERE name = ? AND collectiveId = ?");
            psCheckNameCollision.setString(1, name);
            psCheckNameCollision.setInt(2, collectiveId);
            rs = psCheckNameCollision.executeQuery();

            if (rs.isBeforeFirst() && oldName.compareTo(name) != 0) {
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
     * @param name - удаляемый тег
     * @param collectiveId - идентификатор коллектива
     */
    public static void deleteTag( String name, int collectiveId) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psDelete = connection.prepareStatement("DELETE FROM tags WHERE name = ? AND collectiveId = ?");
            psDelete.setString(1, name);
            psDelete.setInt(2, collectiveId);
            psDelete.executeUpdate();
            //TODO почистить TagPerson
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
     * @param TagId - идентификатор тега
     */
    public static void addTagPerson( int personInfoId, int TagId) {
        Connection connection = null;
        PreparedStatement psCheckDuplication = null;
        ResultSet rs = null;
        PreparedStatement psInsert = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psCheckDuplication = connection.prepareStatement("SELECT * FROM TagPerson WHERE personInfoId = ? AND TagId = ?");
            psCheckDuplication.setInt(1, personInfoId);
            psCheckDuplication.setInt(2, TagId);
            rs = psCheckDuplication.executeQuery();

            if (rs.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Такой тег уже есть у персоны.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO TagPerson VALUES( ?, ?)");
                psInsert.setInt(1, personInfoId);
                psInsert.setInt(2, TagId);
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
     * @param TagId - идентификатор тега
     * @param personInfoId - идентификатор персоны
     */
    public static void deleteTagPerson( int TagId, int personInfoId) {
        Connection connection = null;
        PreparedStatement psDelete = null;

        try {
            connection = DriverManager.getConnection(jdbcURL);
            psDelete = connection.prepareStatement("DELETE FROM TagPerson WHERE TagId = ? AND personInfoId = ?");
            psDelete.setInt(1, TagId);
            psDelete.setInt(2, personInfoId);
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
            connection = DriverManager.getConnection(jdbcURL);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM collectives");
            while (rs.next()) {
                collectives.add(new Collective(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return collectives;
    }
}
