package org.firstinspires.ftc.teamcode.OtherProjects;

import static org.firstinspires.ftc.teamcode.OtherProjects.AutonomousBuilder.encoders;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.FullBase;

@TeleOp(name = "Autonomous Builder TeleOp", group = "Linear Opmode")
public class AutonomousBuilderTeleOp extends LinearOpMode {
    static final String name   = "TestAuto";
    static final int numTasks  = 6;

    FullBase base;

    @Override
    public void runOpMode(){
        int i = 0;
        base = new FullBase(telemetry, this, hardwareMap, false);
        AutonomousBuilder auto = new AutonomousBuilder(name,numTasks);

        // Creates a task that records the drivetrain.
        auto.createTask(0, "Drivetrain", new AutonomousBuilder.EncoderTask(
                (FullBase base) -> {
                    while(! /* TODO fill with stop button*/)
                        base.drivetrain.drive(gamepad1.left_stick_y,
                                gamepad1.right_stick_x, false);

                    return new AutonomousBuilder.EncoderTask.RetType(
                            AutonomousBuilder.ChildComponents(),
                            AutonomousBuilder.MainComponents(
                                    AutonomousBuilder.encoderArray(
                                            AutonomousBuilder.encoders(
                                                    base.drivetrain.frontLeft.getCurrentPosition(),
                                                    base.drivetrain.frontRight.getCurrentPosition(),
                                                    base.drivetrain.backLeft.getCurrentPosition(),
                                                    base.drivetrain.backRight.getCurrentPosition()
                                            )
                                    ),
                                    "base.drivetrain"
                            )
                    );
                }
            )
        );

        auto.createStartOfAuto();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        telemetry.addData("Task", "0 - %s", auto.taskDescriptions[0]);
        telemetry.addData("Status", "Selecting task...");
        telemetry.update();

        while(opModeIsActive()){

            // While start button is not pressed, adjust
            // current task with dpad up and down buttons

            while(! /*TODO: fill with start button*/) {
                if(/*TODO: fill with dpad up button   */ ||
                   /*TODO: fill with dpad down button */)
                {
                    if (        /*TODO: fill with dpad up button   */) {
                        ++i;
                    } else if ( /*TODO: fill with dpad down button */) {
                        --i;
                    }

                    telemetry.addData("Task", "%d - %s", i, auto.taskDescriptions[i]);
                    telemetry.update();
                    try {
                        Thread.sleep(300);
                    } catch(Exception e){}
                }
            }
            telemetry.addData("Status", "Done selecting task. Executing...");
            telemetry.update();
            auto.tasks[i].execute(base);
            telemetry.addData("Status", "Done executing task. Selecing...");
            telemetry.update();
            i = 0;
        }
    }
}
