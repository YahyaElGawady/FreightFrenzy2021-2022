package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
    }
}
