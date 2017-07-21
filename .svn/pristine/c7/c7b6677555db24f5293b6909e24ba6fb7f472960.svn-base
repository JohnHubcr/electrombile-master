package com.zenchn.electrombile.engine;

import com.zenchn.electrombile.engine.bean.JK_BMSTrouble;
import com.zenchn.electrombile.engine.bean.JK_ChargerTrouble;
import com.zenchn.electrombile.engine.bean.JK_ControlTrouble;
import com.zenchn.electrombile.engine.bean.JK_ECUOnlineStatus;
import com.zenchn.electrombile.engine.bean.JK_ECUTrouble;
import com.zenchn.electrombile.engine.bean.JK_GyroscopeStatus;
import com.zenchn.electrombile.engine.bean.JK_MeterTrouble;
import com.zenchn.electrombile.engine.bean.ST_BMSTrouble;
import com.zenchn.electrombile.engine.bean.ST_VehicleHardwareInfo;
import com.zenchn.electrombile.engine.bean.VehicleCheckResultForJKInfo;
import com.zenchn.electrombile.engine.bean.VehicleCheckResultForSTInfo;
import com.zenchn.electrombile.engine.callback.VehicleParserCallback;
import com.zenchn.electrombile.engine.constant.ProtocolForJK;
import com.zenchn.electrombile.engine.constant.ProtocolForST;
import com.zenchn.electrombile.entity.VehicleCheckRawForJKInfo;
import com.zenchn.electrombile.entity.VehicleCheckRawForSTInfo;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.electrombile.utils.CommonUtils;

import java.math.BigInteger;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作    者：wangr on 2017/3/7 13:28
 * 描    述：
 * 修订记录：
 */
public class HardwareParserEngine {

    private HardwareParserEngine() {
    }

    private static class SingletonInstance {
        private static final HardwareParserEngine INSTANCE = new HardwareParserEngine();
    }

    public static HardwareParserEngine getInstance() {
        return SingletonInstance.INSTANCE;
    }

    /**
     * 根据神腾协议进行解析
     *
     * @param vehicleCheckRawForSTInfo
     * @return
     */
    public void analyticalData(final VehicleCheckRawForSTInfo vehicleCheckRawForSTInfo, final VehicleParserCallback callback) {

        Observable.create(new Observable.OnSubscribe<VehicleCheckRawForSTInfo>() {
            @Override
            public void call(Subscriber<? super VehicleCheckRawForSTInfo> subscriber) {
                LogKit.logEngine("Hi , Parser Start！");
                subscriber.onNext(vehicleCheckRawForSTInfo);
                subscriber.onCompleted();
            }
        }).map(new Func1<VehicleCheckRawForSTInfo, VehicleCheckResultForSTInfo>() {
            @Override
            public VehicleCheckResultForSTInfo call(VehicleCheckRawForSTInfo vehicleCheckRawForSTInfo) {

                ST_BMSTrouble st_bmsTrouble = null;
                ST_VehicleHardwareInfo st_vehicleHardwareInfo = null;

                if (vehicleCheckRawForSTInfo != null) {

                    String bmsTrouble = vehicleCheckRawForSTInfo.getBmsTrouble();
                    if (CommonUtils.isNonNull(bmsTrouble))
                        st_bmsTrouble = parseBmsTroubleForST(bmsTrouble);

                    String vehicleConditionStatus = vehicleCheckRawForSTInfo.getVehicleConditionStatus();
                    if (CommonUtils.isNonNull(vehicleConditionStatus))
                        st_vehicleHardwareInfo = parseVehicleConditionStatusForST(vehicleConditionStatus);
                }

                return new VehicleCheckResultForSTInfo(st_bmsTrouble, st_vehicleHardwareInfo);
            }
        })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VehicleCheckResultForSTInfo>() {
                    @Override
                    public void call(VehicleCheckResultForSTInfo vehicleCheckResultForSTInfo) {
                        if (callback != null)
                            callback.onParserSuccess(vehicleCheckResultForSTInfo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogKit.logEngine("Sorry , Parser Error! \n" + "details:" + throwable.toString());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        LogKit.logEngine("OK , Parser Completed!");
                    }
                });
    }

