package com.zenchn.electrombile.entity.motorHardware;

import com.zenchn.electrombile.utils.CommonUtils;

/**
 * 控制器故障状态描述类
 * 
 * @author WangRui
 *
 */
public class ControlTrouble {

	boolean bet_to_protect;// 赌转保护 b1
	boolean over_current_protection;// 过流保护 b2
	boolean anti_runaway_protection;// 防飞车保护 b3
	boolean undervoltage_protection;// 欠压保护 b4
	boolean turning_fault;// 转把故障 b5
	boolean motor_holzer_fault;// 电机霍尔故障 b6
	boolean motor_phase_missing;// 电机缺相 b7
	boolean controller_failure;// 控制器故障 b8
	boolean control_over_heat;// 控制器过热保护 b5
	boolean brake_lamp_failure;// 刹车灯故障 b6
	boolean rear_turn_lamp_fault;// 后转向灯故障 b7
	boolean nocturnal_light_fault;// 夜行灯故障 b8

	public ControlTrouble(boolean bet_to_protect, boolean over_current_protection, boolean anti_runaway_protection,
			boolean undervoltage_protection, boolean turning_fault, boolean motor_holzer_fault,
			boolean motor_phase_missing, boolean controller_failure) {
		super();
		this.bet_to_protect = bet_to_protect;
		this.over_current_protection = over_current_protection;
		this.anti_runaway_protection = anti_runaway_protection;
		this.undervoltage_protection = undervoltage_protection;
		this.turning_fault = turning_fault;
		this.motor_holzer_fault = motor_holzer_fault;
		this.motor_phase_missing = motor_phase_missing;
		this.controller_failure = controller_failure;
	}

	public ControlTrouble(boolean control_over_heat, boolean brake_lamp_failure, boolean rear_turn_lamp_fault,
			boolean nocturnal_light_fault) {
		super();
		this.control_over_heat = control_over_heat;
		this.brake_lamp_failure = brake_lamp_failure;
		this.rear_turn_lamp_fault = rear_turn_lamp_fault;
		this.nocturnal_light_fault = nocturnal_light_fault;
	}

	public ControlTrouble(boolean bet_to_protect, boolean over_current_protection, boolean anti_runaway_protection,
			boolean undervoltage_protection, boolean turning_fault, boolean motor_holzer_fault,
			boolean motor_phase_missing, boolean controller_failure, boolean control_over_heat,
			boolean brake_lamp_failure, boolean rear_turn_lamp_fault, boolean nocturnal_light_fault) {
		super();
		this.bet_to_protect = bet_to_protect;
		this.over_current_protection = over_current_protection;
		this.anti_runaway_protection = anti_runaway_protection;
		this.undervoltage_protection = undervoltage_protection;
		this.turning_fault = turning_fault;
		this.motor_holzer_fault = motor_holzer_fault;
		this.motor_phase_missing = motor_phase_missing;
		this.controller_failure = controller_failure;
		this.control_over_heat = control_over_heat;
		this.brake_lamp_failure = brake_lamp_failure;
		this.rear_turn_lamp_fault = rear_turn_lamp_fault;
		this.nocturnal_light_fault = nocturnal_light_fault;
	}

	public boolean isBet_to_protect() {
		return bet_to_protect;
	}

	public void setBet_to_protect(boolean bet_to_protect) {
		this.bet_to_protect = bet_to_protect;
	}

	public boolean isOver_current_protection() {
		return over_current_protection;
	}

	public void setOver_current_protection(boolean over_current_protection) {
		this.over_current_protection = over_current_protection;
	}

	public boolean isAnti_runaway_protection() {
		return anti_runaway_protection;
	}

	public void setAnti_runaway_protection(boolean anti_runaway_protection) {
		this.anti_runaway_protection = anti_runaway_protection;
	}

	public boolean isUndervoltage_protection() {
		return undervoltage_protection;
	}

