package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcontroller.internal.RobotBase;
import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;

public class OuttakeBucketTyce extends RobotComponent {
    public Servo dumper;
    public DcMotor slider;

    public enum SlidePosition{ BOTTOM, MIDDLE, TOP};

    public OuttakeBucketTyce(RobotBase base) {
        super(base);
        initServosAndMotors();
    }
    void initServosAndMotors() {
        dumper = base.getMapper().mapServo("dumper");
        slider = base.getMapper().mapMotor("slider");
    }
    public void slideTo(SlidePosition spos){
        // TODO: implement with encoders
    }
    public void slideEncoders(int encoders){
        
    }

    @Override
    public void stop() {
        slider.setPower(0);
        dumper.setPosition(0); // TODO: fill with accurate number
    }
};
