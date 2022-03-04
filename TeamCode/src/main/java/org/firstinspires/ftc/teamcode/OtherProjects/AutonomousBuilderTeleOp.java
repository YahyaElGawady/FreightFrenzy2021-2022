package org.firstinspires.ftc.teamcode.OtherProjects;

import static org.firstinspires.ftc.teamcode.OtherProjects.AutonomousBuilder.encoders;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.FullBase;

@TeleOp(name = "Autonomous Builder TeleOp", group = "Linear Opmode")
public class AutonomousBuilderTeleOp extends LinearOpMode {
    public static final String name   = "TestAuto";
    public static final int numTasks  = 6;

//    public static boolean STOP_BUTTON     = false;
//    public static boolean STOP_BUTTON_SET = true;
    FullBase base;

    @Override
    public void runOpMode(){
        int i = 0;
        telemetry.addData("Status", "Starting");
        telemetry.update();
        base = new FullBase(telemetry, this, hardwareMap, false);
        base.initWithoutDuckyDetector();
        AutonomousBuilder auto = new AutonomousBuilder(base, name,numTasks);
        telemetry.addLine("AutoBuilder created");

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

        telemetry.addLine("Creating Tasks...");
        telemetry.update();
        try{Thread.sleep(2000);}catch(Exception e){}

        // Creates a task that records the drivetrain forward-backward motion.
        auto.createTask(0, "Drivetrain: Forward, Backward", new AutonomousBuilder.EncoderTask(
                (FullBase base) -> {
                    while(!gamepad1.b) {
                        base.drivetrain.drive(-gamepad1.left_stick_y, 0,
                                0, false, true);
//                        STOP_BUTTON = gamepad1.b;
                    }

//                    STOP_BUTTON_SET = true;
                    // For thread safety:
//                    try { stop_button.sleep(25); } catch (InterruptedException e){}
//                    STOP_BUTTON = false;

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
//        telemetry.addLine("Task 0");
//        telemetry.update();
        // Creates a task that records the drivetrain turn motion.
        auto.createTask(1, "Drivetrain: Turn", new AutonomousBuilder.EncoderTask(
                        (FullBase base) -> {
//                            STOP_BUTTON_SET = false;
                            while(!gamepad1.b) {
                                base.drivetrain.drive(0, 0,
                                        gamepad1.right_stick_x, false, true);
//                                STOP_BUTTON = gamepad1.b;
                            }

//                            STOP_BUTTON_SET = true;
                            // For thread safety:
//                            try { stop_button.sleep(25); } catch (InterruptedException e){}
//                            STOP_BUTTON = false;

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
//        telemetry.addLine("Task 1");
//        telemetry.update();
        // Creates a task that runs the sucker and drivetrain forward-backward.
        auto.createTask(2, "Drivetrain: Forward, Backward; Intake: Sucker",
                new AutonomousBuilder.EncoderTask(
                        (FullBase base) -> {
//                            STOP_BUTTON_SET = false;
                            while(!gamepad1.b) {
                                base.drivetrain.drive(-gamepad1.left_stick_y, 0,
                                        0, false, true);
                                base.sucker.moveSuckerInTeleop(gamepad1.right_trigger);

//                                STOP_BUTTON = gamepad1.b;
                            }
//                            STOP_BUTTON_SET = true;
                            // For thread safety:
//                            try { stop_button.sleep(25); } catch (InterruptedException e){}
//                            STOP_BUTTON     = false;

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
//        telemetry.addLine("Task 2");
//        telemetry.update();
        // Creates a task that moves the intake arm
        auto.createTask(3, "Intake: Arm",
                new AutonomousBuilder.TimerTask(
                        (FullBase base) ->{
//                            STOP_BUTTON_SET = false;
                            while(!gamepad1.b) {
                                base.sucker.armMovementsInTeleop(gamepad1.right_bumper, gamepad2.b, Math.pow(gamepad2.left_stick_y, 3) * .5);
//                                base.sucker.moveArmInTeleop(gamepad1.right_bumper);
//                                if(!gamepad1.right_bumper && !gamepad2.b)
//                                    base.sucker.moveArmManual(Math.pow(gamepad2.left_stick_y, 3) * .5);
//                                base.sucker.moveArmToNeutral(gamepad2.b);
//                                STOP_BUTTON = gamepad1.b;
                            }
//                            STOP_BUTTON_SET = true;
                            // For thread safety:
//                            try { stop_button.sleep(25); } catch (InterruptedException e){}
//                            STOP_BUTTON = false;
                        },
                        "base.sucker.ARM"
                )
        );
//        telemetry.addLine("Task 3");
//        telemetry.update();
        // Creates a task that moves the slider
        auto.createTask(4, "Outtake: Slider",
                new AutonomousBuilder.EncoderTask(
                        (FullBase base) ->{
//                            STOP_BUTTON_SET = false;
                            while(!gamepad1.b) {
//                                base.outtakeBucket.changeTopInTeleOp(gamepad2.dpad_up, gamepad2.dpad_down);
                                base.outtakeBucket.slideInTeleop(gamepad2.a, gamepad2.x);
                                base.outtakeBucket.slideManual(gamepad2.right_stick_y);
//                                STOP_BUTTON = gamepad1.b;
                            }
//                            STOP_BUTTON_SET = true;
                            // For thread safety:
//                            try { stop_button.sleep(25); } catch (InterruptedException e){}
//                            STOP_BUTTON = false;
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
//        telemetry.addLine("Task 4");
//        telemetry.update();
        // Creates a task that dumps
        auto.createTask(5, "Outtake: Dumper",
                new AutonomousBuilder.TimerTask(
                        (FullBase base) ->{
//                            STOP_BUTTON_SET = false;
                            while(!gamepad1.b) {
                                base.outtakeBucket.dump(gamepad2.y);
//                                STOP_BUTTON = gamepad1.b;
                            }
//                            STOP_BUTTON_SET = true;
                            // For thread safety:
//                            try { stop_button.sleep(25); } catch (InterruptedException e){}
//                            STOP_BUTTON = false;
                        },
                        "base.outtakeBucket.DUMPER"
                )
        );
//        telemetry.addLine("Task 5");
//        telemetry.update();

        telemetry.addLine("All Tasks completed");
//        telemetry.update();


        telemetry.addLine("Creating start of auto...");
        telemetry.update();
        if(!auto.createStartOfAuto()){
            telemetry.addLine("Error in creation of start of auto");
            telemetry.update();
            return;
        }
        telemetry.addLine("Done");
        telemetry.update();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        telemetry.addData("Task", "0 - %s", auto.taskDescriptions[0]);
        telemetry.addData("Status", "Selecting task...");
        telemetry.update();

        while(opModeIsActive()){

            // While start button is not pressed, adjust
            // current task with dpad up and down buttons
            while(! gamepad1.y) {
                if(gamepad1.dpad_up ||
                   gamepad1.dpad_down)
                {
                    if (        gamepad1.dpad_up) {
                        ++i;
                    } else if ( gamepad1.dpad_down) {
                        --i;
                    }

                    telemetry.addData("Task", "%d - %s", i, auto.taskDescriptions[i]);
                    telemetry.update();
                    try {
                        Thread.sleep(500);
                    } catch(Exception e){}
                }
            }
            telemetry.addData("Status", "Done selecting task. Executing...");
            telemetry.update();
            auto.tasks[i].execute(base);
            telemetry.addData("Status", "Done executing task. Selecing...");
            telemetry.update();
        }
        if(!auto.createEndOfAuto()){
            telemetry.addLine("Error in end of auto generation");
            telemetry.update();
            try {
                Thread.sleep(1000);
            } catch (Exception e2) {
            }
            System.exit(1);
        }
    }
}