    /**
     * BMS故障解析
     *
     * @param bmsTrouble
     * @return
     */
    private ST_BMSTrouble parseBmsTroubleForST(String bmsTrouble) {
        int bmsTroubleValue = new BigInteger(bmsTrouble, 2).intValue();

        boolean currC = (bmsTroubleValue & ProtocolForST.BmsTrouble.a) != 0;
        boolean currD = (bmsTroubleValue & ProtocolForST.BmsTrouble.b) != 0;
        boolean tempCH = (bmsTroubleValue & ProtocolForST.BmsTrouble.c) != 0;
        boolean tempCL = (bmsTroubleValue & ProtocolForST.BmsTrouble.d) != 0;

        boolean tempDH = (bmsTroubleValue & ProtocolForST.BmsTrouble.e) != 0;
        boolean tempDL = (bmsTroubleValue & ProtocolForST.BmsTrouble.f) != 0;
        boolean voltH = (bmsTroubleValue & ProtocolForST.BmsTrouble.g) != 0;
        boolean voltL = (bmsTroubleValue & ProtocolForST.BmsTrouble.h) != 0;

        return new ST_BMSTrouble(
                tempDL,
                tempDH,
                tempCL,
                tempCH,
                currD,
                currC,
                voltL,
                voltH);
    }

    /**
     * 车辆车况和故障数据解析
     *
     * @param vehicleConditionStatus
     * @return
     */
    private ST_VehicleHardwareInfo parseVehicleConditionStatusForST(String vehicleConditionStatus) {

        int vehicleConditionStatus1 = new BigInteger(vehicleConditionStatus.substring(0, 8), 2).intValue();
        int vehicleConditionStatus2 = new BigInteger(vehicleConditionStatus.substring(8, 16), 2).intValue();
        int vehicleConditionStatus3 = new BigInteger(vehicleConditionStatus.substring(16, 24), 2).intValue();

        boolean controlFault = (vehicleConditionStatus1 & ProtocolForST.VehicleConditionStatus.a) != 0;
        boolean turningFault = (vehicleConditionStatus1 & ProtocolForST.VehicleConditionStatus.b) != 0;
        boolean brakeFault = (vehicleConditionStatus1 & ProtocolForST.VehicleConditionStatus.c) != 0;
        boolean vehicleHolzerFault = (vehicleConditionStatus1 & ProtocolForST.VehicleConditionStatus.d) != 0;

        boolean vehicleLockStatus = (vehicleConditionStatus1 & ProtocolForST.VehicleConditionStatus.e) != 0;
        boolean underVoltageProtection = (vehicleConditionStatus1 & ProtocolForST.VehicleConditionStatus.f) != 0;
        boolean overVoltageCondition = (vehicleConditionStatus1 & ProtocolForST.VehicleConditionStatus.g) != 0;
        boolean antiTheftState = (vehicleConditionStatus1 & ProtocolForST.VehicleConditionStatus.h) != 0;

        boolean realTimeStatus = (vehicleConditionStatus2 & ProtocolForST.VehicleConditionStatus.i) != 0;
        boolean cruisingState = (vehicleConditionStatus2 & ProtocolForST.VehicleConditionStatus.j) != 0;
        boolean assistState = (vehicleConditionStatus2 & ProtocolForST.VehicleConditionStatus.k) != 0;
        boolean reverseChargeState = (vehicleConditionStatus2 & ProtocolForST.VehicleConditionStatus.l) != 0;

        boolean gear1 = (vehicleConditionStatus2 & ProtocolForST.VehicleConditionStatus.m) != 0;
        boolean gear2 = (vehicleConditionStatus2 & ProtocolForST.VehicleConditionStatus.n) != 0;
        boolean gear3 = (vehicleConditionStatus2 & ProtocolForST.VehicleConditionStatus.o) != 0;
        boolean gear4 = (vehicleConditionStatus2 & ProtocolForST.VehicleConditionStatus.p) != 0;

        boolean speedLimitedMode = (vehicleConditionStatus3 & ProtocolForST.VehicleConditionStatus.q) != 0;
        boolean vehicleState = (vehicleConditionStatus3 & ProtocolForST.VehicleConditionStatus.r) != 0;
        boolean repairStatus = (vehicleConditionStatus3 & ProtocolForST.VehicleConditionStatus.s) != 0;

        return new ST_VehicleHardwareInfo(
                repairStatus,
                vehicleState,
                speedLimitedMode,
                gear4,
                gear3,
                gear2,
                gear1,
                reverseChargeState,
                assistState,
                cruisingState,
                realTimeStatus,
                antiTheftState,
                overVoltageCondition,
                underVoltageProtection,
                vehicleLockStatus,
                vehicleHolzerFault,
                brakeFault,
                turningFault,
                controlFault
        );
    }

