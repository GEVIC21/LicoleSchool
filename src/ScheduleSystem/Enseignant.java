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
public class Enseignant {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    public int getMax() {
        int id = 0;
        Statement st;

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from Enseignant");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Enseignant.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    //methode pour inserer les Enseignants dans la base de donnée
    public void insert(int id, String nom, String genre, String email, String telephone, String matricule, String nationalite, String addresse, String pays, String imagePath) {
        String sql = "insert into Enseignant values(?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, nom);
            ps.setString(3, genre);
            ps.setString(4, email);
            ps.setString(5, telephone);
            ps.setString(6, matricule);
            ps.setString(7, nationalite);
            ps.setString(8, addresse);
            ps.setString(9, pays);
            ps.setString(10, imagePath);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "L'enseignant a ete ajoute avec success");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Enseignant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // verifier si l'email existe deja
    public boolean isEmailExist(String email) {
        try {
            ps = con.prepareStatement("select * from enseignant where email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Enseignant.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isPhonexist(String telephone) {
        try {
            ps = con.prepareStatement("select * from enseignant where telephone = ?");
            ps.setString(1, telephone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Enseignant.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isIdexist(int id) {
        try {
            ps = con.prepareStatement("select * from enseignant where id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Enseignant.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // optenir tous les valeurs de la table enseignant
    public void getEnseignantValue(JTable table, String searchValue) {
        String sql = "select * from enseignant where concat(id,nom,email,telephone) like ?  order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[10];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                row[9] = rs.getString(10);
                model.addRow(row);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Enseignant.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //mettre a jour

    public void update(int id, String nom, String genre, String email, String telephone, String matricule, String nationalite, String addresse, String pays, String imagePath) {
        String sql = "update enseignant set nom=?,genre=?,email=?,telephone=?,matricule=?,nationalite=?,addresse=?,pays=?,imagePath=? where id=?";

        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, nom);
            ps.setString(2, genre);
            ps.setString(3, email);
            ps.setString(4, telephone);
            ps.setString(5, matricule);
            ps.setString(6, nationalite);
            ps.setString(7, addresse);
            ps.setString(8, pays);
            ps.setString(9, imagePath);
            ps.setInt(10, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "les données de l'enseignant ont été mis à jour");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Enseignant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // suppression des informations d'un enseignant
    public void delete(int id) {
        int yesOrNo = JOptionPane.showConfirmDialog(null, "Vos informations vont etre supprimes", "suppression d'un Enseignant", JOptionPane.OK_CANCEL_OPTION, 0);
        if (yesOrNo == JOptionPane.OK_OPTION) {

            try {
                ps = con.prepareStatement("delete from enseignant where id = ?");
                ps.setInt(1, id);
                if(ps.executeUpdate()>0){
                            JOptionPane.showMessageDialog(null, "les données de l'enseignant ont été supprimés");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Enseignant.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
