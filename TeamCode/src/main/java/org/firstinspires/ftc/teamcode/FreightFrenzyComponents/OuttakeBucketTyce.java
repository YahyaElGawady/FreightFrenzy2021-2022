package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcontroller.internal.RobotBase;
import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;

public class OuttakeBucketTyce extends RobotComponent {
    public Servo dumper;
    public DcMotor slider;

    public static final byte BOTTOM = 0, MIDDLE = 1, TOP = 2; // pos for slider enumeration values
    public static final int spmap[] = {0, 0, 0}; // TODO: add encoder value
    public static final int dump_pos = 0; // TODO: add position for dumping
    public static final int neutral_pos = 0; // TODO: add position for not dumping

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
    public void slideTo(byte spos){
        slider.setTargetPosition(spmap[spos]);
    }

    public boolean dump(int millisecs){
        dumper.setPosition(dump_pos);
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
            return false;
        }
        finally {
            dumper.setPosition(neutral_pos);
        }
        return true;
    }

    @Override
    public void stop() {
        slider.setPower(0);
        dumper.setPosition(0); // TODO: fill with accurate number
    }
};
