package com.starsecurity.model;

/* 手机报告设备信息 */
public class TLV_V_PhoneInfoRequest {
	private short[] equipmentIdentity = new short[TLV_Length.STR_LEN_16];		//设备识别码,本字段中为DVS的物理地址,即MAC地址
	private short[] equipmentOS = new short[TLV_Length.STR_LEN_16];						//手机的操作系统
	private short reserve1;						//保留
	private short reserve2;						//保留
	private short reserve3;						//保留
	private short reserve4;						//保留
	public short[] getEquipmentIdentity() {
		return equipmentIdentity;
	}
	public void setEquipmentIdentity(short[] equipmentIdentity) {
		this.equipmentIdentity = equipmentIdentity;
	}
	public short[] getEquipmentOS() {
		return equipmentOS;
	}
	public void setEquipmentOS(short[] equipmentOS) {
		this.equipmentOS = equipmentOS;
	}
	public short getReserve1() {
		return reserve1;
	}
	public void setReserve1(short reserve1) {
		this.reserve1 = reserve1;
	}
	public short getReserve2() {
		return reserve2;
	}
	public void setReserve2(short reserve2) {
		this.reserve2 = reserve2;
	}
	public short getReserve3() {
		return reserve3;
	}
	public void setReserve3(short reserve3) {
		this.reserve3 = reserve3;
	}
	public short getReserve4() {
		return reserve4;
	}
	public void setReserve4(short reserve4) {
		this.reserve4 = reserve4;
	}
}