    /**
     * 根据金开协议进行解析
     *
     * @param vehicleCheckRawForJKInfo
     * @return
     */
    public void analyticalData(final VehicleCheckRawForJKInfo vehicleCheckRawForJKInfo, final VehicleParserCallback callback) {

        Observable.create(new Observable.OnSubscribe<VehicleCheckRawForJKInfo>() {
            @Override
            public void call(Subscriber<? super VehicleCheckRawForJKInfo> subscriber) {
                LogKit.logEngine("Hi , Parser Start！");
                subscriber.onNext(vehicleCheckRawForJKInfo);
                subscriber.onCompleted();
            }
        }).map(new Func1<VehicleCheckRawForJKInfo, VehicleCheckResultForJKInfo>() {
            @Override
            public VehicleCheckResultForJKInfo call(VehicleCheckRawForJKInfo vehicleCheckRawForJKInfo) {

                JK_BMSTrouble jk_bmsTrouble = null;
                JK_ChargerTrouble jk_chargerTrouble = null;
                JK_ControlTrouble jk_controlTrouble = null;
                JK_ECUOnlineStatus jk_ecuOnlineStatus = null;
                JK_ECUTrouble jk_ecuTrouble = null;
                JK_GyroscopeStatus jk_gyroscopeStatus = null;
                JK_MeterTrouble jk_meterTrouble = null;

                if (vehicleCheckRawForJKInfo != null) {

                    String bmsTrouble = vehicleCheckRawForJKInfo.getBmsTrouble();
                    if (CommonUtils.isNonNull(bmsTrouble))
                        jk_bmsTrouble = parseBmsTroubleForJK(bmsTrouble);// BMS故障

                    String chargerTrouble = vehicleCheckRawForJKInfo.getChargerTrouble();
                    if (CommonUtils.isNonNull(chargerTrouble))
                        jk_chargerTrouble = parseChargerTroubleForJK(chargerTrouble);// 充电器故障

                    String controlTroubleStatus1 = vehicleCheckRawForJKInfo.getControlTroubleStatus1();
                    String controlTroubleStatus2 = vehicleCheckRawForJKInfo.getControlTroubleStatus2();
                    if (CommonUtils.isNonNull(controlTroubleStatus1) && CommonUtils.isNonNull(controlTroubleStatus2))
                        jk_controlTrouble = parseControlTroubleForJK(controlTroubleStatus1, controlTroubleStatus2);// 控制器故障

                    String ecuOnlineCheck = vehicleCheckRawForJKInfo.getEcuOnlineCheck();
                    if (CommonUtils.isNonNull(ecuOnlineCheck))
                        jk_ecuOnlineStatus = parseEcuOnlineCheckForJK(ecuOnlineCheck);// ECU外设在线检测结果

                    String ecuTrouble = vehicleCheckRawForJKInfo.getEcuTrouble();
                    if (CommonUtils.isNonNull(ecuTrouble))
                        jk_ecuTrouble = parseEcuTroubleForJK(ecuTrouble);// ECU故障

                    String gyroscopeStatus = vehicleCheckRawForJKInfo.getGyroscopeStatus();
                    if (CommonUtils.isNonNull(gyroscopeStatus))
                        jk_gyroscopeStatus = parseGyroscopeStatusForJK(gyroscopeStatus);// 陀螺仪状态

                    String meterTroubleStatus = vehicleCheckRawForJKInfo.getMeterTroubleStatus();
                    if (CommonUtils.isNonNull(meterTroubleStatus))
                        jk_meterTrouble = parseMeterTroubleForJK(meterTroubleStatus);// 仪表盘状态
                }

                return new VehicleCheckResultForJKInfo(jk_bmsTrouble, jk_chargerTrouble, jk_controlTrouble, jk_ecuOnlineStatus, jk_ecuTrouble, jk_gyroscopeStatus, jk_meterTrouble);
            }
        })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VehicleCheckResultForJKInfo>() {
                    @Override
                    public void call(VehicleCheckResultForJKInfo vehicleCheckResultForJKInfo) {
                        if (callback != null)
                            callback.onParserSuccess(vehicleCheckResultForJKInfo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogKit.logEngine("Sorry , Parser Error! \n" + "details:" + throwable.toString());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        LogKit.logEngine("OK , Parser Completed!");
                    }
                });

    }

    /**
     * 仪表盘状态解析
     *
     * @param meterTrouble
     * @return
     */
    private JK_MeterTrouble parseMeterTroubleForJK(String meterTrouble) {
        int meterTroubleValue = new BigInteger(meterTrouble, 2).intValue();

        boolean meterLinearBrake = (meterTroubleValue & ProtocolForJK.MeterTroubleStatus.f) != 0;
        boolean meterTurn = (meterTroubleValue & ProtocolForJK.MeterTroubleStatus.g) != 0;
        boolean meterSwitch = (meterTroubleValue & ProtocolForJK.MeterTroubleStatus.h) != 0;

        return new JK_MeterTrouble(meterLinearBrake, meterTurn, meterSwitch);
    }

