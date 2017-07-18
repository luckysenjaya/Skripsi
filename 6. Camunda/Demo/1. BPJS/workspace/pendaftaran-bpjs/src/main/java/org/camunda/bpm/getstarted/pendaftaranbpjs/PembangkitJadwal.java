package org.camunda.bpm.getstarted.pendaftaranbpjs;

import java.util.Random;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;

public class PembangkitJadwal implements JavaDelegate{
	public final static Logger LOGGER = Logger.getLogger("pembangkit-jadwal");
	public String jadwalKedatangan(Object hari){
		
		return hari+"";
	}
	
	public int nomorAntrian(){
		Random rand = new Random();
		int nomor = rand.nextInt(10);
		return nomor;
	}
	
	public void execute(DelegateExecution execution) throws Exception {
		execution.getVariable("jadwalHari");
		String jadwal = this.jadwalKedatangan(execution.getVariable("jadwalHari"));
		int nomor = this.nomorAntrian();

		execution.setVariable("jadwal", jadwal);
		execution.setVariable("nomor", nomor);

	}
}
