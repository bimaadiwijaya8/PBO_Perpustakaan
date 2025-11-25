package backend;

import java.util.ArrayList;
import java.sql.*;

public class Buku {
    // 1. ATRIBUT: Termasuk Objek Kategori untuk relasi (Foreign Key)
    private int idbuku;
    private Kategori kategori = new Kategori(); // Relasi ke Objek Kategori
    private String judul;
    private String penerbit;
    private String penulis;
    
    // Asumsi: Tambahkan atribut stok (sesuai kebutuhan perpustakaan)
    private int stok;

    // 2. KONSTRUKTOR
    public Buku() {
    }

    public Buku(Kategori kategori, String judul, String penerbit, String penulis, int stok) {
        this.kategori = kategori;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
        this.stok = stok;
    }

    // 3. GETTER & SETTER
    // ... (Tambahkan semua Getter & Setter di sini) ...
    public int getIdbuku() {
        return idbuku;
    }

    public void setIdbuku(int idbuku) {
        this.idbuku = idbuku;
    }

    // Getter/Setter untuk Objek Kategori
    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }
    
    // ... (Getter/Setter untuk Judul, Penerbit, Penulis, Stok) ...
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }


    // --- 4. METHOD CRUD DENGAN JOIN (Implementasi poin b) ---
    
    // Memformat hasil ResultSet menjadi objek Buku, termasuk mengisi objek Kategori
    public Buku getBuku(ResultSet rs) {
        Buku buku = new Buku();
        try {
            buku.setIdbuku(rs.getInt("idbuku"));
            buku.getKategori().setIdkategori(rs.getInt("idkategori")); // Mengambil FK
            buku.getKategori().setNama(rs.getString("nama_kategori")); // Mengambil data JOIN
            buku.setJudul(rs.getString("judul"));
            buku.setPenerbit(rs.getString("penerbit"));
            buku.setPenulis(rs.getString("penulis"));
            buku.setStok(rs.getInt("stok"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buku;
    }

    // A. GET BY ID
    public Buku getById(int id) {
        Buku buku = new Buku();
        // Query dengan JOIN untuk mengambil Nama Kategori
        String sql = "SELECT b.idbuku, b.idkategori, k.nama AS nama_kategori, "
                   + "b.judul, b.penerbit, b.penulis, b.stok "
                   + "FROM buku b LEFT JOIN kategori k ON b.idkategori = k.idkategori "
                   + "WHERE b.idbuku = '" + id + "'";
        
        ResultSet rs = DBHelper.selectQuery(sql);
        try {
            while (rs.next()) {
                buku = getBuku(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buku;
    }

    // B. GET ALL
    public ArrayList<Buku> getAll() {
        ArrayList<Buku> ListBuku = new ArrayList();
        // Query dengan JOIN untuk mengambil Nama Kategori
        String sql = "SELECT b.idbuku, b.idkategori, k.nama AS nama_kategori, "
                   + "b.judul, b.penerbit, b.penulis, b.stok "
                   + "FROM buku b LEFT JOIN kategori k ON b.idkategori = k.idkategori";
        
        ResultSet rs = DBHelper.selectQuery(sql);
        try {
            while (rs.next()) {
                ListBuku.add(getBuku(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListBuku;
    }

    // C. SEARCH
    public ArrayList<Buku> search(String keyword) {
        ArrayList<Buku> ListBuku = new ArrayList();
        // Query dengan JOIN, mencari keyword di kolom Judul, Penulis, atau Nama Kategori
        String sql = "SELECT b.idbuku, b.idkategori, k.nama AS nama_kategori, "
                   + "b.judul, b.penerbit, b.penulis, b.stok "
                   + "FROM buku b LEFT JOIN kategori k ON b.idkategori = k.idkategori "
                   + "WHERE b.judul LIKE '%" + keyword + "%' "
                   + "      OR b.penulis LIKE '%" + keyword + "%' "
                   + "      OR k.nama LIKE '%" + keyword + "%'";
        
        ResultSet rs = DBHelper.selectQuery(sql);
        try {
            while (rs.next()) {
                ListBuku.add(getBuku(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListBuku;
    }

    // --- 5. METHOD SAVE & DELETE (Implementasi poin a) ---

    // A. SAVE
    public void save() {
        // PERHATIAN: Memanggil getKategori().getIdkategori() untuk mendapatkan FK
        if (getById(idbuku).getIdbuku() == 0) { 
            // INSERT
            String SQL = "INSERT INTO buku (idkategori, judul, penerbit, penulis, stok) VALUES("
                        + "      '" + this.getKategori().getIdkategori() + "', " // Mengambil FK Kategori
                        + "      '" + this.judul + "', "
                        + "      '" + this.penerbit + "', "
                        + "      '" + this.penulis + "', "
                        + "      '" + this.stok + "' "
                        + "      )";
            this.idbuku = DBHelper.insertQueryGetId(SQL);
        } else {
            // UPDATE
            String SQL = "UPDATE buku SET "
                        + "      idkategori = '" + this.getKategori().getIdkategori() + "', " // Mengambil FK Kategori
                        + "      judul = '" + this.judul + "', "
                        + "      penerbit = '" + this.penerbit + "', "
                        + "      penulis = '" + this.penulis + "', "
                        + "      stok = '" + this.stok + "' "
                        + "      WHERE idbuku = '" + this.idbuku + "'";
            DBHelper.executeQuery(SQL);
        }
    }

    // B. DELETE
    public void delete() {
        String SQL = "DELETE FROM buku WHERE idbuku = '" + this.idbuku + "'";
        DBHelper.executeQuery(SQL);
    }
}