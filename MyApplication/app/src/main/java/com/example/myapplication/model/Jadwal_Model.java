package com.example.myapplication.model;

public class Jadwal_Model {
    String id,  nip,  id_kelas,  id_mapel,  hari,  jam, guru, kelas, mapel;

    public String getId_jadwal() {
        return id;
    }

    public void setId_jadwal(String id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(String id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getId_mapel() {
        return id_mapel;
    }

    public void setId_mapel(String id_mapel) { this.id_mapel = id_mapel; }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getGuru() {
        return guru;
    }

    public void setGuru(String guru) {
        this.guru = guru;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) { this.mapel = mapel; }
}
