package frontend;

import backend.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.event.*;

public class FrmBuku extends javax.swing.JFrame {

    // Deklarasi Komponen
    private JTextField txtIdBuku;
    private JComboBox<Kategori> cmbKategori; // Menggunakan JComboBox untuk memilih Kategori
    private JTextField txtJudul;
    private JTextField txtPenerbit;
    private JTextField txtPenulis;
    private JTextField txtStok;
    private JButton btnSimpan;
    private JButton btnHapus;
    private JButton btnTambahBaru;
    private JTextField txtCari;
    private JButton btnCari;
    private JTable tblBuku;
    private JScrollPane jScrollPane1;

    // Label
    private JLabel labelId;
    private JLabel labelKategori;
    private JLabel labelJudul;
    private JLabel labelPenerbit;
    private JLabel labelPenulis;
    private JLabel labelStok;

    public FrmBuku() {
        // Inisialisasi Komponen
        txtIdBuku = new JTextField();
        cmbKategori = new JComboBox<>(); // Inisialisasi ComboBox
        txtJudul = new JTextField();
        txtPenerbit = new JTextField();
        txtPenulis = new JTextField();
        txtStok = new JTextField();
        btnSimpan = new JButton("Simpan");
        btnHapus = new JButton("Hapus");
        btnTambahBaru = new JButton("Tambah Baru");
        txtCari = new JTextField();
        btnCari = new JButton("Cari");
        tblBuku = new JTable();
        jScrollPane1 = new JScrollPane();

        labelId = new JLabel("ID Buku");
        labelKategori = new JLabel("Kategori");
        labelJudul = new JLabel("Judul");
        labelPenerbit = new JLabel("Penerbit");
        labelPenulis = new JLabel("Penulis");
        labelStok = new JLabel("Stok");

        txtIdBuku.setText("0");
        txtIdBuku.setEnabled(false);

        // Definisi Kolom Tabel
        tblBuku.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Kategori", "Judul", "Penulis", "Penerbit", "Stok"}
        ));
        jScrollPane1.setViewportView(tblBuku);

        getContentPane().setLayout(null);

        // Pengaturan Posisi (Layout)
        int y = 15;
        labelId.setBounds(15, y, 100, 20);
        txtIdBuku.setBounds(120, y, 100, 20);
        y += 25;
        
        labelKategori.setBounds(15, y, 100, 20);
        cmbKategori.setBounds(120, y, 300, 20);
        y += 25;
        
        labelJudul.setBounds(15, y, 100, 20);
        txtJudul.setBounds(120, y, 300, 20);
        y += 25;
        
        labelPenulis.setBounds(15, y, 100, 20);
        txtPenulis.setBounds(120, y, 300, 20);
        y += 25;
        
        labelPenerbit.setBounds(15, y, 100, 20);
        txtPenerbit.setBounds(120, y, 300, 20);
        y += 25;
        
        labelStok.setBounds(15, y, 100, 20);
        txtStok.setBounds(120, y, 300, 20);
        y += 35;

        // Posisi Tombol
        btnSimpan.setBounds(15, y, 100, 30);
        btnTambahBaru.setBounds(125, y, 110, 30);
        btnHapus.setBounds(245, y, 100, 30);

        txtCari.setBounds(375, y + 5, 150, 25);
        btnCari.setBounds(535, y + 5, 60, 25);
        y += 40;

        // Posisi Tabel
        jScrollPane1.setBounds(15, y, 580, 200);

        // Menambahkan Komponen ke Frame
        getContentPane().add(labelId);
        getContentPane().add(txtIdBuku);
        getContentPane().add(labelKategori);
        getContentPane().add(cmbKategori);
        getContentPane().add(labelJudul);
        getContentPane().add(txtJudul);
        getContentPane().add(labelPenulis);
        getContentPane().add(txtPenulis);
        getContentPane().add(labelPenerbit);
        getContentPane().add(txtPenerbit);
        getContentPane().add(labelStok);
        getContentPane().add(txtStok);
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

        // ... (Tambahkan listener untuk Hapus, Tambah Baru, Cari) ...
        btnHapus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        
        btnTambahBaru.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                kosongkanForm();
            }
        });
        
        btnCari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cari(txtCari.getText());
            }
        });


        tblBuku.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tblBukuMouseClicked(evt);
            }
        });

        setTitle("Form Data Buku");
        setSize(620, 500); // Sesuaikan ukuran
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tampilkanData();
        kosongkanForm();
        tampilkanCmbKategori();
    }
    
    // --- METHOD LOGIKA ---

    // Mengisi JComboBox dengan data dari Kategori
    public void tampilkanCmbKategori() {
        cmbKategori.removeAllItems(); // Bersihkan item lama
        for (Kategori k : new Kategori().getAll()) {
            cmbKategori.addItem(k);
        }
    }
    
    public void kosongkanForm() {
        txtIdBuku.setText("0");
        tampilkanCmbKategori(); // Refresh combobox
        txtJudul.setText("");
        txtPenerbit.setText("");
        txtPenulis.setText("");
        txtStok.setText("");
    }

    public void tampilkanData() {
        String[] kolom = {"ID", "Kategori", "Judul", "Penulis", "Penerbit", "Stok"};
        ArrayList<Buku> list = new Buku().getAll();
        Object rowData[] = new Object[6]; 

        tblBuku.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (Buku b : list) {
            rowData[0] = b.getIdbuku();
            rowData[1] = b.getKategori().getNama(); // Mengambil Nama Kategori dari objek relasi
            rowData[2] = b.getJudul();
            rowData[3] = b.getPenulis();
            rowData[4] = b.getPenerbit();
            rowData[5] = b.getStok();

            ((DefaultTableModel) tblBuku.getModel()).addRow(rowData);
        }
    }

    public void cari(String keyword) {
        String[] kolom = {"ID", "Kategori", "Judul", "Penulis", "Penerbit", "Stok"};
        ArrayList<Buku> list = new Buku().search(keyword);
        Object rowData[] = new Object[6];

        tblBuku.setModel(new DefaultTableModel(new Object[][] {}, kolom));

        for (Buku b : list) {
            rowData[0] = b.getIdbuku();
            rowData[1] = b.getKategori().getNama();
            rowData[2] = b.getJudul();
            rowData[3] = b.getPenulis();
            rowData[4] = b.getPenerbit();
            rowData[5] = b.getStok();

            ((DefaultTableModel) tblBuku.getModel()).addRow(rowData);
        }
    }

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        Buku b = new Buku();
        b.setIdbuku(Integer.parseInt(txtIdBuku.getText()));
        // Mengambil objek Kategori yang dipilih dari ComboBox
        b.setKategori((Kategori)cmbKategori.getSelectedItem()); 
        
        b.setJudul(txtJudul.getText());
        b.setPenerbit(txtPenerbit.getText());
        b.setPenulis(txtPenulis.getText());
        b.setStok(Integer.parseInt(txtStok.getText()));
        
        b.save();

        txtIdBuku.setText(Integer.toString(b.getIdbuku()));
        tampilkanData();
    }

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblBuku.getModel();
        int row = tblBuku.getSelectedRow();

        // Mengambil ID Buku dari kolom 0
        Buku buku = new Buku().getById(Integer.parseInt(model.getValueAt(row, 0).toString()));
        buku.delete();
        kosongkanForm();
        tampilkanData();
    }

    private void tblBukuMouseClicked(java.awt.event.MouseEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblBuku.getModel();
        int row = tblBuku.getSelectedRow();
        
        // Ambil ID Buku dari tabel
        String idBuku = model.getValueAt(row, 0).toString();
        
        // Ambil Objek Buku lengkap dari database (termasuk FK Kategori)
        Buku buku = new Buku().getById(Integer.parseInt(idBuku));

        // Isi Field
        txtIdBuku.setText(String.valueOf(buku.getIdbuku()));
        txtJudul.setText(buku.getJudul());
        txtPenulis.setText(buku.getPenulis());
        txtPenerbit.setText(buku.getPenerbit());
        txtStok.setText(String.valueOf(buku.getStok()));
        
        // Set ComboBox: Mencari item Kategori yang cocok dengan buku.getKategori().getIdkategori()
        for(int i=0; i < cmbKategori.getItemCount(); i++){
            Kategori k = cmbKategori.getItemAt(i);
            if(k.getIdkategori() == buku.getKategori().getIdkategori()){
                cmbKategori.setSelectedIndex(i);
                break;
            }
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmBuku().setVisible(true);
            }
        });
    }
}