	public void setUndervoltage_protection(boolean undervoltage_protection) {
		this.undervoltage_protection = undervoltage_protection;
	}

	public boolean isTurning_fault() {
		return turning_fault;
	}

	public void setTurning_fault(boolean turning_fault) {
		this.turning_fault = turning_fault;
	}

	public boolean isMotor_holzer_fault() {
		return motor_holzer_fault;
	}

	public void setMotor_holzer_fault(boolean motor_holzer_fault) {
		this.motor_holzer_fault = motor_holzer_fault;
	}

	public boolean isMotor_phase_missing() {
		return motor_phase_missing;
	}

	public void setMotor_phase_missing(boolean motor_phase_missing) {
		this.motor_phase_missing = motor_phase_missing;
	}

	public boolean isController_failure() {
		return controller_failure;
	}

	public void setController_failure(boolean controller_failure) {
		this.controller_failure = controller_failure;
	}

	public boolean isControl_over_heat() {
		return control_over_heat;
	}

	public void setControl_over_heat(boolean control_over_heat) {
		this.control_over_heat = control_over_heat;
	}

	public boolean isBrake_lamp_failure() {
		return brake_lamp_failure;
	}

	public void setBrake_lamp_failure(boolean brake_lamp_failure) {
		this.brake_lamp_failure = brake_lamp_failure;
	}

	public boolean isRear_turn_lamp_fault() {
		return rear_turn_lamp_fault;
	}

	public void setRear_turn_lamp_fault(boolean rear_turn_lamp_fault) {
		this.rear_turn_lamp_fault = rear_turn_lamp_fault;
	}

	public boolean isNocturnal_light_fault() {
		return nocturnal_light_fault;
	}

	public void setNocturnal_light_fault(boolean nocturnal_light_fault) {
		this.nocturnal_light_fault = nocturnal_light_fault;
	}

//	@Override
//	public String toString() {
//		return "ControlTrouble [bet_to_protect=" + bet_to_protect + ", over_current_protection="
//				+ over_current_protection + ", anti_runaway_protection=" + anti_runaway_protection
//				+ ", undervoltage_protection=" + undervoltage_protection + ", turning_fault=" + turning_fault
//				+ ", motor_holzer_fault=" + motor_holzer_fault + ", motor_phase_missing=" + motor_phase_missing
//				+ ", controller_failure=" + controller_failure + ", control_over_heat=" + control_over_heat
//				+ ", brake_lamp_failure=" + brake_lamp_failure + ", rear_turn_lamp_fault=" + rear_turn_lamp_fault
//				+ ", nocturnal_light_fault=" + nocturnal_light_fault + "]";
//	}
	
	@Override
	public String toString() {
		return "赌转保护:" + CommonUtils.formatTrouble(bet_to_protect) + "\n" + "过流保护:"
				+ CommonUtils.formatTrouble(over_current_protection) + "\n" + "防飞车保护:"
				+ CommonUtils.formatTrouble(anti_runaway_protection) + "\n" + "欠压保护:"
				+ CommonUtils.formatTrouble(undervoltage_protection) + "\n" + "转把故障:"
				+ CommonUtils.formatTrouble(turning_fault) + "\n" + "电机霍尔故障:"
				+ CommonUtils.formatTrouble(motor_holzer_fault) + "\n" + "电机缺相:"
				+ CommonUtils.formatTrouble(motor_phase_missing) + "\n" + "控制器故障:"
				+ CommonUtils.formatTrouble(controller_failure) + "\n" + "控制器过热保护:"
				+ CommonUtils.formatTrouble(control_over_heat) + "\n" + "刹车灯故障:"
				+ CommonUtils.formatTrouble(brake_lamp_failure) + "\n" + "后转向灯故障:"
				+ CommonUtils.formatTrouble(rear_turn_lamp_fault) + "\n" + "夜行灯故障:"
				+ CommonUtils.formatTrouble(nocturnal_light_fault) ;
	}

}
