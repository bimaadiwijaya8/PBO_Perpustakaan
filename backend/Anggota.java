package backend;

import java.util.ArrayList;
import java.sql.*;

/**
 * Kelas Anggota: Merepresentasikan Model data untuk entitas Anggota
 * dan menangani logika bisnis (CRUD) ke tabel 'anggota'.
 */
public class Anggota {

    // 1. ATRIBUT: Sesuai dengan kolom di tabel 'anggota'
    private int idanggota;
    private String nama;
    private String alamat;
    private String telepon;

    // 2. KONSTRUKTOR KOSONG: Digunakan saat mengambil data dari database (ResultSet)
    public Anggota() {
    }

    // 3. KONSTRUKTOR BERISI: Digunakan saat membuat objek baru sebelum disimpan ke database
    public Anggota(String nama, String alamat, String telepon) {
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
    }

    // 4. GETTER & SETTER (ENKAPSULASI)
    public int getIdanggota() {
        return idanggota;
    }

    public void setIdanggota(int idanggota) {
        this.idanggota = idanggota;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    // 5. METHOD GET BY ID (READ - SATUAN)
    public Anggota getById(int id) {
        Anggota ang = new Anggota();
        
        // PERHATIAN: Query ini rentan terhadap SQL Injection karena menggunakan String Concatenation.
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM anggota "
                                          + " WHERE idanggota = '" + id + "'");
        try {
            // Karena ID unik, loop hanya akan berjalan maksimal 1 kali
            while (rs.next()) {
                // Catatan: ang = new Anggota() di dalam loop tidak efisien
                ang = new Anggota(); 
                ang.setIdanggota(rs.getInt("idanggota"));
                ang.setNama(rs.getString("nama"));
                ang.setAlamat(rs.getString("alamat"));
                ang.setTelepon(rs.getString("telepon"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ang;
    }

    // 6. METHOD GET ALL (READ - SEMUA)
    public ArrayList<Anggota> getAll() {
        ArrayList<Anggota> ListAnggota = new ArrayList();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM anggota");
        try {
            // Looping untuk setiap baris hasil query (ResultSet)
            while (rs.next()) {
                Anggota ang = new Anggota();
                ang.setIdanggota(rs.getInt("idanggota"));
                ang.setNama(rs.getString("nama"));
                ang.setAlamat(rs.getString("alamat"));
                ang.setTelepon(rs.getString("telepon"));
                ListAnggota.add(ang); // Menambahkan objek ke list
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListAnggota;
    }

    // 7. METHOD SEARCH (READ - PENCARIAN)
    public ArrayList<Anggota> search(String keyword) {
        ArrayList<Anggota> ListAnggota = new ArrayList();
        // Query menggunakan operator LIKE untuk mencari keyword di 3 kolom sekaligus
        // PERHATIAN: Query ini juga rentan terhadap SQL Injection
        String sql = "SELECT * FROM anggota WHERE "
                    + "      nama LIKE '%" + keyword + "%' "
                    + "      OR alamat LIKE '%" + keyword + "%' "
                    + "      OR telepon LIKE '%" + keyword + "%' ";
        ResultSet rs = DBHelper.selectQuery(sql);
        try {
            while (rs.next()) {
                Anggota ang = new Anggota();
                ang.setIdanggota(rs.getInt("idanggota"));
                ang.setNama(rs.getString("nama"));
                ang.setAlamat(rs.getString("alamat"));
                ang.setTelepon(rs.getString("telepon"));
                ListAnggota.add(ang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListAnggota;
    }

    // 8. METHOD SAVE (CREATE/UPDATE)
    public void save() {
        // Cek apakah ID sudah ada di database (UPDATE) atau belum (INSERT)
        if (getById(idanggota).getIdanggota() == 0) { 
            // Blok INSERT: Data baru
            String SQL = "INSERT INTO anggota (nama, alamat, telepon) VALUES("
                        + "      '" + this.nama + "', "
                        + "      '" + this.alamat + "', "
                        + "      '" + this.telepon + "' "
                        + "      )";
            // Eksekusi INSERT dan ambil ID yang baru di-generate
            this.idanggota = DBHelper.insertQueryGetId(SQL); 
        } else {
            // Blok UPDATE: Modifikasi data lama
            String SQL = "UPDATE anggota SET "
                        + "      nama = '" + this.nama + "', "
                        + "      alamat = '" + this.alamat + "', "
                        + "      telepon = '" + this.telepon + "' "
                        + "      WHERE idanggota = '" + this.idanggota + "'";
            // Eksekusi UPDATE
            DBHelper.executeQuery(SQL); 
        }
        // PERHATIAN: Semua query INSERT/UPDATE di sini rentan terhadap SQL Injection
    }

    // 9. METHOD DELETE (DELETE)
    public void delete() {
        String SQL = "DELETE FROM anggota WHERE idanggota = '" + this.idanggota + "'";
        DBHelper.executeQuery(SQL); // Eksekusi DELETE
    }
}