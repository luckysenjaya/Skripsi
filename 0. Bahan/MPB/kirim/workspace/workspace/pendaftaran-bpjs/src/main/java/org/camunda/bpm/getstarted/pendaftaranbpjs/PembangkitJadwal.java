package org.camunda.bpm.getstarted.pendaftaranbpjs;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;

public class PembangkitJadwal implements JavaDelegate{
	public final static Logger LOGGER = Logger.getLogger("pembangkit-jadwal");
	public String jadwalKedatangan(Object hari){
		
		return "77 Juli 7777";
	}
	
	public String nomorAntrian(){
		return "1";
	}
	
	public void execute(DelegateExecution execution) throws Exception {
		execution.getVariable("jadwalHari");
		String jadwal = this.jadwalKedatangan(execution.getVariable("jadwalHari"));
		String nomor = this.nomorAntrian();
		
		
		
		
		
	      ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceById(processDefinition.getId(), createVariables()
	              .putValue("creditor", "Papa Steve's all you can eat")
	              .putValue("amount", 10.99d)
	              .putValue("invoiceCategory", "Travel Expenses")
	              .putValue("invoiceNumber", "PSACE-5342")
	              .putValue("invoiceDocument", fileValue("invoice.pdf")
	                  .file(invoiceInputStream)
	                  .mimeType("application/pdf")
	                  .create()));
		
		
		
		
		
		
		
		
		
		
		
		execution.setVariable("jadwal", jadwal);
		execution.setVariable("nomor", nomor);
		
		//execution.setVariable("uangDaftar", uangDaftar);
		//execution.setVariable("nomor", nomor);
		//pemohon.setKelas("Kelas 1");
		//pemohon.setNamaKlinik("Klinik Suka Sehat");
		LOGGER.info("memproses '"+execution.getVariable("jadwalHari")+"' a");// '"+pemohon.getNama() + "' dengan id ");
	}
}