package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.Drivetrain;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.DuckDetector;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.OuttakeBucket;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.Sucker;
import org.firstinspires.ftc.teamcode.FullBase;

@Autonomous(name="CameraBlueWarehouse")
public class EpicBlueWareHouseSide extends LinearOpMode{
    FullBase base;

    public static final double INCHES_TO_HUB = -33;
    public static final double COMMON_POS_TO_WALL = 0;
    public static final double INCHES_TO_DEPOSIT_BOTTOM = 2;
    public static final double INCHES_TO_DEPOSIT_MIDDLE = 1;
    public static final double INCHES_TO_DEPOSIT_TOP = 3;
    public static final double INCHES_TO_PARK = 32;
    //    public static final double STRAFE_TO_PARK = 5;
    public static final int STRAIGHT = 0;
    public static final int FORWARDS_ALT = -360;
    public static final int BACKWARDS = -180;
    public static final double LEFT_WHEEL_ERROR = 1 /** 29.0/55.0*/;

    @Override
    public void runOpMode(){
        base = new FullBase(telemetry, this, hardwareMap, false,false,true);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        base.init();
        base.getTelemetry().addLine("Initialization Complete");
        base.getTelemetry().update();
        waitForStart();
        base.sucker.setArmPosition(Sucker.Position.START_POSITION, .7);
        sleep(300);
        //base.duckDetector.takePicture();
//        base.drivetrain.moveInches(Drivetrain.DRIVE_SPEED,
//                -INCHES_TO_HUB_TURN, INCHES_TO_HUB_TURN, INCHES_TO_HUB_TURN, -INCHES_TO_HUB_TURN);
//        base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, TURN_TO_FACE_HUB);
        switch(base.duckDetector.mostDuckyArea()){
            case LEFT: {
                // bottom
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, INCHES_TO_BOTTOM, INCHES_TO_BOTTOM, INCHES_TO_BOTTOM, INCHES_TO_BOTTOM, TURN_TO_FACE_HUB, 0);
//                base.outtakeBucket.dump(true);
//                sleep(300);
//                base.outtakeBucket.dump(true);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, -INCHES_TO_BOTTOM, -INCHES_TO_BOTTOM, -INCHES_TO_BOTTOM, -INCHES_TO_BOTTOM, TURN_TO_FACE_HUB, 0);
                base.drivetrain.gyroDrive(.4,
                        -INCHES_TO_HUB * LEFT_WHEEL_ERROR, INCHES_TO_HUB, INCHES_TO_HUB, -INCHES_TO_HUB,STRAIGHT,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -INCHES_TO_DEPOSIT_BOTTOM * LEFT_WHEEL_ERROR, -INCHES_TO_DEPOSIT_BOTTOM, -INCHES_TO_DEPOSIT_BOTTOM, -INCHES_TO_DEPOSIT_BOTTOM, STRAIGHT, 0);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        INCHES_TO_DEPOSIT_BOTTOM * LEFT_WHEEL_ERROR, INCHES_TO_DEPOSIT_BOTTOM, INCHES_TO_DEPOSIT_BOTTOM, INCHES_TO_DEPOSIT_BOTTOM, STRAIGHT, 0);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        -(INCHES_TO_DEPOSIT_BOTTOM + 1) * LEFT_WHEEL_ERROR, -INCHES_TO_DEPOSIT_BOTTOM - 1, -INCHES_TO_DEPOSIT_BOTTOM - 1, -INCHES_TO_DEPOSIT_BOTTOM - 1, STRAIGHT, 0);
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.DUMPED);
                sleep(1000);
                base.outtakeBucket.dumper.setPosition(OuttakeBucket.NEUTRAL);
                base.sucker.setArmPosition(Sucker.Position.OUTTAKE_POSITION, .3);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        INCHES_TO_DEPOSIT_BOTTOM * LEFT_WHEEL_ERROR, INCHES_TO_DEPOSIT_BOTTOM, INCHES_TO_DEPOSIT_BOTTOM, INCHES_TO_DEPOSIT_BOTTOM, STRAIGHT, 0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, BACKWARDS);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        INCHES_TO_DEPOSIT_MIDDLE * LEFT_WHEEL_ERROR, INCHES_TO_DEPOSIT_MIDDLE, INCHES_TO_DEPOSIT_MIDDLE, INCHES_TO_DEPOSIT_MIDDLE, BACKWARDS, 0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -(INCHES_TO_HUB - 6) * LEFT_WHEEL_ERROR, INCHES_TO_HUB - 7, INCHES_TO_HUB - 7, -(INCHES_TO_HUB - 7),BACKWARDS,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -INCHES_TO_PARK * LEFT_WHEEL_ERROR, -INCHES_TO_PARK, -INCHES_TO_PARK, -INCHES_TO_PARK, BACKWARDS, 0);


            }; break;

            case MIDDLE: {
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, INCHES_TO_MIDDLE, INCHES_TO_MIDDLE, INCHES_TO_MIDDLE, INCHES_TO_MIDDLE, TURN_TO_FACE_HUB, 0);
//                base.outtakeBucket.slide(OuttakeBucket.MIDDLE);
//                while(base.outtakeBucket.slider.isBusy());
//                base.outtakeBucket.dump(true);
//                sleep(300);
//                base.outtakeBucket.slide(OuttakeBucket.BOTTOM);
//                while(base.outtakeBucket.slider.isBusy());
//                base.outtakeBucket.dump(true);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, -INCHES_TO_MIDDLE, -INCHES_TO_MIDDLE, -INCHES_TO_MIDDLE, -INCHES_TO_MIDDLE, TURN_TO_FACE_HUB, 0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -INCHES_TO_HUB * LEFT_WHEEL_ERROR, INCHES_TO_HUB, INCHES_TO_HUB, -INCHES_TO_HUB,STRAIGHT,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -INCHES_TO_DEPOSIT_MIDDLE * LEFT_WHEEL_ERROR, -INCHES_TO_DEPOSIT_MIDDLE, -INCHES_TO_DEPOSIT_MIDDLE, -INCHES_TO_DEPOSIT_MIDDLE, STRAIGHT, 0);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        INCHES_TO_DEPOSIT_BOTTOM * LEFT_WHEEL_ERROR, INCHES_TO_DEPOSIT_BOTTOM, INCHES_TO_DEPOSIT_BOTTOM, INCHES_TO_DEPOSIT_BOTTOM, STRAIGHT, 0);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        -(INCHES_TO_DEPOSIT_BOTTOM + 1) * LEFT_WHEEL_ERROR, -INCHES_TO_DEPOSIT_BOTTOM - 1, -INCHES_TO_DEPOSIT_BOTTOM - 1, -INCHES_TO_DEPOSIT_BOTTOM - 1, STRAIGHT, 0);
                base.outtakeBucket.slider.setPower(base.outtakeBucket.UP_POWER);
                base.outtakeBucket.slide(OuttakeBucket.MIDDLE);
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
                        -5 * LEFT_WHEEL_ERROR, 5, 5, -5,STRAIGHT,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        1 * LEFT_WHEEL_ERROR, 1, 1, 1, STRAIGHT, 0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, BACKWARDS);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        INCHES_TO_DEPOSIT_MIDDLE * LEFT_WHEEL_ERROR, INCHES_TO_DEPOSIT_MIDDLE, INCHES_TO_DEPOSIT_MIDDLE, INCHES_TO_DEPOSIT_MIDDLE, BACKWARDS, 0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -(INCHES_TO_HUB - 8) * LEFT_WHEEL_ERROR, INCHES_TO_HUB - 8, INCHES_TO_HUB - 8, -(INCHES_TO_HUB - 8),BACKWARDS,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -INCHES_TO_PARK * LEFT_WHEEL_ERROR, -INCHES_TO_PARK, -INCHES_TO_PARK, -INCHES_TO_PARK, BACKWARDS, 0);


            }; break;

            case RIGHT:  {
                //top
//                base.outtakeBucket.slide(OuttakeBucket.TOP);
//                while(base.outtakeBucket.slider.isBusy());
//                base.outtakeBucket.dump(true);
//                sleep(300);
//                base.outtakeBucket.slide(OuttakeBucket.BOTTOM);
//                while(base.outtakeBucket.slider.isBusy());
//                base.outtakeBucket.dump(true);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -INCHES_TO_HUB - 4 * LEFT_WHEEL_ERROR, INCHES_TO_HUB + 4, INCHES_TO_HUB + 4, -INCHES_TO_HUB - 4,STRAIGHT,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        INCHES_TO_DEPOSIT_TOP * LEFT_WHEEL_ERROR, INCHES_TO_DEPOSIT_TOP, INCHES_TO_DEPOSIT_TOP, INCHES_TO_DEPOSIT_TOP, STRAIGHT, 0);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        INCHES_TO_DEPOSIT_BOTTOM * LEFT_WHEEL_ERROR, INCHES_TO_DEPOSIT_BOTTOM, INCHES_TO_DEPOSIT_BOTTOM, INCHES_TO_DEPOSIT_BOTTOM, STRAIGHT, 0);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        -(INCHES_TO_DEPOSIT_BOTTOM + 1) * LEFT_WHEEL_ERROR, -INCHES_TO_DEPOSIT_BOTTOM - 1, -INCHES_TO_DEPOSIT_BOTTOM - 1, -INCHES_TO_DEPOSIT_BOTTOM - 1, STRAIGHT, 0);
                base.outtakeBucket.slider.setPower(base.outtakeBucket.UP_POWER);
                base.outtakeBucket.slide(-700);
                base.getTelemetry().addLine("SLide");
                base.getTelemetry().update();
                sleep(1000);
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
                        1 * LEFT_WHEEL_ERROR, 1, 1, 1, STRAIGHT, 0);
                base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, BACKWARDS);
//                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
//                        INCHES_TO_DEPOSIT_MIDDLE * LEFT_WHEEL_ERROR, INCHES_TO_DEPOSIT_MIDDLE, INCHES_TO_DEPOSIT_MIDDLE, INCHES_TO_DEPOSIT_MIDDLE, BACKWARDS, 0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -(INCHES_TO_HUB - 6) * LEFT_WHEEL_ERROR, INCHES_TO_HUB - 6, INCHES_TO_HUB - 6, -(INCHES_TO_HUB - 6),BACKWARDS,0);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        -INCHES_TO_PARK * LEFT_WHEEL_ERROR, -INCHES_TO_PARK, -INCHES_TO_PARK, -INCHES_TO_PARK, BACKWARDS, 0);


            };
        }
//        base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, STRAIGHT);
//        base.drivetrain.moveInches(Drivetrain.DRIVE_SPEED,
//                INCHES_TO_HUB_TURN, -INCHES_TO_HUB_TURN, -INCHES_TO_HUB_TURN, INCHES_TO_HUB_TURN);
//        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, INCHES_TO_PARK, INCHES_TO_PARK, INCHES_TO_PARK, INCHES_TO_PARK, STRAIGHT,0);

    }

}
