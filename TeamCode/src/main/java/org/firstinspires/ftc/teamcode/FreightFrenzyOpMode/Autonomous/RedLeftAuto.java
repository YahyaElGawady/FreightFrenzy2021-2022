package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FullBase;
@Disabled
@Autonomous(name="Red_DuckeySpinner_Spin&Park")
public class RedLeftAuto extends LinearOpMode {
    FullBase base;

    @Override
    public void runOpMode(){
        base = new FullBase(telemetry, this, hardwareMap, false);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        base.init();
        base.getTelemetry().addLine("Initialization Complete");
        base.getTelemetry().update();
        waitForStart();
        base.duckeySpinnerSideAuto(1);
//        base.duckDetector.takePicture();
//        base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, 45);
//        base.outtakeBucket.slide(
//                FullBase.duckLocationToSliderPosition(base.duckDetector.mostDuckyArea()));
//
//        base.outtakeBucket.dump(true);
//        try{ Thread.sleep(500); } catch (Exception e) {}
//        base.outtakeBucket.dump(true);
//
//        base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED,45);
//        base.drivetrain.moveInches(
//                Drivetrain.DRIVE_SPEED, inchesToDuckySpinner,
//                inchesToDuckySpinner, inchesToDuckySpinner, inchesToDuckySpinner);
//
//        base.duckeySpinner.spin(true);
//        try{ Thread.sleep(3000); } catch (Exception e) {}
//        base.duckeySpinner.spin(false);
    }
}
