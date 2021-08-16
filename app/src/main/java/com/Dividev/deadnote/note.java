package com.Dividev.deadnote;

public class note {
    private String key;
    private String kegiatan;
    private String status;
    private String tanggal;

    public note(){

    }

    public note(String kegiatan, String status, String tanggal) {
        this.kegiatan = kegiatan;
        this.status = status;
        this.tanggal = tanggal;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
