package model;

public enum UserType {
	TESTER("测试人员", 0), DEVELOPER("开发人员", 1), ADMIN("管理人员", 2);

	private String name;
	private int index;

	UserType(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
}
