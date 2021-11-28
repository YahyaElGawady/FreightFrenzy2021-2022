package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.internal.RobotBase;
import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;

public class OuttakeBucket extends RobotComponent {
    public Servo dumper;
    public DcMotor slider;

    public final int DOWN = 0, BOTTOM = 1, MIDDLE = 2, TOP = 3;
    public final int spmap[] = {}; // TODO: add encoder values

    public final int UP = 1;
    public final double dumperPos[] = {0, 1};

    boolean buttonIsHeld;
    boolean otherButtonIsHeld;
    boolean downIsHeld;
    boolean upIsHeld;
    int level = 1;
    boolean positionIn;
    boolean positiondown;

    public OuttakeBucket(RobotBase base) {
        super(base);
        initServosAndMotors();
    }
    void initServosAndMotors() {
        dumper = base.getMapper().mapServo("dumper");
        slider = base.getMapper().mapMotor("slider");
//        slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Slides to BOTTOM, MIDDLE, or TOP.
    public void slideTo(int spos, double speed){
        slideEncoders(spmap[spos], speed);
    }
    public void changeLevel (boolean down, boolean up){
        if(down && !downIsHeld){
            downIsHeld = true;
            if(level > 0){
                level--;
            }
        }
         else if(up && !upIsHeld){
            upIsHeld = true;
            if(level < 3 ){
                level++;
            }
        }
    }
    void slideEncoders(int encoders, double speed){
        slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slider.setTargetPosition(encoders);
        slider.setPower(speed);

    }
    public void moveArmInTeleop(boolean button, int level){

        if(button && !buttonIsHeld){
            buttonIsHeld = true;
            if(!positionIn){
                slideTo(0,.5);
            }
            else {
                slideTo(level, .5);
            }
            positionIn = !positionIn;
        }
        if(!button){
            buttonIsHeld = false;
        }
    }
    public void DumpBucketInTeleop(boolean otherButton){
        if(otherButton && !otherButtonIsHeld) {
            otherButtonIsHeld = true;
            if (!positionIn) {
                dumper.setPosition(0);
            } else {
                dumper.setPosition(1);
            }
            positionIn = !positionIn;
        }
        if (!otherButtonIsHeld) {
            otherButtonIsHeld = false;
        }
    }


    @Override
    public void stop() {
        slider.setPower(0);
        dumper.setPosition(0); // TODO: fill with accurate number
    }
};
