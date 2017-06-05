package br.com.goline2.model.entity;

public interface IUser {
	
	public boolean passwordsMatch(String password, String confirmPassword);

}
