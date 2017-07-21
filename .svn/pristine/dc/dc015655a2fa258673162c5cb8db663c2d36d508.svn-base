package com.zenchn.electrombile.entity.motorHardware;

import com.zenchn.electrombile.utils.CommonUtils;

/**
 * 仪表故障状态描述类
 * 
 * @author WangRui
 *
 */
public class MeterTrouble {

	boolean meter_linear_brake;// 线性刹把 b6
	boolean meter_turn;// 转把 b7
	boolean meter_switch;// 开关 b8

	public MeterTrouble(boolean meter_linear_brake, boolean meter_turn, boolean meter_switch) {
		super();
		this.meter_linear_brake = meter_linear_brake;
		this.meter_turn = meter_turn;
		this.meter_switch = meter_switch;
	}

	public boolean isMeter_linear_brake() {
		return meter_linear_brake;
	}

	public void setMeter_linear_brake(boolean meter_linear_brake) {
		this.meter_linear_brake = meter_linear_brake;
	}

	public boolean isMeter_turn() {
		return meter_turn;
	}

	public void setMeter_turn(boolean meter_turn) {
		this.meter_turn = meter_turn;
	}

	public boolean isMeter_switch() {
		return meter_switch;
	}

	public void setMeter_switch(boolean meter_switch) {
		this.meter_switch = meter_switch;
	}

//	@Override
//	public String toString() {
//		return "MeterTrouble [meter_linear_brake=" + meter_linear_brake + ", meter_turn=" + meter_turn
//				+ ", meter_switch=" + meter_switch + "]";
//	}
	
	@Override
	public String toString() {
		return "线性刹把:" + CommonUtils.formatTrouble(meter_linear_brake) + "\n" + "转把:"
				+ CommonUtils.formatTrouble(meter_turn) + "\n" + "开关:"
				+ CommonUtils.formatTrouble(meter_switch);
	}

}
