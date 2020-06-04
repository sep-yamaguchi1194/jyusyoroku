package jyusyoroku.sep.yt;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class Common {

    //フォームから受け取るname(名前), address(住所), tel(電話番号)について検証を行い、
    //各々の値が許容されなかった場合エラーメッセージを追加、最後にエラーメッセージをまとめて返す。
    public String getErr(String name, String address, String tel) {
        //エラーメッセージ定数
        final String ERRMSG_NAME01 = "名前は全角20文字以内で入力してください";
        final String ERRMSG_NAME02 = "名前は必須項目です";
        final String ERRMSG_ADDRESS01 = "住所は全角40文字以内で入力してください";
        final String ERRMSG_ADDRESS02 = "住所は必須項目です";
        final String ERRMSG_TEL01 = "電話番号は「000-0000-0000」の形式で入力してください";

        //戻り値用変数
        String returnVal = "";

        //nameのバイト数チェック( 0 < [nameのバイト数] ≦ 40 を許容)
        if(getValueBytes(name) > 40) {
            returnVal += ERRMSG_NAME01 + "<br>";
        } else if(getValueBytes(name) == 0) {
            returnVal += ERRMSG_NAME02 + "<br>";
        }

        //addressのバイト数チェック( 0 < [addressのバイト数] ≦ 80 を許容)
        if(getValueBytes(address) > 80) {
            returnVal += ERRMSG_ADDRESS01 + "<br>";
        } else if(getValueBytes(address) == 0)  {
            returnVal += ERRMSG_ADDRESS02 + "<br>";
        }

        //telは必須項目ではないので、バイト数が0でない場合チェックにかける
        if(getValueBytes(tel) != 0) {
            //telが [0～9 3桁][-][0～9 4桁][-][0～9 4桁] の形式であるかチェック
            String regex = "\\d{3}-\\d{4}-\\d{4}";
            if(!Pattern.matches(regex, tel)) {
                returnVal += ERRMSG_TEL01 + "<br>";
            }
        }
        return returnVal;
    }

    public ResultSet getCategoryAll() {
        Connection connect = null;
        Statement stmt = null;
        //PreparedStatement ps = null;
        ResultSet rs = null;
        String getQuery = "";


        try {
            String DATABASE_NAME = "java_mysql";
            String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
            String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME + PROPATIES;
            String USER = "root";
            String PASS = "password";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        getQuery = "SELECT categoryid, categoryname "
                 + "FROM category "
                 + "ORDER BY categoryid ASC";

        try {
            stmt = connect.createStatement();
            rs = stmt.executeQuery(getQuery);
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return rs;
    }

    public String getCategoryName(String id) {
        Connection connect = null;
        //Statement stmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String getQuery = "";
        String categoryname = "";

        try {
            String DATABASE_NAME = "java_mysql";
            String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
            String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME + PROPATIES;
            String USER = "root";
            String PASS = "password";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        getQuery = "SELECT categoryid, categoryname "
                 + "FROM category "
                 + "WHERE categoryid = ?";

        try {
            ps = connect.prepareStatement(getQuery);
            ps.setString(1, id);
            rs = ps.executeQuery();
            rs.next();
            categoryname = rs.getString(2);
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return categoryname;
    }

    //Stringのバイト数取得用 Stringを受け取りその(Shift_JIS形式での)バイト数をint型で返す
    public int getValueBytes(String value) {
        int bytes = 0;

        if(value == null) value = "";
        try {
            bytes = value.getBytes("SJIS").length;
        } catch (UnsupportedEncodingException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return bytes;
    }
}
