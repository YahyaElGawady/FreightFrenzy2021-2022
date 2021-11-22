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
}
