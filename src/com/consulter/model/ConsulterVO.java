package com.consulter.model;

public class ConsulterVO {
	
	//Oracle存諮詢者的基本資訊，對話訊息存Redis
	private String consulter_no;
	private String consulter_name;
	private byte[] consulter_icon;
	
	public ConsulterVO() {
		
	}
	
	public ConsulterVO(String consulter_no,String consulter_name,byte[] consulter_icon) {
		this.consulter_no = consulter_no;
		this.consulter_name = consulter_name;
		this.consulter_icon = consulter_icon;
	}
	
	public String getConsulter_no() {
		return consulter_no;
	}
	public void setConsulter_no(String consulter_no) {
		this.consulter_no = consulter_no;
	}
	public String getConsulter_name() {
		return consulter_name;
	}
	public void setConsulter_name(String consulter_name) {
		this.consulter_name = consulter_name;
	}
	public byte[] getConsulter_icon() {
		return consulter_icon;
	}
	public void setConsulter_icon(byte[] consulter_icon) {
		this.consulter_icon = consulter_icon;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consulter_no == null) ? 0 : consulter_no.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConsulterVO other = (ConsulterVO) obj;
		if (consulter_no == null) {
			if (other.consulter_no != null)
				return false;
		} else if (!consulter_no.equals(other.consulter_no))
			return false;
		return true;
	}
	

}
