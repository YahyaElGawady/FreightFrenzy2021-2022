package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.Drivetrain;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.DuckDetector;
import org.firstinspires.ftc.teamcode.FreightFrenzyComponents.OuttakeBucket;
import org.firstinspires.ftc.teamcode.FullBase;

@Autonomous(name="DuckSpinneyAuto")
public class DuckSpinneySideAuto extends LinearOpMode {
    FullBase base;


    @Override
    public void runOpMode(){
        base = new FullBase(telemetry, this, hardwareMap, false);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        base.init();
        telemetry.addLine("done with init");
        waitForStart();
        base.duckDetector.takePicture();
        base.outtakeBucket.slide(
                FullBase.duckLocationToSliderPosition(base.duckDetector.mostDuckyArea()));
        base.drivetrain.gyroTurn(Drivetrain.TURN_SPEED, 45);
    }
}
