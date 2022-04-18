package com.banking.myproject;

import java.sql.*;

public class Database {
    Connection conn;

    public Database() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:databases/sqlite_database.db");
    }

    public void insertData(Account acc) throws SQLException {
            String query = "INSERT OR IGNORE INTO account VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, acc.getAccountNo());
            pst.setString(2, acc.getAccountPin());
            pst.setString(3, acc.getAccountType());
            pst.setString(4, acc.getName());
            pst.setString(5, acc.getGender());
            pst.setString(6, acc.getDateOfBirth().toString());
            pst.setString(7, acc.getNationality());
            pst.setString(8, acc.getOccupation());
            pst.setString(9, acc.getAddress());
            pst.setString(10, acc.getPhoneNum());
            pst.executeUpdate();
    }

    public PreparedStatement prepare(String pStatement) throws SQLException {
        return conn.prepareStatement(pStatement);
    }

    public void insertBank(Bank bank) throws SQLException {
        String query = "INSERT OR REPLACE INTO bank VALUES (?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, bank.getAccNo());
        pst.setString(2, bank.getName());
        pst.setDouble(3, bank.getBalance());
        pst.executeUpdate();
    }

    void readBank(Bank bank) {
        String sql = "select * from bank inner join account on bank.account_no=account.account_no where account.account_no=?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, MyPage.accNo);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                bank.setAccNo(rs.getString("account_no"));
                bank.setName(rs.getString("name"));
                bank.setBalance(rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
