package org.camunda.bpm.getstarted.pendaftaranbpjs;

import java.util.Random;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class PembangkitNomor implements JavaDelegate{
	public final static Logger LOGGER = Logger.getLogger("pendaftaran-bpjs");
	int nomor;
	int uangDaftar;
	public int nomorPembayaran(){
		
		Random rand = new Random();
		nomor = rand.nextInt();
		
		return nomor;
	}
	
	public int uangPendaftaran(){
		uangDaftar = 5000;
		return uangDaftar;
	}

	public void execute(DelegateExecution execution) throws Exception {
		Pemohon pemohon = new Pemohon();
		pemohon.setNama(execution.getVariable("nama"));
		pemohon.setNik(execution.getVariable("nik"));
		pemohon.setTanggalLahir(execution.getVariable("tanggalLahir"));
		pemohon.setKelas(execution.getVariable("kelas"));
		pemohon.setNamaKlinik(execution.getVariable("namaKlinik"));
		this.nomorPembayaran();
		this.uangPendaftaran();
		execution.setVariable("uangDaftar", uangDaftar);
		execution.setVariable("nomor", nomor);
		//pemohon.setKelas("Kelas 1");
		//pemohon.setNamaKlinik("Klinik Suka Sehat");
		//LOGGER.info("memproses '"+execution.getVariable("nama") +"' a");// '"+pemohon.getNama() + "' dengan id ");
	}

}
