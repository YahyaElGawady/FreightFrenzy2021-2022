package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.Drivetrain;
import org.firstinspires.ftc.teamcode.FullBase;
@Autonomous(name="CameraBlueDuckie")
public class EpicBlueDuckySide extends LinearOpMode{
    FullBase base;

    public static final double inches_to_hub = 43.5;
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
        switch(base.duckDetector.mostDuckyArea()){
            case LEFT: {
                // bottom
                base.drivetrain.moveInches(Drivetrain.DRIVE_SPEED,
                        -inches_to_hub, inches_to_hub, inches_to_hub, -inches_to_hub);
            }; break;

            case MIDDLE: {
                base.drivetrain.moveInches(Drivetrain.DRIVE_SPEED, )
            }; break;

            case RIGHT:  {

            };
        }
    }
}
