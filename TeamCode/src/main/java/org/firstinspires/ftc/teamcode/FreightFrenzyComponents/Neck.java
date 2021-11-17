package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;
import org.firstinspires.ftc.robotcontroller.internal.RobotBase;

public class Neck extends RobotComponent {
    public Servo leftHand;
    public Servo rightHand;

    public DcMotor neck;

    boolean buttonIsHeld  = false;
    boolean positionIn = true;

    public Neck(RobotBase BASE) {
        super(BASE);
        initServo();
    }
    public enum Position {HUG_POSITION, OPEN_POSITION };

    public void setHandsPosition ( Position targetPositon) {
        switch (targetPositon){
            case HUG_POSITION:
                leftHand.setPosition(1);
                rightHand.setPosition(0);
                break;
            case OPEN_POSITION:
                leftHand.setPosition(0);
                rightHand.setPosition(1);
        }

    }

    void  initServo() {
        leftHand = base.getMapper().mapServo("leftHand");
        rightHand = base.getMapper().mapServo("rightHand");
    }

    public void moveHuggerInTeleop(boolean button){

        if(button && !buttonIsHeld){
            buttonIsHeld = true;
            if(!positionIn){
                setHandsPosition(Position.HUG_POSITION);
            }
            else {
                setHandsPosition(Position.OPEN_POSITION);
            }
            positionIn = !positionIn;
        }
        if(!button){
            buttonIsHeld = false;
        }
    }
    public void movingNeck(double strength){
            neck.setPower(0.3 * strength);
    }
    @Override
    public void stop() {
        this.setHandsPosition(Position.OPEN_POSITION);
    }
}
