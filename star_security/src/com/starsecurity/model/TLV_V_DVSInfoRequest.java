package com.starsecurity.model;

/* DVS�����豸��Ϣ */
public class TLV_V_DVSInfoRequest {
	char[] companyIdentity = new char[TLV_Length.STR_LEN_16];			//��˾ʶ����,���16���ַ�,���治�㲿��Ϊ����0      (ΪASCII�ַ���)
	char[] equipmentIdentity = new char[TLV_Length.STR_LEN_16];			//�豸ʶ����,���ֶ���ΪDVS�������ַ,��MAC��ַ,���治�㲿��Ϊ����0  (ΪASCII�ַ���)
	char[] equipmentName = new char[TLV_Length.STR_LEN_16];				//�豸����,���16���ַ�,���治�㲿��Ϊ����0        (ΪASCII�ַ���)
	char[] equipmentVersion = new char[TLV_Length.STR_LEN_16];			//�豸������汾,���16���ַ�, ���治�㲿��Ϊ����0 (ΪASCII�ַ���)
	OWSP_DATE equipmentDate;							//�豸�ĳ�������20090120 
	short channleNumber;			//�豸֧�ֶ��ٸ�ͨ��
	short reserve1;						//����
	short reserve2;						//����
	short reserve3;						//����
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
