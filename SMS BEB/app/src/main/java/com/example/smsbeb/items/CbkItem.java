package com.example.smsbeb.items;

public class CbkItem {
    private String id;
    private String nama;
    private String gambar;
    private String nis;
    private String point;
    private String ket;

    public CbkItem(String id, String nama, String gambar, String nis, String point, String ket) {
        this.id = id;
        this.nama = nama;
        this.gambar = gambar;
        this.nis = nis;
        this.point = point;
        this.ket = ket;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getGambar() {
        return gambar;
    }

    public String getNis() {
        return nis;
    }

    public String getPoint() {
        return point;
    }

    public String getKet() {
        return ket;
    }
}
