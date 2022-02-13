package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.Drivetrain;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.DuckDetector;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.OuttakeBucket;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.Sucker;
import org.firstinspires.ftc.teamcode.FullBase;
@Autonomous(name="CameraBlueDuckie")
public class EpicBlueDuckySide extends LinearOpMode{
    FullBase base;

    public static final double INCHES_TO_HUB = -36;
    public static final double COMMON_POS_TO_WALL = 25;
    public static final double INCHES_TO_DEPOSIT_BOTTOM = -3.5 ;
    public static final double INCHES_TO_DEPOSIT_MIDDLE = -5
            ;
    public static final double INCHES_TO_DEPOSIT_TOP = -2;
    public static final int TURN_TO_CAROUSEL = 85;
    public static final double INCHES_TO_CAROUSEL = 17.4;
    public static final double INCHES_TO_PARK = 12;
    //    public static final double STRAFE_TO_PARK = 5;
    public static final int STRAIGHT = 0;
    public static final int FORWARDS_ALT = -360;
    public static final int BACKWARDS = -180;
    public static final double LEFT_WHEEL_ERROR = 1 /**29.0/55.0*/;
    @Override
    public void runOpMode(){
        base = new FullBase(telemetry, this, hardwareMap, false);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        base.init();
        base.getTelemetry().addLine("Initialization Complete");
        base.getTelemetry().update();
        waitForStart();
        base.sucker.setArmPosition(Sucker.Position.START_POSITION, 1);
        sleep(300);
       // base.duckDetector.s();
//        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                -INCHES_TO_HUB, INCHES_TO_HUB, INCHES_TO_HUB, -INCHES_TO_HUB, STRAIGHT, 0);
        switch(base.duckDetector.mostDuckyArea()){
            case LEFT: {
                // bottom
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -INCHES_TO_HUB * LEFT_WHEEL_ERROR, INCHES_TO_HUB, INCHES_TO_HUB, -INCHES_TO_HUB,STRAIGHT,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -INCHES_TO_DEPOSIT_BOTTOM * LEFT_WHEEL_ERROR, -INCHES_TO_DEPOSIT_BOTTOM, -INCHES_TO_DEPOSIT_BOTTOM, -INCHES_TO_DEPOSIT_BOTTOM, STRAIGHT, 0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, BACKWARDS);
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.DUMPED);
                sleep(1000);
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.NEUTRAL);
                base.sucker.setArmPosition(Sucker.Position.OUTTAKE_POSITION, .3);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM - 3) * LEFT_WHEEL_ERROR, (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM - 3),
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM - 3 ), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM - 3), BACKWARDS, 0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, TURN_TO_CAROUSEL);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED-.1, (INCHES_TO_CAROUSEL + INCHES_TO_PARK - 6)*LEFT_WHEEL_ERROR, INCHES_TO_CAROUSEL + INCHES_TO_PARK - 6,
                        INCHES_TO_CAROUSEL + INCHES_TO_PARK - 6, INCHES_TO_CAROUSEL + INCHES_TO_PARK - 6, TURN_TO_CAROUSEL, 0);
                base.duckeySpinner.spin(0,  .3);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, 140);
                sleep(4000);
                base.duckeySpinner.spin(0,0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, TURN_TO_CAROUSEL);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, (-INCHES_TO_PARK)*LEFT_WHEEL_ERROR, -INCHES_TO_PARK,
                        -INCHES_TO_PARK, -INCHES_TO_PARK, TURN_TO_CAROUSEL, 0);

            }; break;

            case MIDDLE: {
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -INCHES_TO_HUB * LEFT_WHEEL_ERROR, INCHES_TO_HUB, INCHES_TO_HUB, -INCHES_TO_HUB,STRAIGHT,0);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        -INCHES_TO_DEPOSIT_MIDDLE * LEFT_WHEEL_ERROR, -INCHES_TO_DEPOSIT_MIDDLE, -INCHES_TO_DEPOSIT_MIDDLE, -INCHES_TO_DEPOSIT_MIDDLE, STRAIGHT, 0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, BACKWARDS);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        INCHES_TO_DEPOSIT_MIDDLE * LEFT_WHEEL_ERROR, INCHES_TO_DEPOSIT_MIDDLE, INCHES_TO_DEPOSIT_MIDDLE, INCHES_TO_DEPOSIT_MIDDLE, STRAIGHT, 0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        (INCHES_TO_DEPOSIT_MIDDLE) * LEFT_WHEEL_ERROR, INCHES_TO_DEPOSIT_MIDDLE , INCHES_TO_DEPOSIT_MIDDLE , INCHES_TO_DEPOSIT_MIDDLE , BACKWARDS, 0);
                base.outtakeBucket.slider.setPower(base.outtakeBucket.UP_POWER);
                base.outtakeBucket.slide(OuttakeBucket.MIDDLE);
                while(base.outtakeBucket.slider.isBusy());
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.DUMPED);
                sleep(1000);
                base.outtakeBucket.slide(OuttakeBucket.BOTTOM);
                while(base.outtakeBucket.slider.isBusy());
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.NEUTRAL);
                sleep(200);
                base.sucker.setArmPosition(Sucker.Position.OUTTAKE_POSITION, .3);
                base.sucker.setArmPosition(Sucker.Position.OUTTAKE_POSITION, .3);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 3) * LEFT_WHEEL_ERROR, (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 3),
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 3 ), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 3 ), BACKWARDS, 0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, TURN_TO_CAROUSEL + 15);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED-.1, (INCHES_TO_CAROUSEL + INCHES_TO_PARK - 2)*LEFT_WHEEL_ERROR, INCHES_TO_CAROUSEL + INCHES_TO_PARK  - 2,
                        INCHES_TO_CAROUSEL + INCHES_TO_PARK -2, INCHES_TO_CAROUSEL + INCHES_TO_PARK - 2, TURN_TO_CAROUSEL + 7, 0);
                base.duckeySpinner.spin(0,  .3);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, 110);
                sleep(4000);
                base.duckeySpinner.spin(0,0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, TURN_TO_CAROUSEL);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, (-INCHES_TO_PARK  - 3)*LEFT_WHEEL_ERROR, -INCHES_TO_PARK - 3,
                        -INCHES_TO_PARK - 3, -INCHES_TO_PARK - 3, TURN_TO_CAROUSEL + 8, 0);

            }; break;

            case RIGHT:  {
                //top
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -(INCHES_TO_HUB - 1)* LEFT_WHEEL_ERROR, (INCHES_TO_HUB - 1), (INCHES_TO_HUB - 1), -(INCHES_TO_HUB - 1),STRAIGHT,0);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        INCHES_TO_DEPOSIT_TOP, INCHES_TO_DEPOSIT_TOP, INCHES_TO_DEPOSIT_TOP, INCHES_TO_DEPOSIT_BOTTOM, STRAIGHT, 0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, BACKWARDS);
                base.outtakeBucket.slider.setPower(base.outtakeBucket.UP_POWER);
                base.outtakeBucket.slide(-700);
                // while(base.outtakeBucket.slider.isBusy());
                sleep(1000);
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.DUMPED);
                //base.sucker.setArmPosition(Sucker.Position.INTAKE_POSITION, .3);
                sleep(1000);
                base.outtakeBucket.slide(0 );
                while(base.outtakeBucket.slider.isBusy())
                    base.outtakeBucket.dumper.setPosition(OuttakeBucket.NEUTRAL);
                sleep(400);
                base.sucker.setArmPosition(Sucker.Position.OUTTAKE_POSITION, .3);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 1) * LEFT_WHEEL_ERROR, (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 1),
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 1), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_TOP - 1), BACKWARDS, 0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, TURN_TO_CAROUSEL + 8);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED-.1, (INCHES_TO_CAROUSEL + INCHES_TO_PARK - 4)*LEFT_WHEEL_ERROR, INCHES_TO_CAROUSEL + INCHES_TO_PARK  - 4,
                        INCHES_TO_CAROUSEL + INCHES_TO_PARK -4, INCHES_TO_CAROUSEL + INCHES_TO_PARK - 4, TURN_TO_CAROUSEL + 7, 0);
                base.duckeySpinner.spin(0,  .3);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, 110);
                sleep(4000);
                base.duckeySpinner.spin(0,0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, TURN_TO_CAROUSEL);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, (-INCHES_TO_PARK - 2 )*LEFT_WHEEL_ERROR, -INCHES_TO_PARK - 2,
                        -INCHES_TO_PARK - 2, -INCHES_TO_PARK - 2, TURN_TO_CAROUSEL + 8, 0);

            };
        }
//        base.drivetrain.moveInches(Drivetrain.DRIVE_SPEED,
//                -INCHES_TO_CAROUSEL, INCHES_TO_CAROUSEL, INCHES_TO_CAROUSEL, -INCHES_TO_CAROUSEL);
//        base.duckeySpinner.spinner.setPower(-.8);
//        sleep(500);
//        base.drivetrain.moveInches(Drivetrain.DRIVE_SPEED, -INCHES_TO_PARK, INCHES_TO_PARK, INCHES_TO_PARK, -INCHES_TO_PARK);
//        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, INCHES_TO_FIX_PARK, INCHES_TO_FIX_PARK, INCHES_TO_FIX_PARK, INCHES_TO_FIX_PARK, STRAIGHT, 0);
    }
}
