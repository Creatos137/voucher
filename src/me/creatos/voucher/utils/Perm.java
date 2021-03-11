package me.creatos.voucher.utils;

public enum Perm {

	EXAMPLE("project.example");
	
	private String permission;

	private Perm(String permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return permission;
	}

	
	
	
}
