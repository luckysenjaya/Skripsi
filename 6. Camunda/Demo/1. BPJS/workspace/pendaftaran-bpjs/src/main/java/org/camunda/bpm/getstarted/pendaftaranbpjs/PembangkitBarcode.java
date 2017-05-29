package org.camunda.bpm.getstarted.pendaftaranbpjs;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class PembangkitBarcode implements JavaDelegate{

	public int getBarcode(){
		return 12345;
	}
	
	public void execute(DelegateExecution execution) throws Exception {
		String barcode = "b" + this.getBarcode();
		execution.setVariable("barcode", barcode);
	}
}
