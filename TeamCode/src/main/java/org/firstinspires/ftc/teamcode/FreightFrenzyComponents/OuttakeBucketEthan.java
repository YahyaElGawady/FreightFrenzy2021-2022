package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;
import org.firstinspires.ftc.robotcontroller.internal.RobotBase;

public class OuttakeBucketEthan extends RobotComponent {
    public DcMotor slider;
    public Servo dumper;

    boolean buttonIsHeld  = false;
    boolean positionIn = true;
    boolean dpadUpIsPressed = false;
    boolean dpadDownIsPressed = false;
    //todo change values to correct values
    public final int Top = 100;
    public final int Middle = 50;
    public final int Bottom = 0;
    public int currentSelectedPosition = Bottom;
    boolean buttonAIsPressed = false;
    boolean buttonAToggle = false;


    public OuttakeBucketEthan(RobotBase base) {
        super(base);
        dumper = base.getMapper().mapServo("dumper");
        slider = base().getMapper().mapMotor("slider");
    }
    public void DumpBucketInTeleop(boolean button){
        if(button && !buttonIsHeld) {
            buttonIsHeld = true;
            if (!positionIn) {
                dumper.setPosition(0);
            } else {
                dumper.setPosition(1);
            }
            positionIn = !positionIn;
        }
        if (!button) {
            buttonIsHeld = false;
        }
    }

    @Override
    public void stop() {

    }

    public void SelectedSliderPosition(boolean dpadUp, boolean dpadDown) {
        if(dpadUp && !dpadUpIsPressed); {
            switch(currentSelectedPosition) {
                case Bottom: currentSelectedPosition = Middle;break;
                case Middle: currentSelectedPosition = Top;
            }
        if(dpadDown && !dpadDownIsPressed); {
            switch(currentSelectedPosition) {
                case Middle: currentSelectedPosition = Bottom; break;
                case Top: currentSelectedPosition = Middle;
            }
            }
            dpadUpIsPressed = dpadUp;
            dpadDownIsPressed = dpadDown;
    }

}
    public void SliderPosition(boolean buttonA) {
        if(buttonA && !buttonAIsPressed) {
            buttonAToggle = !buttonAToggle;
            if(buttonAToggle){
                slider.setTargetPosition(currentSelectedPosition);
            }
            else{
                slider.setTargetPosition(Bottom);
            }
        }
        buttonAIsPressed = buttonA;
    }

}
