package ru.kpfu.itis.khakov.repository;

import ru.kpfu.itis.khakov.entity.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserRepository {
    /**
     * Добавление нового пользователя в базу данных
     */
    public static void addUser(User user) {
        try {
            Connection conn = new MyConnection().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO users VALUES (DEFAULT,?,?);"
            );
            ps.setString(1, user.getUsername());
            ps.setString(2, md5Decoder(user.getPassword()));
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Достаем пользователя по имени
     */
    public static boolean getLogin(String login) {
        boolean userLogin = false;
        try {
            Connection conn = new MyConnection().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT login, password FROM users WHERE login =?");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userLogin = true;
            }
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userLogin;
    }
    /**
     * Авторизация пользователя
     */
    public static User authUser(String login, String password) {
        password = md5Decoder(password);
        User user = null;
        try {
            Connection conn = new MyConnection().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT login, password FROM users WHERE login =?");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("PASSWORD").equals(password)){
                    user = new User(rs.getString("LOGIN"), rs.getString("PASSWORD"));
            }}
            ps.close();
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }
    /**
     * Достаем имя пользователя по сессии
     */
    public static String getLoginBySession(String login) {
        String user = null;
        try {
            Connection conn = MyConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("(SELECT login FROM users)");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (login.equals(md5Decoder(rs.getString("login"))))
                    user = rs.getString("login");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * MD5 хэширование пароля
     */
    public static String md5Decoder(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(password.getBytes(), 0, password.length());
        String pass = new BigInteger(1, messageDigest.digest()).toString(16);
        if (pass.length() < 32) {
            pass = "0" + pass;
        }
        return pass;
    }
}
