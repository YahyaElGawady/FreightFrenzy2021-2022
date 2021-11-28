package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.internal.RobotBase;
import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;

public class OuttakeBucketTyce extends RobotComponent {
    public Servo dumper;
    public DcMotor slider;

//    public boolean sliderButtonIsHeld = false;
    public boolean dumperButtonIsHeld = false;
    public double dumperPosition =  NEUTRAL;
    public int sliderPosition = DOWN;

    public static final int DOWN = 0, BOTTOM = 1, MIDDLE = 2, TOP = 3;  // TODO: add encoder values
    public static final double DUMPED = 0; // TODO: add position for dumping
    public static final double NEUTRAL = 0; // TODO: add position for not dumping

    public OuttakeBucketTyce(RobotBase base) {
        super(base);
        initServosAndMotors();
    }
    void initServosAndMotors() {
        dumper = base.getMapper().mapServo("dumper");
        slider = base.getMapper().mapMotor("slider");
//        slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Finds next position in O(1)
    public int nextPosition(){
        switch(sliderPosition){
            case DOWN: sliderPosition = BOTTOM; break;
            case BOTTOM: sliderPosition = MIDDLE; break;
            case MIDDLE: sliderPosition = TOP; break;
        }
        return sliderPosition;
    }
    // Finds prev position in O(1)
    public int prevPosition(){
        switch(sliderPosition){
            case BOTTOM: sliderPosition = DOWN; break;
            case MIDDLE: sliderPosition = BOTTOM; break;
            case TOP: sliderPosition = MIDDLE; break;
        }
        return sliderPosition;
    }

    // Slides to DOWN, BOTTOM, MIDDLE, or TOP based on up or down
    public void slideInTeleop(boolean up, boolean down){
        if(up) {
            slider.setTargetPosition(nextPosition());
        }
        else if(down){
            slider.setTargetPosition(prevPosition());
        }
    }
    public void slide(int encoders){
        slider.setTargetPosition(sliderPosition = encoders);
    }
//    public boolean slideInTeleop(boolean button, int spos){
//        if(button && !sliderButtonIsHeld){
//            slide(spos);
//        }
//        sliderButtonIsHeld = button;
//    }

//    public void dump(boolean dump){
//        if (dump && dumperPosition == NEUTRAL) {
//            dumper.setPosition(dumperPosition = DUMPED);
//        } else if (!dump && dumperPosition == DUMPED) {
//            dumper.setPosition(dumperPosition = NEUTRAL);
//        }
//    }

    // Toggles dumper position
    public void dump(boolean button){
        if(!dumperButtonIsHeld && button) {
            dumper.setPosition(dumperPosition = (dumperPosition == NEUTRAL ? DUMPED : NEUTRAL));
        }
        dumperButtonIsHeld = button;
    }
    @Override
    public void stop() {
        slider.setPower(0);
        dumper.setPosition(dumperPosition = NEUTRAL);
    }
};
