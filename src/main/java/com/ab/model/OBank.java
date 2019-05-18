package com.ab.model;

public class OBank {
	private int bankId;
	private String bankName;
	private String ifsCode;
	private int estYear;

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIfsCode() {
		return ifsCode;
	}

	public void setIfsCode(String ifsCode) {
		this.ifsCode = ifsCode;
	}

	public int getEstYear() {
		return estYear;
	}

	public void setEstYear(int estYear) {
		this.estYear = estYear;
	}

	@Override
	public String toString() {
		return "Bank [bankId=" + bankId + ", bankName=" + bankName + ", ifsCode=" + ifsCode + ", estYear=" + estYear
				+ "]";
	}

}
