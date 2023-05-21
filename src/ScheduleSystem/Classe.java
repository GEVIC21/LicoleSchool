
package ScheduleSystem;


import db.MyConnection;
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

/**
 *
 * @author DOUVIC
 */
public class Classe {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    
    
    public int getMax() {
        int id = 0;
        Statement st;

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from Classe");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public void insert(int id, String intitule, String code) {
        String sql = "insert into classe values(?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, intitule);
            ps.setString(3, code);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "La classe a ete ajoute avec success");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void getClasseValue(JTable table, String searchValue) {
        String sql = "select * from classe where concat(intitule,code) like ?  order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[3];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);

                model.addRow(row);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void delete(int id) {
        int yesOrNo = JOptionPane.showConfirmDialog(null, "Vos informations vont etre supprimes", "suppression d'une classe", JOptionPane.OK_CANCEL_OPTION, 0);
        if (yesOrNo == JOptionPane.OK_OPTION) {

            try {
                ps = con.prepareStatement("delete from classe where id = ?");
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "la classe a ete bien supprimes");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
      public void update(int id, String intitule, String code) {
        String sql = "update classe set intitule=?,code=? where id=?";

        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, code);
            ps.setString(2, intitule);
            ps.setInt(3, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "la Classe été mis à jour");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public boolean isIdexist(int id) {
        try {
            ps = con.prepareStatement("select * from classe where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
