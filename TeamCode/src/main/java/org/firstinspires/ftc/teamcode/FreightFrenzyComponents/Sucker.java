package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;
import org.firstinspires.ftc.robotcontroller.internal.RobotBase;

public class Sucker extends RobotComponent {

    public DcMotor sucker;
    public DcMotor arm;

    boolean buttonIsHeld  = false;
    boolean positionIn = true;

    public Sucker(RobotBase BASE) {
        super(BASE);
        initMotors();

    }
    public enum Position {INTAKE_POSITION, OUTTAKE_POSITION };

    public void setArmPosition ( Position targetPositon, double speed) {
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        switch (targetPositon){
            case INTAKE_POSITION:
                arm.setTargetPosition(100);
                break;
            case OUTTAKE_POSITION:
                arm.setTargetPosition(-100);
        }
        arm.setPower(speed);
    }

    void  initMotors() {
        sucker = base.getMapper().mapMotor("sucker");
        arm = base.getMapper().mapMotor("arm");
        sucker.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sucker.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void moveArmInTeleop(boolean button){

        if(button && !buttonIsHeld){
            buttonIsHeld = true;
            if(!positionIn){
                setArmPosition(Position.INTAKE_POSITION,.5);
            }
            else {
                setArmPosition(Position.OUTTAKE_POSITION,.5);
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
            this.suck(0);
        }
    }

    @Override
    public void stop() {
        stopSucker();
    }

}
