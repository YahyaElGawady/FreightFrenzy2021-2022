package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.Drivetrain;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.DuckDetector;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.OuttakeBucket;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.Sucker;
import org.firstinspires.ftc.teamcode.FullBase;

@Autonomous(name="CameraRedDuckie")
public class EpicRedDuckySide extends LinearOpMode {
    FullBase base;

    public static final double INCHES_TO_HUB = -35;
    public static final double COMMON_POS_TO_WALL = 31;
    public static final double INCHES_TO_DEPOSIT_BOTTOM = 8;
    public static final double INCHES_TO_DEPOSIT_MIDDLE = 8;
    public static final double INCHES_TO_DEPOSIT_TOP = 16;
    public static final double TURN_TO_CAROUSEL = 80;
    public static final double INCHES_TO_CAROUSEL = 13;
    public static final double INCHES_TO_PARK = 18;
    public static final double STRAFE_TO_PARK = 5;
    public static final int STRAIGHT = 0;
    public static final double LEFT_WHEEL_ERROR = 29.0/55.0;
    @Override
    public void runOpMode(){
        base = new FullBase(telemetry, this, hardwareMap, false, true);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        base.init();
        base.getTelemetry().addLine("Initialization Complete");
        base.getTelemetry().update();
        waitForStart();
        base.sucker.setArmPosition(Sucker.Position.START_POSITION, .3);
        //base.duckDetector.takePicture();
        switch(/*base.duckDetector.mostDuckyArea()*/ DuckDetector.DuckLocation.LEFT){
            case LEFT: {
                // bottom
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -INCHES_TO_DEPOSIT_BOTTOM * LEFT_WHEEL_ERROR, -INCHES_TO_DEPOSIT_BOTTOM, -INCHES_TO_DEPOSIT_BOTTOM, -INCHES_TO_DEPOSIT_BOTTOM, STRAIGHT, 0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -INCHES_TO_HUB * LEFT_WHEEL_ERROR, INCHES_TO_HUB, INCHES_TO_HUB, -INCHES_TO_HUB,STRAIGHT,0);
                base.sucker.setArmPosition(Sucker.Position.START_POSITION, .3);
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.DUMPED);
                sleep(1000);
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.NEUTRAL);
                base.sucker.setArmPosition(Sucker.Position.OUTTAKE_POSITION, .3);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM) * LEFT_WHEEL_ERROR, (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM),
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM), STRAIGHT, 0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, TURN_TO_CAROUSEL);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, (INCHES_TO_CAROUSEL + INCHES_TO_PARK)*LEFT_WHEEL_ERROR, INCHES_TO_CAROUSEL + INCHES_TO_PARK,
                        INCHES_TO_CAROUSEL + INCHES_TO_PARK, INCHES_TO_CAROUSEL + INCHES_TO_PARK, TURN_TO_CAROUSEL, 0);
                base.duckeySpinner.spin(.5,  0);
                sleep(2000);
                base.duckeySpinner.spin(0,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, (-INCHES_TO_PARK)*LEFT_WHEEL_ERROR, -INCHES_TO_PARK,
                         -INCHES_TO_PARK, -INCHES_TO_PARK, TURN_TO_CAROUSEL, 0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        STRAFE_TO_PARK * LEFT_WHEEL_ERROR, -STRAFE_TO_PARK, -STRAFE_TO_PARK, -STRAFE_TO_PARK,TURN_TO_CAROUSEL,0);
            }; break;

            case MIDDLE: {
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        INCHES_TO_DEPOSIT_MIDDLE, INCHES_TO_DEPOSIT_MIDDLE, INCHES_TO_DEPOSIT_MIDDLE, INCHES_TO_DEPOSIT_MIDDLE, STRAIGHT, 0);
                base.outtakeBucket.slide(OuttakeBucket.MIDDLE);
                while(base.outtakeBucket.slider.isBusy());
                base.outtakeBucket.dump(true);
                sleep(300);
                base.outtakeBucket.slide(OuttakeBucket.BOTTOM);
                while(base.outtakeBucket.slider.isBusy());
                base.outtakeBucket.dump(true);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_MIDDLE), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_MIDDLE),
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_MIDDLE), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_MIDDLE), STRAIGHT, 0);
            }; break;

            case RIGHT:  {
                //top
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        INCHES_TO_DEPOSIT_TOP, INCHES_TO_DEPOSIT_TOP, INCHES_TO_DEPOSIT_TOP, INCHES_TO_DEPOSIT_TOP, STRAIGHT, 0);
                base.outtakeBucket.slide(OuttakeBucket.TOP);
                while(base.outtakeBucket.slider.isBusy());
                base.outtakeBucket.dump(true);
                sleep(300);
                base.outtakeBucket.slide(OuttakeBucket.BOTTOM);
                while(base.outtakeBucket.slider.isBusy());
                base.outtakeBucket.dump(true);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP),
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP), STRAIGHT, 0);
            };
        }
//        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                INCHES_TO_CAROUSEL, INCHES_TO_CAROUSEL, INCHES_TO_CAROUSEL, INCHES_TO_CAROUSEL, TURN_TO_CAROUSEL, 0);
//        base.duckeySpinner.spinner.setPower(.8);
//        sleep(500);
//        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, -INCHES_TO_PARK, -INCHES_TO_PARK, -INCHES_TO_PARK, -INCHES_TO_PARK, STRAIGHT, 0);
    }

}
