package com.example.myapplication.model;

public class Pembayaran_Model {
    String id,  nis,  tanggal,  jml_tagihan, status, jenis_pembayaran, deskripsi;

    public String getId_Pembayaran() {
        return id;
    }

    public void setId_Pembayaran(String id) {
        this.id = id;
    }


    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }


    public String getTanggal_Pembayaran() {
        return tanggal;
    }

    public void setTanggal_Pembayaran(String tanggal) {
        this.tanggal = tanggal;
    }


    public String getJml_tagihan() { return jml_tagihan; }

    public void setJml_tagihan(String jml_tagihan) {
        this.jml_tagihan = jml_tagihan;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getJenis_Pembayaran() { return jenis_pembayaran; }

    public void setJenis_Pembayaran(String jenis_pembayaran) { this.jenis_pembayaran = jenis_pembayaran; }


    public String getDeskripsi_Pembayaran() { return deskripsi; }

    public void setDeskripsi_Pembayaran(String deskripsi) { this.deskripsi = deskripsi; }
}