    /**
     * 陀螺仪状态解析
     *
     * @param gyroscopeStatus
     * @return
     */
    private JK_GyroscopeStatus parseGyroscopeStatusForJK(String gyroscopeStatus) {
        int gyroscopeStatusValue = new BigInteger(gyroscopeStatus, 2).intValue();

        boolean staticRollover = ProtocolForJK.GyroscopeStatus.a == gyroscopeStatusValue;
        boolean dynamicRollover = ProtocolForJK.GyroscopeStatus.b == gyroscopeStatusValue;
        boolean bumpyRoad = ProtocolForJK.GyroscopeStatus.c == gyroscopeStatusValue;
        boolean decelerationBeforeTurning = ProtocolForJK.GyroscopeStatus.d == gyroscopeStatusValue;

        boolean downhillAcceleration = ProtocolForJK.GyroscopeStatus.e == gyroscopeStatusValue;
        boolean downhillDeceleration = ProtocolForJK.GyroscopeStatus.f == gyroscopeStatusValue;
        boolean brakeHard = ProtocolForJK.GyroscopeStatus.g == gyroscopeStatusValue;
        boolean downhillTurn = ProtocolForJK.GyroscopeStatus.h == gyroscopeStatusValue;

        boolean S_Type_Route = ProtocolForJK.GyroscopeStatus.i == gyroscopeStatusValue;
        boolean cornerTurn = ProtocolForJK.GyroscopeStatus.j == gyroscopeStatusValue;
        boolean noLightsAtNight = ProtocolForJK.GyroscopeStatus.k == gyroscopeStatusValue;

        return new JK_GyroscopeStatus(
                staticRollover,
                dynamicRollover,
                bumpyRoad,
                decelerationBeforeTurning,
                downhillAcceleration,
                downhillDeceleration,
                brakeHard,
                downhillTurn,
                S_Type_Route,
                cornerTurn,
                noLightsAtNight);
    }

    /**
     * ECU外设在线检测
     *
     * @param ecuOnlineCheck
     * @return
     */
    private JK_ECUOnlineStatus parseEcuOnlineCheckForJK(String ecuOnlineCheck) {

        int ecuOnlineCheckValue = new BigInteger(ecuOnlineCheck, 2).intValue();

        boolean BMS = (ecuOnlineCheckValue & ProtocolForJK.EcuOnlineCheck.c) != 0;
        boolean Charger = (ecuOnlineCheckValue & ProtocolForJK.EcuOnlineCheck.d) != 0;

        boolean Bluetooth = (ecuOnlineCheckValue & ProtocolForJK.EcuOnlineCheck.e) != 0;
        boolean Appearance = (ecuOnlineCheckValue & ProtocolForJK.EcuOnlineCheck.f) != 0;
        boolean GPS = (ecuOnlineCheckValue & ProtocolForJK.EcuOnlineCheck.g) != 0;
        boolean Controller = (ecuOnlineCheckValue & ProtocolForJK.EcuOnlineCheck.h) != 0;

        return new JK_ECUOnlineStatus(
                BMS,
                Charger,
                Bluetooth,
                Appearance,
                GPS,
                Controller);
    }

    /**
     * ECU故障解析
     *
     * @param ecuTrouble
     * @return
     */
    private JK_ECUTrouble parseEcuTroubleForJK(String ecuTrouble) {
        int ecuTroubleValue = new BigInteger(ecuTrouble, 2).intValue();

        boolean ecuBrakeFault = (ecuTroubleValue & ProtocolForJK.EcuTrouble.e) != 0;
        boolean ecuTurningFault = (ecuTroubleValue & ProtocolForJK.EcuTrouble.f) != 0;
        boolean ecuConverterException = (ecuTroubleValue & ProtocolForJK.EcuTrouble.g) != 0;
        boolean ecuOverCurrent = (ecuTroubleValue & ProtocolForJK.EcuTrouble.h) != 0;

        return new JK_ECUTrouble(
                ecuBrakeFault,
                ecuTurningFault,
                ecuConverterException,
                ecuOverCurrent);
    }

