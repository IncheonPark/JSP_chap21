package member.service;

import java.util.Map;

public class JoinRequest {
	
	private String id;
	private String name;
	private String password;
	private String confirmPassword;
	
	public boolean isPasswordEqualToConfirm() {
		return password != null && password.equals(confirmPassword);
	}
	
	public void validate( Map <String, Boolean> errors ) {
		checkEmpty(errors, id, "id");
		checkEmpty(errors, name, "name");
		checkEmpty(errors, password, "password");
		checkEmpty(errors, confirmPassword, "confirmPassword");
		
		if(!errors.containsKey("confirmPassword")) { //비밀번호 확인 키가 없고
			if(!isPasswordEqualToConfirm()) {	//비밀번호 확인이 일치하지 않을 때
				errors.put("notMatch", Boolean.TRUE); // 키 값으로 motMatch 넣음, 값은 TRUE
			}
		}
	}
	
	private void checkEmpty( Map <String, Boolean> errors, String value, String fieldName) {
		if (value == null || value.isEmpty())
			errors.put(fieldName, Boolean.TRUE);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	

}
