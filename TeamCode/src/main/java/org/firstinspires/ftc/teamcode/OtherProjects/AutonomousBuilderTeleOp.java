package org.firstinspires.ftc.teamcode.OtherProjects;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.FullBase;

@TeleOp(name = "Autonomous Builder TeleOp", group = "Linear Opmode")
public class AutonomousBuilderTeleOp extends LinearOpMode {
    static final String name = "TestAuto";
    static final int numTasks=6;

    FullBase base;
    @Override
    public void runOpMode() throws InterruptedException {
        base = new FullBase(telemetry, this, hardwareMap, false);
        AutonomousBuilder auto = new AutonomousBuilder(name,numTasks);
        auto.createAuto();
    }
}
