package ScheduleSystem;

import db.MyConnection;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Dashboard {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    public void getDashboardValue(JTable table, String searchValue) {
        String sql = "select * from emploitemps where concat(enseignant,cours,heure_debut,heure_fin) like ?  order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[6];
                String concatValue = rs.getString(3) + " à " + rs.getString(4); // Concaténation de row[2] avec row[1]
                row[0] = rs.getString(2);
                row[1] = rs.getString(6);
//                row[2] = rs.getString(4);
                row[2] = concatValue;

                model.addRow(row);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
