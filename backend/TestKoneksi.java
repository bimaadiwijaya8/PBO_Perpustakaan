package backend;

import java.sql.ResultSet;

public class TestKoneksi {
    public static void main(String[] args) {

        // Coba buka koneksi
        DBHelper.bukaKoneksi();

        // Test query sederhana
        try {
            ResultSet rs = DBHelper.selectQuery("SELECT 1 AS tes");
            if (rs.next()) {
                System.out.println("✅ QUERY Berjalan! Hasil: " + rs.getInt("tes"));
                System.out.println("✅ Koneksi database PostgreSQL SUKSES!");
            }
        } catch (Exception e) {
            System.out.println("❌ QUERY Gagal!");
            e.printStackTrace();
        }
    }
}
