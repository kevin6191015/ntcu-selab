package ntcu.selab.SpringServer.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ntcu.selab.SpringServer.data.Assignment;

public class AssignmentDBManager {
    private static AssignmentDBManager dbManager = new AssignmentDBManager();
    private MysqlDatabase database = MysqlDatabase.getObject();
    private static final Logger logger = LoggerFactory.getLogger(AssignmentDBManager.class);

    public static AssignmentDBManager getObject() {
        return dbManager;
    }

    private AssignmentDBManager() {

    }

    public void addAssignment(Assignment assignment) {
        String str = "INSERT INTO Assignment(`name`, `createTime`, `description`,"
                + " VALUES(?, ?, ?)";
        Timestamp createtime = new Timestamp(assignment.getCreateTime().getTime());

        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);

            prestmt.setString(1, assignment.getName());
            prestmt.setTimestamp(2, createtime);
            prestmt.setString(3, assignment.getDescription());
            prestmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Assignment getAssignmentbyName(String name) {
        Assignment assignment = new Assignment();
        String str = "SELECT * FROM Assignment WHERE name = ?";
        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);

            prestmt.setString(1, name);
            try (ResultSet rs = prestmt.executeQuery()) {
                while (rs.next()) {
                    assignment.setId(rs.getInt("id"));
                    assignment.setName(rs.getString(name));
                    assignment.setCreateTime(rs.getTimestamp("createtime"));
                    assignment.setDescription(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return assignment;
    }

    public String getAssignmentNameById(int id) {
        String str = "SELECT name FROM Assignment WHERE id = ?";
        String assignmentname = "";
        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);

            prestmt.setInt(1, id);
            try (ResultSet rs = prestmt.executeQuery()) {
                while (rs.next()) {
                    assignmentname = rs.getString("name");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return assignmentname;
    }

    public int getAssignmentIdbyName(String assignmentname) {
        String str = "SELECT id FROM Assignment WHERE name = ?";
        int id = 0;
        Connection conn = null;
        PreparedStatement prestmt = null;
        ResultSet rs = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);

            prestmt.setString(1, assignmentname);
            rs = prestmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return id;
    }

    public List<Assignment> getAllAssignment() {
        List<Assignment> assignments = new ArrayList<>();
        String str = "SELECT id, name, createTime, description "
                + "FROM Assignment;";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = database.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(str);
            while (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("id"));
                assignment.setName(rs.getString("name"));
                assignment.setCreateTime(rs.getTimestamp("createtime"));
                assignment.setDescription(rs.getString("description"));
                assignments.add(assignment);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                stmt.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return assignments;
    }
}
