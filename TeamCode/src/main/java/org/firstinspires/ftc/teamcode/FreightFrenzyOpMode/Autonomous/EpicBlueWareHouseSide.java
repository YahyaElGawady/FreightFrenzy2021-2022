package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.Drivetrain;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.OuttakeBucket;
import org.firstinspires.ftc.teamcode.FullBase;

@Autonomous(name="CameraBlueWarehouse")
public class EpicBlueWareHouseSide extends LinearOpMode{
    FullBase base;

    public static final double INCHES_TO_HUB_TURN = 15;
    public static final double INCHES_TO_BOTTOM = -16.5;
    public static final double INCHES_TO_MIDDLE = -8.25;
    public static final double TURN_TO_FACE_HUB = 40;
    public static final double INCHES_TO_PARK = 25;

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

        base.duckDetector.takePicture();
        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                -INCHES_TO_HUB_TURN, INCHES_TO_HUB_TURN, INCHES_TO_HUB_TURN, -INCHES_TO_HUB_TURN, STRAIGHT, 0);
        base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, TURN_TO_FACE_HUB);
        switch(base.duckDetector.mostDuckyArea()){
            case LEFT: {
                // bottom
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, INCHES_TO_BOTTOM, INCHES_TO_BOTTOM, INCHES_TO_BOTTOM, INCHES_TO_BOTTOM, TURN_TO_FACE_HUB, 0);
                base.outtakeBucket.dump(true);
                sleep(300);
                base.outtakeBucket.dump(true);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, -INCHES_TO_BOTTOM, -INCHES_TO_BOTTOM, -INCHES_TO_BOTTOM, -INCHES_TO_BOTTOM, TURN_TO_FACE_HUB, 0);


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
                INCHES_TO_HUB_TURN, -INCHES_TO_HUB_TURN, -INCHES_TO_HUB_TURN, INCHES_TO_HUB_TURN, STRAIGHT, 0);
        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, INCHES_TO_PARK, INCHES_TO_PARK, INCHES_TO_PARK, INCHES_TO_PARK, STRAIGHT,0);

    }

}
