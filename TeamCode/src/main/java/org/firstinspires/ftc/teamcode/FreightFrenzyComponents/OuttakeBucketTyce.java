package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.internal.RobotBase;
import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;

public class OuttakeBucketTyce extends RobotComponent {
    public Servo dumper;
    public DcMotor slider;

    public final int BOTTOM = 0, MIDDLE = 1, TOP = 2;
    public final int spmap[] = {}; // TODO: add encoder values

    class InvalidSlidePosition extends RuntimeException{}

    public OuttakeBucketTyce(RobotBase base) {
        super(base);
        initServosAndMotors();
    }
    void initServosAndMotors() {
        dumper = base.getMapper().mapServo("dumper");
        slider = base.getMapper().mapMotor("slider");
//        slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Slides to BOTTOM, MIDDLE, or TOP.
    public void slideTo(int spos){
        slideEncoders(spmap[spos]);
    }
    void slideEncoders(int encoders){
        slider.setTargetPosition(encoders);
    }

    @Override
    public void stop() {
        slider.setPower(0);
        dumper.setPosition(0); // TODO: fill with accurate number
    }
};
