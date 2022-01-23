package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.Drivetrain;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.OuttakeBucket;
import org.firstinspires.ftc.teamcode.FullBase;

@Autonomous(name="CameraRedDuckie")
public class EpicRedDuckySide extends LinearOpMode {
    FullBase base;

    public static final double INCHES_TO_HUB = -43.5;
    public static final double COMMON_POS_TO_WALL = 25;
    public static final double INCHES_TO_DEPOSIT_BOTTOM = -3;
    public static final double INCHES_TO_DEPOSIT_MIDDLE = 8;
    public static final double INCHES_TO_DEPOSIT_TOP = 16;
    public static final double TURN_TO_CAROUSEL = 90;
    public static final double INCHES_TO_CAROUSEL = 17;
    public static final double INCHES_TO_PARK = 18;
    public static final int STRAIGHT = 0;
    @Override
    public void runOpMode(){
        base = new FullBase(telemetry, this, hardwareMap, false, true);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        base.init();
        base.getTelemetry().addLine("Initialization Complete");
        base.getTelemetry().update();
        waitForStart();

        //base.duckDetector.takePicture();
        base.drivetrain.moveInches(Drivetrain.DRIVE_SPEED,
                -INCHES_TO_HUB, INCHES_TO_HUB, INCHES_TO_HUB, -INCHES_TO_HUB);
        switch(base.duckDetector.mostDuckyArea()){
            case LEFT: {
                // bottom
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        INCHES_TO_DEPOSIT_BOTTOM, INCHES_TO_DEPOSIT_BOTTOM, INCHES_TO_DEPOSIT_BOTTOM, INCHES_TO_DEPOSIT_BOTTOM, STRAIGHT, 0);
                base.outtakeBucket.dump(true);
                sleep(300);
                base.outtakeBucket.dump(true);
                base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM),
                        (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM), (COMMON_POS_TO_WALL - INCHES_TO_DEPOSIT_BOTTOM), STRAIGHT, 0);
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
        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED,
                INCHES_TO_CAROUSEL, INCHES_TO_CAROUSEL, INCHES_TO_CAROUSEL, INCHES_TO_CAROUSEL, TURN_TO_CAROUSEL, 0);
        base.duckeySpinner.spinner.setPower(.8);
        sleep(500);
        base.drivetrain.gyroDrive(Drivetrain.DRIVE_SPEED, -INCHES_TO_PARK, -INCHES_TO_PARK, -INCHES_TO_PARK, -INCHES_TO_PARK, STRAIGHT, 0);
    }

}
