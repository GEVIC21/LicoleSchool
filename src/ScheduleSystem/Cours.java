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

public class Cours {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;



    public int getMax() {
        int id = 0;
        Statement st;

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from cours");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Cours.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public void insert(int id, String matiere, String enseignant, String classe, String annee_scolaire) {
        String sql = "insert into cours values(?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, matiere);
            ps.setString(3, enseignant);
            ps.setString(4, classe);
            ps.setString(5, annee_scolaire);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Le Cours a ete ajoute avec success");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Classe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void getCoursValue(JTable table, String searchValue) {
        String sql = "select * from cours where concat(matiere,enseignant,classe,annee_scolaire) like ?  order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);

                model.addRow(row);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Cours.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
