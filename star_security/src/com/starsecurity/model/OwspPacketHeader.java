package com.starsecurity.model;

/* the common packet header, must be placed in front of any packets. */
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
