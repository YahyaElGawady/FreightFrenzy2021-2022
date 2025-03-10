package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;
import org.firstinspires.ftc.robotcontroller.internal.RobotBase;

public class Sucker extends RobotComponent {

    public DcMotor sucker;
    public DcMotor arm;

    boolean buttonIsHeld  = false;
    boolean neutralButtonIsHeld = false;
    boolean positionIn = false;
    boolean isDoingSomethingNotManual = false;

    // For Generated Auto Support
    public class SUCKER_INTERFACE{
        public void setPowerInAuto(final double power){ suck(power);}
    }
    public class ARM_INTERFACE {
        public void setPowerInAuto(final double power) {
            if (power > 0)     setArmPosition(Position.OUTTAKE_POSITION, .4);
            else               setArmPosition(Position.INTAKE_POSITION,  1);
        }
    }
    public ARM_INTERFACE ARM = new ARM_INTERFACE();
    public SUCKER_INTERFACE SUCKER = new SUCKER_INTERFACE();

    public Sucker(RobotBase BASE) {
        super(BASE);
        initMotors();

    }
    public enum Position {INTAKE_POSITION, OUTTAKE_POSITION, START_POSITION };

    public void setArmPosition ( Position targetPositon, double speed) {
        switch (targetPositon){
            case START_POSITION:
                arm.setTargetPosition(-150);
                break;
            case INTAKE_POSITION:
                arm.setTargetPosition(-850);
                break;
            case OUTTAKE_POSITION:
                arm.setTargetPosition(100);
        }
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(speed);
    }

    void  initMotors() {
        sucker = base.getMapper().mapMotor("sucker", DcMotorSimple.Direction.FORWARD);
        arm = base.getMapper().mapMotor("arm");
        sucker.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void armMovementsInTeleop(boolean fullRange, boolean neutralPosition, double manualPower){
        if(fullRange){
            moveArmInTeleop();
        } //else if(neutralPosition){
//            moveArmToNeutral();
//        }
        else if(Math.abs(manualPower) > .1){
//            moveArmManual(manualPower);
            arm.setPower(manualPower);
            arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            isDoingSomethingNotManual = false;
        }
        else if(!isDoingSomethingNotManual){
            arm.setPower(0);
            arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            isDoingSomethingNotManual = true;
        }
        buttonIsHeld = fullRange;
        neutralButtonIsHeld = neutralPosition;
    }
    public void moveArmInTeleop(){

        if(!buttonIsHeld){
//            buttonIsHeld = true;
            if(!positionIn){
                setArmPosition(Position.INTAKE_POSITION,.4);
            }
            else {
                setArmPosition(Position.OUTTAKE_POSITION,1
                );
            }
            positionIn = !positionIn;
        }
//        else if(!button){
//            buttonIsHeld = false;
//        }
    }
    public void moveArmManual(double power){
//        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            if(Math.abs(power) > .1)
//                arm.setPower(power);
//            else
//                arm.setPower(0);
    }
    public void moveArmToNeutral(){

        if(!neutralButtonIsHeld){
            setArmPosition(Position.START_POSITION,.7);
        }
//        neutralButtonIsHeld = button;
    }

    public void stopSucker(){ sucker.setPower(0); }
    public void suck (double speed){
        sucker.setPower(speed);
    }
    public void moveSuckerInTeleop(double speed){
        //.362
        if(Math.abs(speed) >= .1) {

            this.suck(speed);
        }
        else {
            this.stopSucker();
        }
    }

    @Override
    public void stop() {
        stopSucker();
    }

}
