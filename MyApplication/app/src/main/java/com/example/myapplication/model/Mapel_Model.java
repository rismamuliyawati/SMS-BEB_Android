package com.example.myapplication.model;

public class Mapel_Model {
    String id,  nis,  id_kelas,  id_mapel,  nip,  guru, mapel, jam, status_absen, tanggal;

    public String getId_Absenmapel() {
        return id;
    }

    public void setId_Absenmapel(String id) {
        this.id = id;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getId_kelasmapel() {
        return id_kelas;
    }

    public void setId_kelasmapel(String id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getId_mapel() { return id_mapel; }

    public void setId_mapel(String id_mapel) {
        this.id_mapel = id_mapel;
    }

    public String getNipmapel() { return nip; }

    public void setNipmapel(String nip) { this.nip = nip; }

    public String getNama_guru() { return guru; }

    public void setNama_guru(String guru) { this.guru = guru; }

    public String getNama_mapel() { return mapel; }

    public void setNama_mapel(String mapel) { this.mapel = mapel; }

    public String getJam_mapel() {
        return jam;
    }

    public void setJam_mapel(String jam) {
        this.jam = jam;
    }

    public String getStatus_absenmapel() {
        return status_absen;
    }

    public void setStatus_absenmapel(String status_absen) { this.status_absen = status_absen; }

    public String getTanggal_mapel() {
        return tanggal;
    }

    public void setTanggal_mapel(String tanggal) {
        this.tanggal = tanggal;
    }
}
