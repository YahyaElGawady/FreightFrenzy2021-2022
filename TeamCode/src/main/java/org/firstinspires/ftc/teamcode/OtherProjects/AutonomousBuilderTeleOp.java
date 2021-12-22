package org.firstinspires.ftc.teamcode.OtherProjects;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AutonomousBuilderTeleOp extends LinearOpMode {
    static final String name = "TestAuto";
    @Override
    public void runOpMode() throws InterruptedException {
        AutonomousBuilder auto = new AutonomousBuilder(name);
        auto.createAuto();
    }
}
