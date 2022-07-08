package ntcu.selab.SpringServer.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ntcu.selab.SpringServer.data.User;

public class UserDBManager {
    private static final String GITLAB_ID = "gitlabid";
    private static final String USERNAME = "username";
    private static final String NAME = "name";
    private static final String TOKEN = "password";
    private static final String EMAIL = "eamil";
    private static final String GITLAB_TOKEN = "gitlabtoken";
    private static final String ROLE = "role";
    private static UserDBManager dbManager = null;
    private static final Logger logger = LoggerFactory.getLogger(UserDBManager.class);
    private MysqlDatabase database = MysqlDatabase.getObject();

    public static UserDBManager getObject() {
        if (dbManager == null) {
            dbManager = new UserDBManager();
        }
        return dbManager;
    }

    public void addUser(User user) {
        String str = "insert into User (" + GITLAB_ID + "," + USERNAME + "," + NAME + ","
                + TOKEN + "," + EMAIL + "," + GITLAB_TOKEN + "," + ROLE + ")"
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);

            prestmt.setInt(1, user.getGitlabId());
            prestmt.setString(2, user.getUserName());
            prestmt.setString(3, user.getName());
            prestmt.setString(4, user.getPassword());
            prestmt.setString(5, user.getEmail());
            prestmt.setString(6, user.getGitlabToken());
            prestmt.setString(7, user.getRole());
            prestmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    public String getPassword(String username) {
        String password = "";
        String str = "SELECT password FROM User WHERE username = ?";
        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);
            prestmt.setString(1, username);
            try (ResultSet rs = prestmt.executeQuery()) {
                while (rs.next()) {
                    password = rs.getString(TOKEN);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return password;
    }

    public void ModifyPassword(String username, String password, String newpassword) {
        String str = "UPDATE User SET password=? WHERE username = ?";
        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);

            prestmt.setString(1, newpassword);
            prestmt.setString(2, username);
            prestmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    public boolean checkPassword(String username, String password) {
        boolean check = false;
        String current = getPassword(username);
        if (current.equals(password)) {
            check = true;
        }
        return check;
    }

    public int getUseridByUsername(String username) {
        String str = "SELECT id FROM User WHERE username = ?";
        int id = 0;
        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);

            prestmt.setString(1, username);
            try (ResultSet rs = prestmt.executeQuery();) {
                while (rs.next()) {
                    id = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return id;
    }

    public int getGitlabidByUsername(String username) {
        String str = "SELECT gitlabid FROM User WHERE username = ?";
        int gitlabid = 0;
        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);

            prestmt.setString(1, username);
            try (ResultSet rs = prestmt.executeQuery();) {
                while (rs.next()) {
                    gitlabid = rs.getInt("gitlabid");
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return gitlabid;
    }

    public User getUserInfo(int id) {
        User user = new User();
        String str = "SELECT * FROM User WHERE id = ?";
        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);

            prestmt.setInt(1, id);
            try (ResultSet rs = prestmt.executeQuery()) {
                while (rs.next()) {
                    int gitlabid = rs.getInt(GITLAB_ID);
                    String username = rs.getString(USERNAME);
                    String name = rs.getString(NAME);
                    String password = rs.getString(TOKEN);
                    String email = rs.getString(EMAIL);
                    String gitlabtoken = rs.getString(GITLAB_TOKEN);
                    String role = rs.getString(ROLE);

                    user.setId(id);
                    user.setGitlabId(gitlabid);
                    user.setUserName(username);
                    user.setEmail(email);
                    user.setName(name);
                    user.setGitlabToken(gitlabtoken);
                    user.setPassword(password);
                    user.setRole(role);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return user;
    }

    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        String str = "SELECT * FROM User";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = database.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(str);

            while (rs.next()) {
                int id = rs.getInt("id");
                int gitlabid = rs.getInt(GITLAB_ID);
                String username = rs.getString(USERNAME);
                String name = rs.getString(NAME);
                String email = rs.getString(EMAIL);
                String password = rs.getString(TOKEN);
                String gitlabtoken = rs.getString(GITLAB_TOKEN);
                String role = rs.getString(ROLE);

                User user = new User(name, username, email, password, role);
                user.setId(id);
                user.setGitlabToken(gitlabtoken);
                user.setGitlabId(gitlabid);
                users.add(user);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                stmt.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return users;
    }

    public boolean checkUsername(String username) {
        boolean check = false;
        String str = "SELECT count(*) FROM User WHERE username = ?";
        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);

            prestmt.setString(1, username);
            try (ResultSet rs = prestmt.executeQuery()) {
                rs.next();
                if (rs.getInt("count(*)") > 0) {
                    check = true;
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return check;
    }

    public boolean checkEmail(String email) {
        boolean check = false;
        String str = "SELECT count(*) FROM User WHERE email = ?";
        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);

            prestmt.setString(1, email);
            try (ResultSet rs = prestmt.executeQuery()) {
                rs.next();
                if (rs.getInt("count(*)") > 0) {
                    check = true;
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return check;
    }

    public String getUsernameById(int id) {
        String username = "";
        String str = "SELECT username FROM User WHERE id = ?";
        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);

            prestmt.setInt(1, id);
            try (ResultSet rs = prestmt.executeQuery()) {
                while (rs.next()) {
                    username = rs.getString(USERNAME);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return username;
    }

    public void DeleteUserById(int id) {
        String str = "DELETE FROM spring_server.User WHERE id = ?";
        Connection conn = null;
        PreparedStatement prestmt = null;
        try {
            conn = database.getConnection();
            prestmt = conn.prepareStatement(str);

            prestmt.setInt(1, id);
            prestmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                prestmt.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            try {
                conn.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
