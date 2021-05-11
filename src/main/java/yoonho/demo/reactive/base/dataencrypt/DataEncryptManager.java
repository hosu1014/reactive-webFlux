package yoonho.demo.reactive.base.dataencrypt;

public interface DataEncryptManager {
	public String encryptCardNo(String cartNo);
	public String decryptCardNo(String encCardNo);
	
	public String encryptAccountNo(String acntNo);
	public String decryptAccountNo(String encAcntNo);
}
