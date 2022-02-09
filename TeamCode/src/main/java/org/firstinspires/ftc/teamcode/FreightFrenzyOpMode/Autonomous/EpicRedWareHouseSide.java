package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.Drivetrain;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.DuckDetector;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.OuttakeBucket;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.Sucker;
import org.firstinspires.ftc.teamcode.FullBase;

@Autonomous(name="CameraRedWarehouse")
public class EpicRedWareHouseSide extends LinearOpMode {
    FullBase base;

    public static final double INCHES_TO_HUB_TURN = -19;
    public static final double INCHES_TO_HUB = -36;
    public static final double COMMON_POS_TO_WALL = 30;
    public static final double INCHES_TO_DEPOSIT_BOTTOM = 4;
    public static final double INCHES_TO_DEPOSIT_MIDDLE = -9;
    public static final double INCHES_TO_DEPOSIT_TOP = -6;
    public static final double INCHES_TO_MIDDLE = -8.25;
    public static final double TURN_TO_FACE_HUB = -40;
    public static final double INCHES_TO_PARK = 25;
    public static final double LEFT_WHEEL_ERROR = 29.0/55.0;

    public static final int STRAIGHT = 0;
    @Override
    public void runOpMode(){
        base = new FullBase(telemetry, this, hardwareMap, false);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        base.init();
        base.getTelemetry().addLine("Initialization Complete");
        base.getTelemetry().update();
        waitForStart();

       // base.duckDetector.takePicture();

        switch(/*base.duckDetector.mostDuckyArea()*/ DuckDetector.DuckLocation.LEFT){
            case LEFT: {
                // bottom
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        -INCHES_TO_DEPOSIT_BOTTOM * LEFT_WHEEL_ERROR, -INCHES_TO_DEPOSIT_BOTTOM, -INCHES_TO_DEPOSIT_BOTTOM, -INCHES_TO_DEPOSIT_BOTTOM, STRAIGHT, 0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        INCHES_TO_HUB * LEFT_WHEEL_ERROR, -INCHES_TO_HUB, -INCHES_TO_HUB, INCHES_TO_HUB,STRAIGHT,0);
                base.sucker.setArmPosition(Sucker.Position.START_POSITION, .3);
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.DUMPED);
                sleep(1000);
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.NEUTRAL);
                base.sucker.setArmPosition(Sucker.Position.OUTTAKE_POSITION, .3);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        STRAFE_TO_PARK * LEFT_WHEEL_ERROR, -STRAFE_TO_PARK, -STRAFE_TO_PARK, -STRAFE_TO_PARK,TURN_TO_CAROUSEL,0);

            }; break;

            case MIDDLE: {
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, INCHES_TO_MIDDLE, INCHES_TO_MIDDLE, INCHES_TO_MIDDLE, INCHES_TO_MIDDLE, TURN_TO_FACE_HUB, 0);
                base.outtakeBucket.slide(OuttakeBucket.MIDDLE);
                while(base.outtakeBucket.slider.isBusy());
                base.outtakeBucket.dump(true);
                sleep(300);
                base.outtakeBucket.slide(OuttakeBucket.BOTTOM);
                while(base.outtakeBucket.slider.isBusy());
                base.outtakeBucket.dump(true);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, -INCHES_TO_MIDDLE, -INCHES_TO_MIDDLE, -INCHES_TO_MIDDLE, -INCHES_TO_MIDDLE, TURN_TO_FACE_HUB, 0);


            }; break;

            case RIGHT:  {
                //top
                base.outtakeBucket.slide(OuttakeBucket.TOP);
                while(base.outtakeBucket.slider.isBusy());
                base.outtakeBucket.dump(true);
                sleep(300);
                base.outtakeBucket.slide(OuttakeBucket.BOTTOM);
                while(base.outtakeBucket.slider.isBusy());
                base.outtakeBucket.dump(true);

            };
        }
        base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, STRAIGHT);
        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                INCHES_TO_HUB_TURN * LEFT_WHEEL_ERROR, -INCHES_TO_HUB_TURN, -INCHES_TO_HUB_TURN, INCHES_TO_HUB_TURN, STRAIGHT, 0);
        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, INCHES_TO_PARK * LEFT_WHEEL_ERROR, INCHES_TO_PARK, INCHES_TO_PARK, INCHES_TO_PARK, STRAIGHT,0);

    }
}
