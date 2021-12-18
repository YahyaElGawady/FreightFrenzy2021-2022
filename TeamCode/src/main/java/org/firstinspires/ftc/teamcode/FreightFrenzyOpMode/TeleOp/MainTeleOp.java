package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.FullBase;

@TeleOp(name = "Main TeleOp", group = "Linear Opmode")
public class MainTeleOp extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    FullBase Base ;


    boolean dpadUpHeld = false;
    boolean dpadDownHeld = false;
    boolean dpadRightHeld = false;
    boolean dpadLeftHeld  = false;
    boolean gamepad1XHeld = false;
    boolean gamepad1YHeld = false;

    boolean flickerPositon =true;
    boolean firstTime = true;
    boolean slowMode = false;
    double initTime;
    int initPos;

    @Override
    public void runOpMode() {
        Base = new FullBase(telemetry,this, hardwareMap, true);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        Base.init();
        telemetry.addLine("done with init");
        waitForStart();
        while (opModeIsActive()){
            /*      drivetrain      */
            double forward = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            Base.drivetrain.drive(forward, turn, slowMode);
            /*      intake      */
            Base.sucker.moveArmInTeleop(gamepad1.right_bumper);
<<<<<<< HEAD
<<<<<<< HEAD
            Base.sucker.moveSuckerInTeleop(gamepad1.right_trigger);
            Base.getTelemetry().addData("Gamepad 1 Trigger: ",gamepad1.right_trigger);
            Base.getTelemetry().addData("Gamepad 2 Trigger: ",gamepad2.right_trigger);
            Base.getTelemetry().addData("Gamepad 2 A: ",gamepad1.a);
            Base.getTelemetry().addData("Gamepad 1 Left Trigger: ",gamepad1.left_trigger);
            Base.getTelemetry().addData("Gamepad 2 y: ",gamepad2.y);
=======
>>>>>>> parent of 60abfa4 (Long Stories No Vibes Heres the code!!!)
=======
>>>>>>> parent of 60abfa4 (Long Stories No Vibes Heres the code!!!)
            Base.sucker.moveSuckerInTeleop(gamepad1.right_trigger);
            if(gamepad1.right_trigger < .1)
                Base.sucker.moveSuckerInTeleop(-gamepad1.left_trigger);
            /*      outtake     */
<<<<<<< HEAD
<<<<<<< HEAD
           Base.outtakeBucket.dump(gamepad2.right_trigger > 0.5);
           Base.getTelemetry().addData("Slider Position", Base.outtakeBucket.slideInTeleop(gamepad2.a));
           Base.getTelemetry().addLine("Update 2; ");
           Base.getTelemetry().update();
=======
            Base.outtakeBucket.dump(gamepad2.right_trigger > 0.5);
            Base.outtakeBucket.slideInTeleop(gamepad2.a);
>>>>>>> parent of 60abfa4 (Long Stories No Vibes Heres the code!!!)
=======
            Base.outtakeBucket.dump(gamepad2.right_trigger > 0.5);
            Base.outtakeBucket.slideInTeleop(gamepad2.a);
>>>>>>> parent of 60abfa4 (Long Stories No Vibes Heres the code!!!)
//            Base.outtakeBucket.changeTopInTeleOp(gamepad2.dpad_up, gamepad2.dpad_down);
            /*   ducky spinner  */
//            Base.duckeySpinner.spin(gamepad2.y);
//            Base.duckeySpinner.spinner.setPower(gamepad2.right_trigger);
            Base.duckeySpinner.spin(
                    gamepad2.right_bumper,
                    gamepad2.left_bumper);
        }
    }
}
