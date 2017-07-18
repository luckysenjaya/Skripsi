package org.camunda.bpm.getstarted.pendaftaranbpjs;

import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class PembangkitBarcode implements JavaDelegate{

	public int getBarcode(){
		Random rand = new Random();
		int nomor = rand.nextInt(10000000)+100000;
		return nomor;
	}
	
	public void execute(DelegateExecution execution) throws Exception {
		String barcode = ""+this.getBarcode();
		execution.setVariable("barcode", barcode);
	}
}
