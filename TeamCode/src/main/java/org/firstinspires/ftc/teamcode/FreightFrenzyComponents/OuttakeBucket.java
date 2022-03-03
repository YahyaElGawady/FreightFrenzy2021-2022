//package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;
//
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import org.firstinspires.ftc.robotcontroller.internal.RobotBase;
//import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;
//
//public class OuttakeBucket extends RobotComponent {
//    public Servo dumper;
//    public DcMotor slider;
//
//    public final int DOWN = 0, BOTTOM = 1, MIDDLE = 2, TOP = 3;
//    public final int spmap[] = {}; // TODO: add encoder values
//
//    public final int UP = 1;
//    public final double dumperPos[] = {0, 1};
//    boolean buttonIsHeld;
//    boolean otherButtonIsHeld;
//    boolean downIsHeld;
//    boolean upIsHeld;
//    int level = 1;
//    boolean positionIn;
//    boolean positiondown;
//
//    public OuttakeBucket(RobotBase base) {
//        super(base);
//        initServosAndMotors();
//    }
//    void initServosAndMotors() {
//        dumper = base.getMapper().mapServo("dumper");
//        slider = base.getMapper().mapMotor("slider");
////        slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//    }
//
//    // Slides to BOTTOM, MIDDLE, or TOP.
//    public void slideTo(int spos, double speed){
//        slideEncoders(spmap[spos], speed);
//    }
//    public void changeLevel (boolean down, boolean up){
//        if(down && !downIsHeld){
//            downIsHeld = true;
//            if(level > 0){
//                level--;
//            }
//        }
//         else if(up && !upIsHeld){
//            upIsHeld = true;
//            if(level < 3 ){
//                level++;
//            }
//        }
//    }
//    void slideEncoders(int encoders, double speed){
//        slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        slider.setTargetPosition(encoders);
//        slider.setPower(speed);
//
//    }
//    public void moveArmInTeleop(boolean button, int level){
//
//        if(button && !buttonIsHeld){
//            buttonIsHeld = true;
//            if(!positionIn){
//                slideTo(0,.5);
//            }
//            else {
//                slideTo(level, .5);
//            }
//            positionIn = !positionIn;
//        }
//        if(!button){
//            buttonIsHeld = false;
//        }
//    }
//    public void DumpBucketInTeleop(boolean otherButton){
//        if(otherButton && !otherButtonIsHeld) {
//            otherButtonIsHeld = true;
//            if (!positionIn) {
//                dumper.setPosition(0);
//            } else {
//                dumper.setPosition(1);
//            }
//            positionIn = !positionIn;
//        }
//        if (!otherButtonIsHeld) {
//            otherButtonIsHeld = false;
//        }
//    }
//
//
//    @Override
//    public void stop() {
//        slider.setPower(0);
//        dumper.setPosition(0); // TODO: fill with accurate number
//    }
//};
package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.internal.RobotBase;
import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;

public class OuttakeBucket extends RobotComponent {
    public Servo dumper;
    public DcMotor slider;

    public boolean sliderButtonIsHeld = false;
    public boolean halfwayButtonIsHeld = false;
    public boolean dumperButtonIsHeld = false;
    public double dumperPosition =  DUMPED;
    public int sliderPosition = BOTTOM;

    public int sliderTop = TOP;

    public static final int    /*DOWN = 0, */BOTTOM = 0, MIDDLE = -185, TOP = -739;  // TODO: add encoder values
    public static final double DUMPED = .3; // TODO: add position for dumping
    public static final double NEUTRAL = 1; // TODO: add position for not dumping
    public static final double DOWN_POWER = .6;   // TODO: add slider Power
    public static final double UP_POWER = 1;   //  add slider Power

    // For Generated Auto Support
    public class SLIDER_INTERFACE{
        public void setTargetPosition(final double power){
            // Switches position based on a double power.
            switch((int)(power * 2)){
                case 0: slide(BOTTOM);   break;
                case 1: slide(MIDDLE); break;
                case 2: slide(TOP); break;
            }
        }
    }
    public class DUMPER_INTERFACE {
        public void setPowerInAuto(final double power) {
            if (power > 0) dumper.setPosition(DUMPED);
            else           dumper.setPosition(NEUTRAL);
        }
    }
    public SLIDER_INTERFACE SLIDER = new SLIDER_INTERFACE();
    public DUMPER_INTERFACE DUMPER = new DUMPER_INTERFACE();

    public  OuttakeBucket(RobotBase base) {
        super(base);
        initServosAndMotors();
    }
    void initServosAndMotors() {
        dumper = base.getMapper().mapServo("dumper");
        slider = base.getMapper().mapMotor("slider");
        slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slider.setTargetPosition(BOTTOM);
        slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dumper.setPosition(NEUTRAL);
//        slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

//    public int nextTop(){
//        switch(sliderTop){
//            case BOTTOM: return MIDDLE;
//        }
//        return TOP;
//    }
//    public int prevTop(){
//        switch(sliderTop){
//            case TOP: return MIDDLE;
//        }
//        return BOTTOM;
//    }
    // Slides to DOWN, BOTTOM, MIDDLE, or TOP based on up or down
    public int slideInTeleop(boolean button, boolean halfway){
//        if(button) {
//            switch(sliderPosition){
//                case DOWN: slide(sliderTop); break;
//                default: slide(DOWN);
//            }
//        }
        if(slider.getMode() != DcMotor.RunMode.RUN_TO_POSITION)
            slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(button && !sliderButtonIsHeld){
            sliderButtonIsHeld = true;
            switch(sliderPosition){
                case BOTTOM: slider.setPower(UP_POWER); slide(TOP); break;
                default:     slider.setPower(DOWN_POWER); slide(BOTTOM); break;
            }
        }
        else if(halfway && !halfwayButtonIsHeld){
            halfwayButtonIsHeld = true;
            switch(sliderPosition){
                case BOTTOM: slider.setPower(UP_POWER); slide(MIDDLE); break;
                default:     slider.setPower(DOWN_POWER); slide(BOTTOM); break;
            }
        }
        if(!button){
            sliderButtonIsHeld = false;
        }
        if(!halfway){
            halfwayButtonIsHeld = false;
        }
//        sliderButtonIsHeld = button;
//        halfwayButtonIsHeld = halfway;
        return sliderPosition;
    }
//    public void changeTopInTeleOp(boolean up, boolean down){
//        if(up){
//            sliderTop = nextTop();
//        }
//        else if(down){
//            sliderTop = prevTop();
//        }
//    }
    public void slideManual(double speed){
        if(Math.abs(speed) > .1) {
            if(slider.getMode() != DcMotor.RunMode.RUN_USING_ENCODER)
                slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slider.setPower(speed);
        } else{
            if(slider.getMode() == DcMotor.RunMode.RUN_USING_ENCODER)
                slider.setPower(0);
        }
    }

    public void slide(int encoders){
//        int targetPosition = slider.getCurrentPosition() + encoders;
        slider.setTargetPosition(encoders);
        sliderPosition = encoders;

//        if(!slider.isBusy())
//            slider.setPower(0);
//        slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
