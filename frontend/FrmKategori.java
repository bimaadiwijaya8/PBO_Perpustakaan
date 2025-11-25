package frontend;

import backend.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.event.*;

public class FrmKategori extends javax.swing.JFrame {
    
    // ATRIBUT UNTUK KOMPONEN BARU
    private JTextField txtIdKategori;
    private JTextField txtNama;
    private JTextField txtKeterangan; // BARU: Field input Keterangan
    private JButton btnSimpan;
    private JButton btnHapus;
    private JButton btnTambahBaru;
    private JTextField txtCari;
    private JButton btnCari;
    private JTable tblKategori;
    private JScrollPane jScrollPane1;

    private JLabel labelId;
    private JLabel labelNama;
    private JLabel labelKeterangan; // BARU: Label Keterangan

    public FrmKategori() {
        // INISIALISASI
        txtIdKategori = new JTextField();
        txtNama = new JTextField();
        txtKeterangan = new JTextField(); // BARU
        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnTambahBaru = new JButton("Tambah Baru");
        txtCari = new JTextField();
        btnCari = new JButton("Cari");
        tblKategori = new JTable();
        jScrollPane1 = new JScrollPane();

        labelId = new JLabel("ID Kategori");
        labelNama = new JLabel("Nama Kategori");
        labelKeterangan = new JLabel("Keterangan"); // BARU

        txtIdKategori.setText("0");
        txtIdKategori.setEnabled(false);

        // PENYESUAIAN KOLOM TABEL (3 Kolom)
        tblKategori.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Kategori", "Keterangan"} // Kolom 3: Keterangan
        ));
        jScrollPane1.setViewportView(tblKategori);

        getContentPane().setLayout(null);

        // PENGATURAN POSISI
        labelId.setBounds(15, 15, 100, 20);
        txtIdKategori.setBounds(120, 15, 100, 20);

        labelNama.setBounds(15, 40, 100, 20);
        txtNama.setBounds(120, 40, 300, 20);
        
        // POSISI KETERANGAN (BARU)
        labelKeterangan.setBounds(15, 65, 100, 20);
        txtKeterangan.setBounds(120, 65, 300, 20);

        // POSISI TOMBOL (DIGESER KE BAWAH)
        btnSimpan.setBounds(15, 100, 100, 30);
        btnTambahBaru.setBounds(125, 100, 110, 30);
        btnHapus.setBounds(245, 100, 100, 30);

        txtCari.setBounds(375, 105, 150, 25);
        btnCari.setBounds(535, 105, 60, 25);

        // POSISI TABEL (DIGESER KE BAWAH)
        jScrollPane1.setBounds(15, 140, 580, 235);

        // MENAMBAHKAN KOMPONEN BARU KE LAYOUT
        getContentPane().add(labelId);
        getContentPane().add(txtIdKategori);
        getContentPane().add(labelNama);
        getContentPane().add(txtNama);
        getContentPane().add(labelKeterangan); // BARU
        getContentPane().add(txtKeterangan); // BARU
        getContentPane().add(btnSimpan);
        getContentPane().add(btnTambahBaru);
        getContentPane().add(btnHapus);
        getContentPane().add(txtCari);
        getContentPane().add(btnCari);
        getContentPane().add(jScrollPane1);
        
        // PENGATURAN ACTION LISTENER (Tidak Berubah)
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

        tblKategori.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tblKategoriMouseClicked(evt);
            }
        });

        setTitle("Form Kategori");
        setSize(620, 440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tampilkanData();
        kosongkanForm();
    }
    
    // --- METHOD LOGIKA DIPERBAIKI ---

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        Kategori kat = new Kategori();
        kat.setIdkategori(Integer.parseInt(txtIdKategori.getText()));
        kat.setNama(txtNama.getText());
        kat.setKeterangan(txtKeterangan.getText()); // DIPERBAIKI: Mengambil nilai dari field Keterangan
        kat.save();

        txtIdKategori.setText(Integer.toString(kat.getIdkategori()));
        tampilkanData();
    }

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblKategori.getModel();
        int row = tblKategori.getSelectedRow();

        Kategori kat = new Kategori().getById(Integer.parseInt(model.getValueAt(row, 0).toString()));
        kat.delete();
        kosongkanForm();
        tampilkanData();
    }

    private void btnTambahBaruActionPerformed(java.awt.event.ActionEvent evt) {
        kosongkanForm();
    }

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {
        cari(txtCari.getText());
    }

    private void tblKategoriMouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblKategori.getModel();
        int row = tblKategori.getSelectedRow();

        txtIdKategori.setText(model.getValueAt(row, 0).toString());
        txtNama.setText(model.getValueAt(row, 1).toString());
        txtKeterangan.setText(model.getValueAt(row, 2).toString()); // DIPERBAIKI: Mengisi field Keterangan
    }

    public void kosongkanForm() {
        txtIdKategori.setText("0");
        txtNama.setText("");
        txtKeterangan.setText(""); // DIPERBAIKI: Mengosongkan field Keterangan
    }

    public void tampilkanData() {
        String[] kolom = {"ID", "Kategori", "Keterangan"}; // DIPERBAIKI: Tambah kolom Keterangan
        ArrayList<Kategori> list = new Kategori().getAll();
        Object rowData[] = new Object[3]; // DIPERBAIKI: Ukuran array 3

        tblKategori.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (Kategori kat : list) {
            rowData[0] = kat.getIdkategori();
            rowData[1] = kat.getNama();
            rowData[2] = kat.getKeterangan(); // DIPERBAIKI: Menambahkan data Keterangan ke tabel

            ((DefaultTableModel) tblKategori.getModel()).addRow(rowData);
        }
    }

    public void cari(String keyword) {
        String[] kolom = {"ID", "Kategori", "Keterangan"}; // DIPERBAIKI: Tambah kolom Keterangan
        ArrayList<Kategori> list = new Kategori().search(keyword);
        Object rowData[] = new Object[3]; // DIPERBAIKI: Ukuran array 3

        tblKategori.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (Kategori kat : list) {
            rowData[0] = kat.getIdkategori();
            rowData[1] = kat.getNama();
            rowData[2] = kat.getKeterangan(); // DIPERBAIKI: Menambahkan data Keterangan ke tabel

            ((DefaultTableModel) tblKategori.getModel()).addRow(rowData);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmKategori().setVisible(true);
            }
        });
    }
}

