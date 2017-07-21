package com.zenchn.electrombile.entity.motorHardware;

import com.zenchn.electrombile.utils.CommonUtils;

/**
 * ECU故障描述类
 * 
 * @author WangRui
 *
 */
public class ECUTrouble {

	boolean ecu_brake_fault;// 刹把故障 b5
	boolean ecu_converter_exception;// 转换器异常 b6
	boolean ecu_turning_fault;// 转把故障 b7
	boolean ecu_over_current;// ECU过流 b8

	public ECUTrouble(boolean ecu_brake_fault, boolean ecu_converter_exception, boolean ecu_turning_fault,
			boolean ecu_over_current) {
		super();
		this.ecu_brake_fault = ecu_brake_fault;
		this.ecu_converter_exception = ecu_converter_exception;
		this.ecu_turning_fault = ecu_turning_fault;
		this.ecu_over_current = ecu_over_current;
	}

	public boolean isEcu_brake_fault() {
		return ecu_brake_fault;
	}

	public void setEcu_brake_fault(boolean ecu_brake_fault) {
		this.ecu_brake_fault = ecu_brake_fault;
	}

	public boolean isEcu_converter_exception() {
		return ecu_converter_exception;
	}

	public void setEcu_converter_exception(boolean ecu_converter_exception) {
		this.ecu_converter_exception = ecu_converter_exception;
	}

	public boolean isEcu_turning_fault() {
		return ecu_turning_fault;
	}

	public void setEcu_turning_fault(boolean ecu_turning_fault) {
		this.ecu_turning_fault = ecu_turning_fault;
	}

	public boolean isEcu_over_current() {
		return ecu_over_current;
	}

	public void setEcu_over_current(boolean ecu_over_current) {
		this.ecu_over_current = ecu_over_current;
	}

	// @Override
	// public String toString() {
	// return "ECUTroble [ecu_brake_fault=" + ecu_brake_fault +
	// ", ecu_converter_exception=" + ecu_converter_exception
	// + ", ecu_turning_fault=" + ecu_turning_fault + ", ecu_over_current=" +
	// ecu_over_current + "]";
	// }

	@Override
	public String toString() {
		return "刹把故障:" + CommonUtils.formatBoolean(ecu_brake_fault) + "\n" + "转把故障:"
				+ CommonUtils.formatBoolean(ecu_converter_exception) + "\n" + "转换器异常:"
				+ CommonUtils.formatBoolean(ecu_turning_fault) + "\n" + "ECU过流:"
				+ CommonUtils.formatBoolean(ecu_over_current);
	}

}