    /**
     * 控制器故障解析
     *
     * @param controlTroubleStatus1
     * @param controlTroubleStatus2
     * @return
     */
    private JK_ControlTrouble parseControlTroubleForJK(String controlTroubleStatus1, String controlTroubleStatus2) {

        int controlTroubleStatus1Value = new BigInteger(controlTroubleStatus1, 2).intValue();
        int controlTroubleStatus2Value = new BigInteger(controlTroubleStatus2, 2).intValue();

        boolean betToProtect = (controlTroubleStatus1Value & ProtocolForJK.ControlTroubleStatus1.a) != 0;
        boolean overCurrentProtection = (controlTroubleStatus1Value & ProtocolForJK.ControlTroubleStatus1.b) != 0;
        boolean antiRunawayProtection = (controlTroubleStatus1Value & ProtocolForJK.ControlTroubleStatus1.c) != 0;
        boolean underVoltageProtection = (controlTroubleStatus1Value & ProtocolForJK.ControlTroubleStatus1.d) != 0;

        boolean turningFault = (controlTroubleStatus1Value & ProtocolForJK.ControlTroubleStatus1.e) != 0;
        boolean motorHolzerFault = (controlTroubleStatus1Value & ProtocolForJK.ControlTroubleStatus1.f) != 0;
        boolean motorPhaseMissing = (controlTroubleStatus1Value & ProtocolForJK.ControlTroubleStatus1.g) != 0;
        boolean controllerFailure = (controlTroubleStatus1Value & ProtocolForJK.ControlTroubleStatus1.h) != 0;

        boolean controlOverHeat = (controlTroubleStatus2Value & ProtocolForJK.ControlTroubleStatus2.e) != 0;
        boolean brakeLampFailure = (controlTroubleStatus2Value & ProtocolForJK.ControlTroubleStatus2.f) != 0;
        boolean rearTurnLampFault = (controlTroubleStatus2Value & ProtocolForJK.ControlTroubleStatus2.g) != 0;
        boolean nocturnalLightFault = (controlTroubleStatus2Value & ProtocolForJK.ControlTroubleStatus2.h) != 0;

        return new JK_ControlTrouble(
                betToProtect,
                overCurrentProtection,
                antiRunawayProtection,
                underVoltageProtection,
                turningFault,
                motorHolzerFault,
                motorPhaseMissing,
                controllerFailure,
                controlOverHeat,
                brakeLampFailure,
                rearTurnLampFault,
                nocturnalLightFault);
    }

    /**
     * 充电器故障解析
     *
     * @param chargerTrouble
     * @return
     */
    private JK_ChargerTrouble parseChargerTroubleForJK(String chargerTrouble) {
        int chargerTroubleValue = new BigInteger(chargerTrouble, 2).intValue();

        boolean chargerTemperature = (chargerTroubleValue & ProtocolForJK.ChargerTrouble.e) != 0;
        boolean chargerOverVoltage = (chargerTroubleValue & ProtocolForJK.ChargerTrouble.f) != 0;
        boolean chargerUnderVoltage = (chargerTroubleValue & ProtocolForJK.ChargerTrouble.g) != 0;
        boolean chargerOverCurrent = (chargerTroubleValue & ProtocolForJK.ChargerTrouble.h) != 0;

        return new JK_ChargerTrouble(
                chargerTemperature,
                chargerOverVoltage,
                chargerUnderVoltage,
                chargerOverCurrent);
    }

    /**
     * BMS故障解析
     *
     * @param bmsTrouble
     * @return
     */
    private JK_BMSTrouble parseBmsTroubleForJK(String bmsTrouble) {
        int bmsTroubleValue = new BigInteger(bmsTrouble, 2).intValue();

        boolean bmsMosDamage = (bmsTroubleValue & ProtocolForJK.BmsTrouble.b) != 0;
        boolean bmsChargingOver = (bmsTroubleValue & ProtocolForJK.BmsTrouble.c) != 0;
        boolean bmsOverTemperature = (bmsTroubleValue & ProtocolForJK.BmsTrouble.d) != 0;

        boolean bmsHighVoltage = (bmsTroubleValue & ProtocolForJK.BmsTrouble.e) != 0;
        boolean bmsLowVoltage = (bmsTroubleValue & ProtocolForJK.BmsTrouble.f) != 0;
        boolean bmsBatteryOverCurrent = (bmsTroubleValue & ProtocolForJK.BmsTrouble.g) != 0;
        boolean bmsExternalCircuitShortCircuit = (bmsTroubleValue & ProtocolForJK.BmsTrouble.h) != 0;

        return new JK_BMSTrouble(
                bmsMosDamage,
                bmsChargingOver,
                bmsOverTemperature,
                bmsHighVoltage,
                bmsLowVoltage,
                bmsBatteryOverCurrent,
                bmsExternalCircuitShortCircuit);
    }


}
