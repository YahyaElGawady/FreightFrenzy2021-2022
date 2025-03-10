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

    public static final double INCHES_TO_HUB = - 42;
    public static final double COMMON_POS_TO_WALL = 30;
    public static final double INCHES_TO_DEPOSIT_BOTTOM = 7;
    public static final double INCHES_TO_DEPOSIT_MIDDLE = -5;
    public static final double INCHES_TO_DEPOSIT_TOP = -2;
    public static final int TURN_TO_CAROUSEL = 85;
    public static final double INCHES_TO_CAROUSEL = 17.4;
    public static final double INCHES_TO_PARK = 12;
//    public static final double STRAFE_TO_PARK = 5;
    public static final int STRAIGHT = 0;
    public static final double LEFT_WHEEL_ERROR = 1 /**29.0/55.0*/;
    @Override
    public void runOpMode(){
        base = new FullBase(telemetry, this, hardwareMap, false, true,true);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        base.init();
        base.getTelemetry().addLine("Initialization Complete");
        base.getTelemetry().update();
        waitForStart();
        base.sucker.setArmPosition(Sucker.Position.START_POSITION, .3);
        sleep(300);
        //base.duckDetector.takePicture();
        switch(base.duckDetector.mostDuckyArea()){
            case LEFT: {
                // bottom
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -INCHES_TO_DEPOSIT_BOTTOM * LEFT_WHEEL_ERROR, -INCHES_TO_DEPOSIT_BOTTOM, -INCHES_TO_DEPOSIT_BOTTOM, -INCHES_TO_DEPOSIT_BOTTOM, STRAIGHT, 0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -(INCHES_TO_HUB + 2)* LEFT_WHEEL_ERROR, INCHES_TO_HUB + 2, INCHES_TO_HUB + 2, -INCHES_TO_HUB -2,STRAIGHT,0);
                //base.sucker.setArmPosition(Sucker.Position.START_POSITION, .3);
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.DUMPED);
                sleep(1000);
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.NEUTRAL);
                sleep(500);
                base.sucker.setArmPosition(Sucker.Position.OUTTAKE_POSITION, .3);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM) * LEFT_WHEEL_ERROR, (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM),
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM), STRAIGHT, 0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, TURN_TO_CAROUSEL);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED-.1, (INCHES_TO_CAROUSEL + INCHES_TO_PARK - 7)*LEFT_WHEEL_ERROR, INCHES_TO_CAROUSEL + INCHES_TO_PARK - 7,
                        INCHES_TO_CAROUSEL + INCHES_TO_PARK - 7, INCHES_TO_CAROUSEL + INCHES_TO_PARK - 7, TURN_TO_CAROUSEL, 0);
                base.duckeySpinner.spin(.3,  0);
                sleep(4000);
                base.duckeySpinner.spin(0,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, (-INCHES_TO_PARK)*LEFT_WHEEL_ERROR, -INCHES_TO_PARK,
                         -INCHES_TO_PARK, -INCHES_TO_PARK, TURN_TO_CAROUSEL, 0);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        STRAFE_TO_PARK * LEFT_WHEEL_ERROR, -STRAFE_TO_PARK, -STRAFE_TO_PARK, -STRAFE_TO_PARK,TURN_TO_CAROUSEL,0);
            }; break;

            case MIDDLE: {
//                        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                                -(INCHES_TO_HUB + 2)* LEFT_WHEEL_ERROR, INCHES_TO_HUB + 2, INCHES_TO_HUB + 2, -INCHES_TO_HUB -2,STRAIGHT,0);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        INCHES_TO_DEPOSIT_MIDDLE * LEFT_WHEEL_ERROR, INCHES_TO_DEPOSIT_MIDDLE, INCHES_TO_DEPOSIT_MIDDLE, INCHES_TO_DEPOSIT_MIDDLE, STRAIGHT, 0);
//                base.outtakeBucket.slider.setPower(base.outtakeBucket.UP_POWER);
//                base.outtakeBucket.slide(OuttakeBucket.MIDDLE);
//                while(base.outtakeBucket.slider.isBusy());
//                base.outtakeBucket.dumper.setPosition(OuttakeBucket.DUMPED);
//                sleep(1000);
//                base.outtakeBucket.slide(OuttakeBucket.BOTTOM);
//                while(base.outtakeBucket.slider.isBusy());
//                base.outtakeBucket.dumper.setPosition(OuttakeBucket.NEUTRAL);
//                sleep(200);
//                base.sucker.setArmPosition(Sucker.Position.OUTTAKE_POSITION, .3);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_MIDDLE - 12) * LEFT_WHEEL_ERROR, (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_MIDDLE- 12),
//                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_MIDDLE - 12), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_MIDDLE - 12), STRAIGHT, 0);
//                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, TURN_TO_CAROUSEL + 6);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED-.1, (INCHES_TO_CAROUSEL + INCHES_TO_PARK )*LEFT_WHEEL_ERROR, INCHES_TO_CAROUSEL + INCHES_TO_PARK ,
//                        INCHES_TO_CAROUSEL + INCHES_TO_PARK , INCHES_TO_CAROUSEL + INCHES_TO_PARK , TURN_TO_CAROUSEL, 0);
//                base.duckeySpinner.spin(.3,  0);
//                sleep(3500);
//                base.duckeySpinner.spin(0,0);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, (-INCHES_TO_PARK)*LEFT_WHEEL_ERROR, -INCHES_TO_PARK,
//                        -INCHES_TO_PARK, -INCHES_TO_PARK, TURN_TO_CAROUSEL, 0);
                base.drivetrain.gyroDrive(.5,
                        -(INCHES_TO_HUB + 2)* LEFT_WHEEL_ERROR, INCHES_TO_HUB + 2, INCHES_TO_HUB + 2, -INCHES_TO_HUB -2,STRAIGHT,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED + .2,
                        (INCHES_TO_DEPOSIT_MIDDLE - 2)* LEFT_WHEEL_ERROR, INCHES_TO_DEPOSIT_MIDDLE - 2 , INCHES_TO_DEPOSIT_MIDDLE - 2, INCHES_TO_DEPOSIT_MIDDLE - 2, STRAIGHT, 0);
                base.outtakeBucket.slider.setPower(base.outtakeBucket.UP_POWER);
                base.outtakeBucket.slide(-150);
                base.getTelemetry().addLine("SLide");
                base.getTelemetry().update();
                while(base.outtakeBucket.slider.isBusy());
                base.getTelemetry().addLine("Done Sliding");
                base.getTelemetry().update();
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.DUMPED);
                base.getTelemetry().addLine("Dumping");
                base.getTelemetry().update();
                //base.sucker.setArmPosition(Sucker.Position.INTAKE_POSITION, .3);
                sleep(1000);
                base.outtakeBucket.slide(-10);
                base.getTelemetry().addLine("SLide");
                base.getTelemetry().update();
                while(base.outtakeBucket.slider.isBusy());
                base.getTelemetry().addLine("Sliding down");
                base.getTelemetry().update();
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.NEUTRAL);
                base.getTelemetry().addLine("Undump");
                base.getTelemetry().update();
                sleep(400);
                base.sucker.setArmPosition(Sucker.Position.OUTTAKE_POSITION, .3);
                base.drivetrain.gyroDrive(.5,
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 12) * LEFT_WHEEL_ERROR, (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 12),
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 12 ), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 12 ), STRAIGHT, 0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, TURN_TO_CAROUSEL + 8);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED-.1, (INCHES_TO_CAROUSEL + INCHES_TO_PARK - 6)*LEFT_WHEEL_ERROR, INCHES_TO_CAROUSEL + INCHES_TO_PARK  - 6,
                        INCHES_TO_CAROUSEL + INCHES_TO_PARK - 6, INCHES_TO_CAROUSEL + INCHES_TO_PARK - 6, TURN_TO_CAROUSEL + 6, 0);
                base.duckeySpinner.spin(.3,  0);
                sleep(4000);
                base.duckeySpinner.spin(0,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, (-INCHES_TO_PARK )*LEFT_WHEEL_ERROR, -INCHES_TO_PARK,
                        -INCHES_TO_PARK, -INCHES_TO_PARK, TURN_TO_CAROUSEL + 8, 0);
            }; break;

            case RIGHT:  {
                //top
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -(INCHES_TO_HUB + 2)* LEFT_WHEEL_ERROR, INCHES_TO_HUB + 2, INCHES_TO_HUB + 2, -INCHES_TO_HUB -2,STRAIGHT,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED + .2,
                        INCHES_TO_DEPOSIT_TOP - 1.3 * LEFT_WHEEL_ERROR, INCHES_TO_DEPOSIT_TOP - 1.3, INCHES_TO_DEPOSIT_TOP - 1.3, INCHES_TO_DEPOSIT_TOP - 1.3, STRAIGHT, 0);
                base.outtakeBucket.slider.setPower(base.outtakeBucket.UP_POWER);
                base.outtakeBucket.slide(-510);
                base.getTelemetry().addLine("SLide");
                base.getTelemetry().update();
                while(base.outtakeBucket.slider.isBusy());
                base.getTelemetry().addLine("Done Sliding");
                base.getTelemetry().update();
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.DUMPED);
                base.getTelemetry().addLine("Dumping");
                base.getTelemetry().update();
                //base.sucker.setArmPosition(Sucker.Position.INTAKE_POSITION, .3);
                sleep(1000);
                base.outtakeBucket.slide(10);
                base.getTelemetry().addLine("SLide");
                base.getTelemetry().update();
                while(base.outtakeBucket.slider.isBusy());
                base.getTelemetry().addLine("Sliding down");
                base.getTelemetry().update();
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.NEUTRAL);
                base.getTelemetry().addLine("Undump");
                base.getTelemetry().update();
                sleep(400);
                base.sucker.setArmPosition(Sucker.Position.OUTTAKE_POSITION, .3);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 12) * LEFT_WHEEL_ERROR, (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 12),
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 12 ), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 12 ), STRAIGHT, 0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, TURN_TO_CAROUSEL + 8);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED-.1, (INCHES_TO_CAROUSEL + INCHES_TO_PARK - 7)*LEFT_WHEEL_ERROR, INCHES_TO_CAROUSEL + INCHES_TO_PARK  - 7,
                        INCHES_TO_CAROUSEL + INCHES_TO_PARK - 7, INCHES_TO_CAROUSEL + INCHES_TO_PARK - 7, TURN_TO_CAROUSEL + 7, 0);
                base.duckeySpinner.spin(.15,  0);
                sleep(4000);
                base.duckeySpinner.spin(0,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, (-INCHES_TO_PARK )*LEFT_WHEEL_ERROR, -INCHES_TO_PARK,
                        -INCHES_TO_PARK, -INCHES_TO_PARK, TURN_TO_CAROUSEL + 8, 0);
            };
        }
//        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                INCHES_TO_CAROUSEL, INCHES_TO_CAROUSEL, INCHES_TO_CAROUSEL, INCHES_TO_CAROUSEL, TURN_TO_CAROUSEL, 0);
//        base.duckeySpinner.spinner.setPower(.8);
//        sleep(500);
//        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, -INCHES_TO_PARK, -INCHES_TO_PARK, -INCHES_TO_PARK, -INCHES_TO_PARK, STRAIGHT, 0);
    }

}
