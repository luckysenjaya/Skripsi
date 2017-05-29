package org.camunda.bpm.getstarted.pendaftaranbpjs;

public class Pemohon {
	
	private String nama;
	private String tanggalLahir;
	private String nik;
	private String kelas;
	private String namaKlinik;
	
	
	public String getNama() {
		return nama;
	}
	public void setNama(Object nama) {
		this.nama = (String) nama;
	}
	
	public String getTanggalLahir() {
		return tanggalLahir;
	}
	public void setTanggalLahir(Object tanggalLahir) {
		this.tanggalLahir = (String) tanggalLahir;
	}
	
	public String getNik() {
		return nik;
	}
	public void setNik(Object nik) {
		this.nik = (String) nik;
	}
	
	public String getKelas() {
		return kelas;
	}
	public void setKelas(Object kelas) {
		this.kelas = (String) kelas;
	}
	
	public String getNamaKlinik() {
		return namaKlinik;
	}
	public void setNamaKlinik(Object namaKlinik) {
		this.namaKlinik = (String) namaKlinik;
	}
	
	

}
