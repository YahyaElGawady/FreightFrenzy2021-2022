package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.Drivetrain;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.DuckDetector;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.OuttakeBucket;
import org.firstinspires.ftc.teamcode.FullBase;
@Disabled
@Autonomous(name="DuckSpinneyAuto")
@Deprecated
public class DuckSpinneySideAuto extends LinearOpMode {
    FullBase base;

    public final double inchesToDuckySpinner = 20;
    @Override
    public void runOpMode(){
//        base = new FullBase(telemetry, this, hardwareMap, false);
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//        base.init();
//        telemetry.addLine("done with init");
//        waitForStart();
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
