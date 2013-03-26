package com.starsecurity.model;

/* DVS报告设备信息 */
public class TLV_V_DVSInfoRequest {
	char[] companyIdentity = new char[TLV_Length.STR_LEN_16];			//公司识别码,最多16个字符,后面不足部分为数字0      (为ASCII字符串)
	char[] equipmentIdentity = new char[TLV_Length.STR_LEN_16];			//设备识别码,本字段中为DVS的物理地址,即MAC地址,后面不足部分为数字0  (为ASCII字符串)
	char[] equipmentName = new char[TLV_Length.STR_LEN_16];				//设备名称,最多16个字符,后面不足部分为数字0        (为ASCII字符串)
	char[] equipmentVersion = new char[TLV_Length.STR_LEN_16];			//设备的软件版本,最多16个字符, 后面不足部分为数字0 (为ASCII字符串)
	OWSP_DATE equipmentDate;							//设备的出厂日期20090120 
	short channleNumber;			//设备支持多少个通道
	short reserve1;						//保留
	short reserve2;						//保留
	short reserve3;						//保留
	public char[] getCompanyIdentity() {
		return companyIdentity;
	}
	public void setCompanyIdentity(char[] companyIdentity) {
		this.companyIdentity = companyIdentity;
	}
	public char[] getEquipmentIdentity() {
		return equipmentIdentity;
	}
	public void setEquipmentIdentity(char[] equipmentIdentity) {
		this.equipmentIdentity = equipmentIdentity;
	}
	public char[] getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(char[] equipmentName) {
		this.equipmentName = equipmentName;
	}
	public char[] getEquipmentVersion() {
		return equipmentVersion;
	}
	public void setEquipmentVersion(char[] equipmentVersion) {
		this.equipmentVersion = equipmentVersion;
	}
	public OWSP_DATE getEquipmentDate() {
		return equipmentDate;
	}
	public void setEquipmentDate(OWSP_DATE equipmentDate) {
		this.equipmentDate = equipmentDate;
	}
	public short getChannleNumber() {
		return channleNumber;
	}
	public void setChannleNumber(short channleNumber) {
		this.channleNumber = channleNumber;
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
}
