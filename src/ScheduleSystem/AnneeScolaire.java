package ScheduleSystem;

/**
 *
 * @author DOUVIC
 */
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

public class AnneeScolaire {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    public void insert(int id, String code, String dateDebut, String dateFin) {
        String sql = "insert into anneescolaire values(?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, code);
            ps.setString(3, dateDebut);
            ps.setString(4, dateFin);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "L'année Scolaire a ete ajoute avec success");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(int id, String code, String DateDebut, String DateFin) {
        String sql = "update anneescolaire set code=?,DateDebut=?,DateFin=? where id=?";

        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, code);
            ps.setString(2, DateDebut);
            ps.setString(3, DateFin);
            ps.setInt(4, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "l'annee Scolaire a été mis à jour");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnneeScolaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getAnneeScolaireValue(JTable table, String searchValue) {
        String sql = "select * from anneescolaire where concat(code,dateDebut,dateFin) like ?  order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[4];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);

                model.addRow(row);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AnneeScolaire.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getMax() {
        int id = 0;
        Statement st;

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from anneescolaire");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnneeScolaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public boolean isIdexist(int id) {
        try {
            ps = con.prepareStatement("select * from anneescolaire where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnneeScolaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
