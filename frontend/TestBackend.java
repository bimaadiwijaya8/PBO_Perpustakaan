package frontend;

import backend.*; // 1. Tambahkan import backend.* [cite: 804, 806]

public class TestBackend {
    public static void main(String[] args) { // [cite: 808]
        // Membuat objek Kategori
        Kategori kat1 = new Kategori("Novel", "Koleksi buku novel"); // [cite: 809, 810]
        Kategori kat2 = new Kategori("Referensi", "Buku referensi ilmiah"); // [cite: 811, 812]
        Kategori kat3 = new Kategori("Komik", "Komik anak-anak"); // [cite: 813, 814]

        // test insert
        kat1.save(); // [cite: 815, 816]
        kat2.save(); // [cite: 817]
        kat3.save(); // [cite: 818]

        // test update
        kat2.setKeterangan("Koleksi buku referensi ilmiah"); // [cite: 820]
        kat2.save(); // [cite: 819, 821]

        // test delete
        kat3.delete(); // [cite: 822, 823]

        // test select all
        for (Kategori k : new Kategori().getAll()) { // [cite: 824, 825]
            System.out.println("Nama: " + k.getNama() + ", Ket: " + k.getKeterangan()); // [cite: 828]
        }

        // test search
        for (Kategori k : new Kategori().search("ilmiah")) { // [cite: 829, 830]
            System.out.println("Nama: " + k.getNama() + ", Ket: " + k.getKeterangan()); // [cite: 833]
        }
    }
}