package com.example.myapplication.model;

public class Sekolah_Model {
    String id,  nis,  tanggal,  status_absen, id_kelas, type, jam;

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getId_Sekolah() {
        return id;
    }

    public void setId_Sekolah(String id) {
        this.id = id;
    }


    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }


    public String getTanggal_Sekolah() {
        return tanggal;
    }

    public void setTanggal_Sekolah(String tanggal) {
        this.tanggal = tanggal;
    }


    public String getStatus_absen() { return status_absen; }

    public void setStatus_absen(String status_absen) {
        this.status_absen = status_absen;
    }


    public String getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(String id_kelas) {
        this.id_kelas = id_kelas;
    }


    public String getType_kelas() { return type; }

    public void setType_kelas(String type) { this.type = type; }

}
