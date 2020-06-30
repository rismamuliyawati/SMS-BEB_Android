package com.example.myapplication.model;

public class DataAbsenMapel_Model {
    String id,  nis,  id_kelas,  id_mapel,  nip,  jam, status_absen, tanggal, siswa;

    public String getId_Absenmapel() {
        return id;
    }

    public void setId_Absenmapel(String id) {
        this.id = id;
    }

    public String getNismapel() {
        return nis;
    }

    public void setNismapel(String nis) {
        this.nis = nis;
    }

    public String getId_kelasmapel() { return id_kelas; }

    public void setId_kelasmapel(String id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getId_mapel() {
        return id_mapel;
    }

    public void setId_mapel(String id_mapel) { this.id_mapel= id_mapel; }

    public String getNipmapel() { return nip; }

    public void setNipmapel(String nip) {
        this.nip = nip;
    }

    public String getJammapel() {
        return jam;
    }

    public void setJammapel(String jam) {
        this.jam = jam;
    }

    public String getStatus_absenmapel() {
        return status_absen;
    }

    public void setStatus_absenmapel(String status_absen) {
        this.status_absen = status_absen;
    }

    public String getTanggalmapel() { return tanggal; }

    public void setTanggalmapel(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getSiswamapel() {
        return siswa;
    }

    public void setSiswamapel(String siswa) {
        this.siswa = siswa;
    }
}
