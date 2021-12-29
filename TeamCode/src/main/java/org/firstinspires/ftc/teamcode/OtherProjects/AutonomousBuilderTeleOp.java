package org.firstinspires.ftc.teamcode.OtherProjects;

import static org.firstinspires.ftc.teamcode.OtherProjects.AutonomousBuilder.encoders;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.FullBase;

@TeleOp(name = "Autonomous Builder TeleOp", group = "Linear Opmode")
public class AutonomousBuilderTeleOp extends LinearOpMode {
    public static final String name   = "TestAuto";
    public static final int numTasks  = 6;

    public static boolean STOP_BUTTON     = false;
    public static boolean STOP_BUTTON_SET = true;
    FullBase base;

    @Override
    public void runOpMode(){
        int i = 0;
        base = new FullBase(telemetry, this, hardwareMap, false);
        AutonomousBuilder auto = new AutonomousBuilder(name,numTasks);

        // Create daemon thread that constantly updates the stop button. Not necessary, but
        // possibly convenient.
//        Thread stop_button = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    if(true /* noTODO: fill with stop button */ && !STOP_BUTTON_SET) STOP_BUTTON = true;
//                }
//            }
//        });
//        stop_button.setDaemon(true);
//        stop_button.run();

        // Creates a task that records the drivetrain forward-backward motion.
        auto.createTask(0, "Drivetrain: Forward, Backward", new AutonomousBuilder.EncoderTask(
                (FullBase base) -> {
                    while(!STOP_BUTTON) {
                        base.drivetrain.drive(-gamepad1.left_stick_y,
                                0, true);
                        STOP_BUTTON = /*TODO: fill with stop button*/ true;
                    }

//                    STOP_BUTTON_SET = true;
                    // For thread safety:
//                    try { stop_button.sleep(25); } catch (InterruptedException e){}
                    STOP_BUTTON = false;

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
        // Creates a task that records the drivetrain turn motion.
        auto.createTask(1, "Drivetrain: Turn", new AutonomousBuilder.EncoderTask(
                        (FullBase base) -> {
//                            STOP_BUTTON_SET = false;
                            while(!STOP_BUTTON) {
                                base.drivetrain.drive(0,
                                        gamepad1.right_stick_x, true);
                                STOP_BUTTON = /*TODO: fill with stop button*/ true;
                            }

//                            STOP_BUTTON_SET = true;
                            // For thread safety:
//                            try { stop_button.sleep(25); } catch (InterruptedException e){}
                            STOP_BUTTON = false;

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
        // Creates a task that runs the sucker and drivetrain forward-backward.
        auto.createTask(2, "Drivetrain: Forward, Backward; Intake: Sucker",
                new AutonomousBuilder.EncoderTask(
                        (FullBase base) -> {
//                            STOP_BUTTON_SET = false;
                            while(!STOP_BUTTON) {
                                base.drivetrain.drive(-gamepad1.left_stick_y,
                                        0, true);
                                base.sucker.moveSuckerInTeleop(gamepad1.right_trigger);

                                STOP_BUTTON = /*TODO: fill with stop button*/ true;
                            }
//                            STOP_BUTTON_SET = true;
                            // For thread safety:
//                            try { stop_button.sleep(25); } catch (InterruptedException e){}
                            STOP_BUTTON     = false;

                            return new AutonomousBuilder.EncoderTask.RetType(
                                    AutonomousBuilder.ChildComponents("base.sucker.SUCKER"),
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
        // Creates a task that moves the intake arm
        auto.createTask(3, "Intake: Arm",
                new AutonomousBuilder.TimerTask(
                        (FullBase base) ->{
//                            STOP_BUTTON_SET = false;
                            while(!STOP_BUTTON) {
                                base.sucker.moveArmInTeleop(gamepad1.right_bumper);
                                STOP_BUTTON = /*TODO: fill with stop button*/ true;
                            }
//                            STOP_BUTTON_SET = true;
                            // For thread safety:
//                            try { stop_button.sleep(25); } catch (InterruptedException e){}
                            STOP_BUTTON = false;
                        },
                        "base.sucker.ARM"
                )
        );
        // Creates a task that moves the slider
        auto.createTask(4, "Outtake: Slider",
                new AutonomousBuilder.EncoderTask(
                        (FullBase base) ->{
//                            STOP_BUTTON_SET = false;
                            while(!STOP_BUTTON) {
                                base.outtakeBucket.slideInTeleop(gamepad2.a);

                                STOP_BUTTON = /*TODO: fill with stop button*/ true;
                            }
//                            STOP_BUTTON_SET = true;
                            // For thread safety:
//                            try { stop_button.sleep(25); } catch (InterruptedException e){}
                            STOP_BUTTON = false;
                            return new AutonomousBuilder.EncoderTask.RetType(
                                    AutonomousBuilder.ChildComponents(),
                                    AutonomousBuilder.MainComponents(
                                            AutonomousBuilder.encoderArray(
                                                    AutonomousBuilder.encoders(
                                                            base.outtakeBucket.sliderPosition
                                                    )
                                            ),
                                            "base.outtakeBucket.SLIDER"
                                    )
                            );
                        }
                )
        );
        // Creates a task that dumps
        auto.createTask(5, "Outtake: Dumper",
                new AutonomousBuilder.TimerTask(
                        (FullBase base) ->{
//                            STOP_BUTTON_SET = false;
                            while(!STOP_BUTTON) {
                                base.outtakeBucket.dump(gamepad2.y);
                                STOP_BUTTON = /*TODO: fill with stop button*/ true;
                            }
//                            STOP_BUTTON_SET = true;
                            // For thread safety:
//                            try { stop_button.sleep(25); } catch (InterruptedException e){}
                            STOP_BUTTON = false;
                        },
                        "base.outtakeBucket.DUMPER"
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
        }
        auto.createEndOfAuto();
    }
}
