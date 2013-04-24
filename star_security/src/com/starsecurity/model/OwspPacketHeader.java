package com.starsecurity.model;

/***
 * 
 * the common packet header, must be placed in front of any packets.
 * to server immidiately after StreamDataFormat
 * @author  	创建人                 肖远东
 * @date        创建日期           2013-03-18
 * @author      修改人                 肖远东
 * @date        修改日期           2013-03-18
 * @description 修改说明	             首次增加
 *
 */
public class OwspPacketHeader {
	private long packet_length;		//length of the following packet, donot include this header
	private long packet_seq;		//packet sequence 包序号,每发送一个包就自增
	public long getPacket_length() {
		return packet_length;
	}
	public void setPacket_length(long packet_length) {
		this.packet_length = packet_length;
	}
	public long getPacket_seq() {
		return packet_seq;
	}
	public void setPacket_seq(long packet_seq) {
		this.packet_seq = packet_seq;
	}
}
