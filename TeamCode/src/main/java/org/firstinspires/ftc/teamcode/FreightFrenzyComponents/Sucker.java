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
    boolean positionIn = false;

    // For Generated Auto Support
    public class SUCKER_INTERFACE{
        public void setPowerInAuto(final double power){ suck(power);}
    }
    public class ARM_INTERFACE {
        public void setPowerInAuto(final double power) {
            if (power > 0)     setArmPosition(Position.OUTTAKE_POSITION, power);
            else               setArmPosition(Position.INTAKE_POSITION,  power);
        }
    }
    public ARM_INTERFACE SLIDER = new ARM_INTERFACE();
    public SUCKER_INTERFACE DUMPER = new SUCKER_INTERFACE();

    public Sucker(RobotBase BASE) {
        super(BASE);
        initMotors();

    }
    public enum Position {INTAKE_POSITION, OUTTAKE_POSITION };

    public void setArmPosition ( Position targetPositon, double speed) {
        switch (targetPositon){
            case INTAKE_POSITION:
                arm.setTargetPosition(775);
                break;
            case OUTTAKE_POSITION:
                arm.setTargetPosition(0);
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

    public void moveArmInTeleop(boolean button){

        if(button && !buttonIsHeld){
            buttonIsHeld = true;
            if(!positionIn){
                setArmPosition(Position.INTAKE_POSITION,.2);
            }
            else {
                setArmPosition(Position.OUTTAKE_POSITION,.6);
            }
            positionIn = !positionIn;
        }
        if(!button){
            buttonIsHeld = false;
        }
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
