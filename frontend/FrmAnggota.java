package frontend;

import backend.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.event.*;

public class FrmAnggota extends javax.swing.JFrame {
    
    // Deklarasi Komponen
    private JTextField txtIdAnggota;
    private JTextField txtNama;
    private JTextField txtAlamat;
    private JTextField txtTelepon;
    private JButton btnSimpan;
    private JButton btnHapus;
    private JButton btnTambahBaru;
    private JTextField txtCari;
    private JButton btnCari;
    private JTable tblAnggota;
    private JScrollPane jScrollPane1;

    private JLabel labelId;
    private JLabel labelNama;
    private JLabel labelAlamat;
    private JLabel labelTelepon;

    public FrmAnggota() {
        // Inisialisasi Komponen
        txtIdAnggota = new JTextField();
        txtNama = new JTextField();
        txtAlamat = new JTextField();
        txtTelepon = new JTextField();
        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnTambahBaru = new JButton("Tambah Baru");
        txtCari = new JTextField();
        btnCari = new JButton("Cari");
        tblAnggota = new JTable();
        jScrollPane1 = new JScrollPane();

        labelId = new JLabel("ID Anggota");
        labelNama = new JLabel("Nama");
        labelAlamat = new JLabel("Alamat");
        labelTelepon = new JLabel("Telepon");

        txtIdAnggota.setText("0");
        txtIdAnggota.setEnabled(false);

        // Definisi Kolom Tabel (4 Kolom: ID, Nama, Alamat, Telepon)
        tblAnggota.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Nama", "Alamat", "Telepon"}
        ));
        jScrollPane1.setViewportView(tblAnggota);

        getContentPane().setLayout(null);

        // Pengaturan Posisi (Layout)
        labelId.setBounds(15, 15, 100, 20);
        txtIdAnggota.setBounds(120, 15, 100, 20);

        labelNama.setBounds(15, 40, 100, 20);
        txtNama.setBounds(120, 40, 300, 20);
        
        labelAlamat.setBounds(15, 65, 100, 20);
        txtAlamat.setBounds(120, 65, 300, 20);
        
        labelTelepon.setBounds(15, 90, 100, 20);
        txtTelepon.setBounds(120, 90, 300, 20);

        // Posisi Tombol
        btnSimpan.setBounds(15, 125, 100, 30);
        btnTambahBaru.setBounds(125, 125, 110, 30);
        btnHapus.setBounds(245, 125, 100, 30);

        txtCari.setBounds(375, 130, 150, 25);
        btnCari.setBounds(535, 130, 60, 25);

        // Posisi Tabel
        jScrollPane1.setBounds(15, 170, 580, 235);

        // Menambahkan Komponen ke Frame
        getContentPane().add(labelId);
        getContentPane().add(txtIdAnggota);
        getContentPane().add(labelNama);
        getContentPane().add(txtNama);
        getContentPane().add(labelAlamat);
        getContentPane().add(txtAlamat);
        getContentPane().add(labelTelepon);
        getContentPane().add(txtTelepon);
        getContentPane().add(btnSimpan);
        getContentPane().add(btnTambahBaru);
        getContentPane().add(btnHapus);
        getContentPane().add(txtCari);
        getContentPane().add(btnCari);
        getContentPane().add(jScrollPane1);
        
        // --- Penambahan Event Listener ---

        btnSimpan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnTambahBaru.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnTambahBaruActionPerformed(evt);
            }
        });

        btnCari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        tblAnggota.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tblAnggotaMouseClicked(evt);
            }
        });

        setTitle("Form Data Anggota");
        setSize(620, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tampilkanData();
        kosongkanForm();
    }
    
    // --- Implementasi Method Event ---

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        Anggota ang = new Anggota();
        ang.setIdanggota(Integer.parseInt(txtIdAnggota.getText()));
        ang.setNama(txtNama.getText());
        ang.setAlamat(txtAlamat.getText());
        ang.setTelepon(txtTelepon.getText());
        ang.save();

        txtIdAnggota.setText(Integer.toString(ang.getIdanggota()));
        tampilkanData();
    }

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblAnggota.getModel();
        int row = tblAnggota.getSelectedRow();

        Anggota ang = new Anggota().getById(Integer.parseInt(model.getValueAt(row, 0).toString()));
        ang.delete();
        kosongkanForm();
        tampilkanData();
    }

    private void btnTambahBaruActionPerformed(java.awt.event.ActionEvent evt) {
        kosongkanForm();
    }

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {
        cari(txtCari.getText());
    }

    private void tblAnggotaMouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblAnggota.getModel();
        int row = tblAnggota.getSelectedRow();

        txtIdAnggota.setText(model.getValueAt(row, 0).toString());
        txtNama.setText(model.getValueAt(row, 1).toString());
        txtAlamat.setText(model.getValueAt(row, 2).toString());
        txtTelepon.setText(model.getValueAt(row, 3).toString());
    }

    public void kosongkanForm() {
        txtIdAnggota.setText("0");
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelepon.setText("");
    }

    public void tampilkanData() {
        String[] kolom = {"ID", "Nama", "Alamat", "Telepon"};
        ArrayList<Anggota> list = new Anggota().getAll();
        Object rowData[] = new Object[4]; // Array 4 untuk 4 kolom

        tblAnggota.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (Anggota ang : list) {
            rowData[0] = ang.getIdanggota();
            rowData[1] = ang.getNama();
            rowData[2] = ang.getAlamat();
            rowData[3] = ang.getTelepon();

            ((DefaultTableModel) tblAnggota.getModel()).addRow(rowData);
        }
    }

    public void cari(String keyword) {
        String[] kolom = {"ID", "Nama", "Alamat", "Telepon"};
        ArrayList<Anggota> list = new Anggota().search(keyword);
        Object rowData[] = new Object[4];

        tblAnggota.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (Anggota ang : list) {
            rowData[0] = ang.getIdanggota();
            rowData[1] = ang.getNama();
            rowData[2] = ang.getAlamat();
            rowData[3] = ang.getTelepon();

            ((DefaultTableModel) tblAnggota.getModel()).addRow(rowData);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAnggota().setVisible(true);
            }
        });
    }
}