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

public class EmploiTemps {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    public boolean isCourseConflict(String enseignant, String heure_debut, String heure_fin, String salle) {
        try {
            // Vérifier si l'enseignant a déjà un cours à la même heure
            ps = con.prepareStatement("SELECT * FROM emploitemps WHERE enseignant = ? AND (heure_debut <= ? AND heure_fin >= ?)");
            ps.setString(1, enseignant);
            ps.setString(2, heure_debut);
            ps.setString(3, heure_fin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String message = "Il y a déjà un cours pour l'enseignant " + enseignant + " à la même heure.";
                JOptionPane.showMessageDialog(null, message);
                return true;
            }

            // Vérifier si la salle est déjà occupée à la même heure
            ps = con.prepareStatement("SELECT * FROM emploitemps WHERE salle = ? AND (heure_debut <= ? AND heure_fin >= ?)");
            ps.setString(1, salle);
            ps.setString(2, heure_debut);
            ps.setString(3, heure_fin);
            rs = ps.executeQuery();
            if (rs.next()) {
                Component EmploiTemps = null;

                System.out.println("La salle " + salle + " est déjà occupée à la même heure.");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmploiTemps.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public void insert(int id, String enseignant, String heure_debut, String heure_fin, String salle, String cours) {
        String sql = "insert into emploitemps values(?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, enseignant);
            ps.setString(3, heure_debut);
            ps.setString(4, heure_fin);
            ps.setString(5, salle);
            ps.setString(6, cours);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "L'emploi du temps a ete ajoute avec success");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmploiTemps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getEmploiTempsValue(JTable table, String searchValue) {
        String sql = "select * from emploitemps where concat(enseignant,heure_debut,heure_fin,salle) like ?  order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[6];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);

                model.addRow(row);

            }
        } catch (SQLException ex) {
            Logger.getLogger(EmploiTemps.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getMax() {
        int id = 0;
        Statement st;

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from emploitemps");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnneeScolaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

}
