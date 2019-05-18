package com.ab.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.ab.model.IBank;
import com.ab.model.OBank;

@Component
public class BankItemProcessor implements ItemProcessor<IBank, OBank> {

	@Override
	public OBank process(IBank item) throws Exception {
           OBank oBank=null;
		 if(item.getIfsCode().length()>9) {
			oBank=new OBank();
			oBank.setBankId(item.getBankId());
			oBank.setBankName(item.getBankName());
			oBank.setIfsCode(item.getIfsCode());
			oBank.setEstYear(item.getEstYear());
			return oBank;
		 }
		 else
    		return null;
	}

}
